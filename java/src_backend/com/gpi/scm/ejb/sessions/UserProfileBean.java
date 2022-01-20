package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.UserProfileConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Grant;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.ejb.entities.UserProfile;
import com.gpi.scm.generic.dtos.GrantDto;
import com.gpi.scm.generic.dtos.UserProfileDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.UserProfileLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class UserProfileBean extends GenericBean implements UserProfileLocal {

	private static final Logger logger = Logger.getLogger(UserProfileBean.class);

	@Override
	public UserProfileDto findProfileById(long idProfile, List<Long> organizations) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			UserProfile profile = (UserProfile) entityManager.createNamedQuery(UserProfile.NQ_PROFILE_BY_ID)
					.setParameter("idProfile", idProfile).setParameter("organizations", organizations)
					.getSingleResult();
			return UserProfileConverter.entityToDto(profile, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserProfileDto saveProfile(UserProfileDto toSave) throws BusinessException {

		UserProfile profile = new UserProfile();
		if (toSave.getOrganization() != null) {

			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());

			if (!organizations.contains(toSave.getOrganization().getId())) {
				throw new NoResultException("No access!");
			}
			Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
			if (organization == null || organization.getDeleted()
					|| !(organizations.contains(toSave.getOrganization().getId()))) {
				throw new NoResultException("Organization not found!");
			}

			profile.setOrganization(organization);
		} else {
			throw new BusinessException("Organization needed to create a profile!");
		}
		Session ses = (Session) entityManager.getDelegate();
		ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);

		List<GrantDto> grantsToInsert = toSave.getGrants();
		List<Grant> effectiveGrants = new ArrayList<Grant>();

		if (!grantsToInsert.isEmpty()) {
			Set<Long> grantsId = new HashSet<Long>();
			for (GrantDto grant : grantsToInsert) {
				grantsId.add(grant.getId());
			}
			effectiveGrants = (List<Grant>) entityManager.createQuery("Select r from Grant r where r.id in :grants ")
					.setParameter("grants", grantsId).getResultList();
			if (effectiveGrants.size() != grantsId.size()) {
				throw new NoResultException("One or more grants not found!");
			}
		}
		profile.setName(toSave.getName());
		profile.getGrants().clear();
		profile.getGrants().addAll(effectiveGrants);
		profile.setName(toSave.getName());

		return UserProfileConverter.entityToDto(entityManager.merge(profile), true);

	}

	@Override
	public UserProfileDto editProfile(UserProfileDto toSave) throws BusinessException {

		UserProfile profile = entityManager.find(UserProfile.class, toSave.getId());
		if (profile == null || profile.getDeleted()) {
			throw new NoResultException("Profile not found!");
		}
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());

		if (!organizations.contains(toSave.getOrganization().getId())) {
			throw new NoResultException("No access!");
		}
		if (!(organizations.contains(profile.getOrganization().getId()))) {
			throw new NoResultException("Organization not found!");
		}
		if (toSave.getOrganization() != null) {
			Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
			if (organization == null || organization.getDeleted() || !(organizations.contains(organization.getId()))) {
				throw new NoResultException("Organization not found!");
			}
			profile.setOrganization(organization);

		}
		List<GrantDto> grantsToInsert = toSave.getGrants();
		List<Grant> effectiveGrants = new ArrayList<Grant>();

		if (!grantsToInsert.isEmpty()) {
			Set<Long> grantsId = new HashSet<Long>();
			for (GrantDto grant : grantsToInsert) {
				grantsId.add(grant.getId());
			}

			effectiveGrants = (List<Grant>) entityManager.createQuery("Select r from Grant r where r.id in :grants ")
					.setParameter("grants", grantsId).getResultList();

			if (effectiveGrants.size() != grantsId.size()) {
				throw new NoResultException("One or more grants not found!");
			}
		}

		profile.setName(toSave.getName());
		profile.getGrants().clear();
		profile.getGrants().addAll(effectiveGrants);

		return UserProfileConverter.entityToDto(entityManager.merge(profile), true);

	}

	@Override
	public List<UserProfileDto> findProfiles(List<Long> organizations) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			List<UserProfile> entities = (List<UserProfile>) entityManager.createNamedQuery(UserProfile.NQ_PROFILES)
					.getResultList();

			return UserProfileConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

	public boolean deleteUserProfileById(long id) throws BusinessException {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());

			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			UserProfile toDelete = (UserProfile) entityManager.createNamedQuery(UserProfile.NQ_PROFILE_BY_ID)
					.setParameter("idProfile", id).getSingleResult();
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
	public List<UserProfileDto> getAllProfiles() throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);

			List<UserProfile> entities = (List<UserProfile>) entityManager.createNamedQuery(UserProfile.NQ_PROFILES)
					.getResultList();

			return UserProfileConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

}
