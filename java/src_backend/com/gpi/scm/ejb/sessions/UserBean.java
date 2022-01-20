package com.gpi.scm.ejb.sessions;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;

import com.gpi.scm.constants.SmtpServerConstants;
import com.gpi.scm.converters.UserConverter;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.ejb.entities.User;
import com.gpi.scm.ejb.entities.UserRole;
import com.gpi.scm.generic.dtos.UserDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.UserLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class UserBean extends GenericBean implements UserLocal {

	private static final Logger logger = Logger.getLogger(UserBean.class);

	@Override
	public UserDto saveUser(UserDto toSave) throws BusinessException {
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}
		UserRole role = entityManager.find(UserRole.class, toSave.getRole().getId());

		if (role == null || role.getDeleted()) {
			throw new NoResultException("Role not found!");
		}

		User user = UserConverter.dtoToEntity(toSave);

		String initialPassword = RandomStringUtils.random(10, true, true);
	//	String initialPassword = "password";

		user.setPassword(MD5(initialPassword));
		user.setOrganization(organization);
		user.setRole(role);
		Session ses = (Session) entityManager.getDelegate();
		ses.getTransaction().begin();
		User userMerge = null;
		try {
			userMerge = entityManager.merge(user);
			entityManager.flush();
		
			Email email = EmailBuilder.startingBlank().from(SmtpServerConstants.DEFAULT_MAILER)
					.to(userMerge.getEmail()).withSubject(userMerge.getId().toString())
					.withHTMLText(initialPassword).buildEmail();

			Mailer mailerTest = MailerBuilder
					.withSMTPServer(SmtpServerConstants.SMTP_SERVER_HOST, SmtpServerConstants.SMTP_SERVER_PORT,
							SmtpServerConstants.SMTP_SERVER_USER, SmtpServerConstants.SMTP_SERVER_PASSWORD)
					.withTransportStrategy(SmtpServerConstants.SMTP_SERVER_SSL ? TransportStrategy.SMTP_TLS : TransportStrategy.SMTP).withSessionTimeout(10 * 1000)
					.clearEmailAddressCriteria().withDebugLogging(false).buildMailer();

			mailerTest.sendMail(email);
		} catch(PersistenceException e){	
			ses.getTransaction().rollback();
			throw new NonUniqueResultException("Username already exists");
		} catch (Exception e) {
			ses.getTransaction().rollback();
			throw new RuntimeException("Mailing error");
		}
		ses.getTransaction().commit();
		return UserConverter.entityToDto(userMerge, true);

	}

	@Override
	public UserDto editUser(UserDto toSave) throws BusinessException {

		User user = entityManager.find(User.class, toSave.getId());

		if (user == null || user.getDeleted()) {
			throw new NoResultException("User not found!");
		}
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found ");
		}
		UserRole role = entityManager.find(UserRole.class, toSave.getRole().getId());
		if (role == null || role.getDeleted()) {
			throw new NoResultException("Role not found!");
		}
		user.setUsername(toSave.getEmail());
		user.setEmail(toSave.getEmail());
		user.setFirstname(toSave.getFirstname());
		user.setLanguage(toSave.getLanguage());
		user.setLastname(toSave.getLastname());
		user.setPhone(toSave.getPhone());
		user.setStatus(toSave.getStatus());

		user.setOrganization(organization);
		user.setRole(role);
		return UserConverter.entityToDto(entityManager.merge(user), true);

	}

	@Override
	public UserDto findUserById(long idUser) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);

			User user = (User) entityManager.createNamedQuery(User.NQ_USER_BY_ID).setParameter("idUser", idUser)
					.getSingleResult();
			return UserConverter.entityToDto(entityManager.merge(user), true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return null;
	}

	@Override
	public List<UserDto> findUsers(List<Long> organizations) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			List<User> entities = (List<User>) entityManager.createNamedQuery(User.NQ_USERS_IN_ORGANIZATIONS)
					.setParameter("organizations", organizations).getResultList();
			return UserConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

	@Override
	public UserDto findUserByLogin(UserDto login) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			String hashpass = MD5(login.getPassword());
			User user = (User) entityManager.createNamedQuery(User.NQ_LOGIN)
					.setParameter("username", login.getUsername()).setParameter("password", hashpass).getSingleResult();
			return UserConverter.entityToDto(user, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return null;
	}

	@Override
	public boolean deleteUserById(long id, List<Long> organizations) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);
			User toDelete = (User) entityManager.createNamedQuery(User.NQ_USER_BY_ID).setParameter("idUser", id)
					.getSingleResult();
			toDelete.setDeleted(true);
			entityManager.merge(toDelete);
			return true;

		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return false;

	}

	@Override
	public UserDto findUserById(long idUser, List<Long> organizations) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			User user = (User) entityManager.createNamedQuery(User.NQ_USER_BY_ID).setParameter("idUser", idUser)
					.getSingleResult();

			return UserConverter.entityToDto(entityManager.merge(user), true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return null;
	}

	public String MD5(String md5) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes(Charset.forName("UTF-8")));
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (NullPointerException e) {

		} catch (java.security.NoSuchAlgorithmException e) {
		}
		return null;
	}
}
