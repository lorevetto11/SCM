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

import com.gpi.scm.converters.UserRoleConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.ejb.entities.UserProfile;
import com.gpi.scm.ejb.entities.UserRole;
import com.gpi.scm.generic.dtos.UserProfileDto;
import com.gpi.scm.generic.dtos.UserRoleDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.UserRoleLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class UserRoleBean extends GenericBean implements UserRoleLocal {
	private static final Logger logger = Logger.getLogger(UserRoleBean.class);

	@Override
	public List<UserRoleDto> findRoles(List<Long> organizations) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			List<UserRole> entities = (List<UserRole>) entityManager
					.createNamedQuery(UserRole.NQ_ROLES_IN_ORGANIZATIONS).setParameter("organizations", organizations)
					.getResultList();
			return UserRoleConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

	@Override
	public UserRoleDto findRoleById(long idRole) throws BusinessException {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsReverseTree(UserContextHolder.getUser().getOrganization().getId());
			
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			UserRole role = (UserRole) entityManager.createNamedQuery(UserRole.NQ_ROLE_BY_ID)
					.setParameter("idRole", idRole).getSingleResult();
			return UserRoleConverter.entityToDto(role, true, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return null;
	}

	@Override
	public UserRoleDto saveRole(UserRoleDto toSave) throws BusinessException {
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());

		if (!organizations.contains(toSave.getOrganization().getId())) {
			throw new NoResultException("No access!");
		}

		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}

		List<UserProfileDto> profileToInsert = toSave.getProfiles();
		List<UserProfile> effectiveUserProfiles = new ArrayList<UserProfile>();

		if (!profileToInsert.isEmpty()) {
			Set<Long> profileId = new HashSet<Long>(); // prevention of duplicates
			for (UserProfileDto profile : profileToInsert) {
				profileId.add(profile.getId());
			}

			effectiveUserProfiles = (List<UserProfile>) entityManager
					.createQuery("Select r from UserProfile r where r.id in :profile ")
					.setParameter("profile", profileId).getResultList();
			if (effectiveUserProfiles.size() != profileId.size()) {
				throw new NoResultException("One or more profile not found!");
			}
		}

		UserRole role = UserRoleConverter.dtoToEntity(toSave);
		role.setOrganization(organization);
		role.getProfiles().addAll(effectiveUserProfiles);

		return UserRoleConverter.entityToDto(entityManager.merge(role), true);
	}

	@Override
	public UserRoleDto editRole(UserRoleDto toSave) throws BusinessException {
		UserRole role = entityManager.find(UserRole.class, toSave.getId());
		if (role == null || role.getDeleted()) {
			throw new NoResultException("Role not found!");
		}
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		if (!organizations.contains(toSave.getOrganization().getId())) {
			throw new NoResultException("No access!");
		}
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted() || !organizations.contains(organization.getId())) {
			throw new NoResultException("Organization not found!");
		}

		List<UserProfileDto> profileToInsert = toSave.getProfiles();
		List<UserProfile> effectiveUserProfiles = new ArrayList<UserProfile>();
		if (!profileToInsert.isEmpty()) {
			Set<Long> profileId = new HashSet<Long>();
			for (UserProfileDto profile : profileToInsert) {
				profileId.add(profile.getId());
			}
			effectiveUserProfiles = (List<UserProfile>) entityManager
					.createQuery("Select r from UserProfile r where r.id in :profile ")
					.setParameter("profile", profileId).getResultList();
			if (effectiveUserProfiles.size() != profileId.size()) {
				throw new NoResultException("One or more profile not found!");
			}
		}
		role.setName(toSave.getName());
		role.setDescription(toSave.getDescription());
		role.setOrganization(organization);
		role.getProfiles().clear();
		role.getProfiles().addAll(effectiveUserProfiles);

		return UserRoleConverter.entityToDto(entityManager.merge(role), true);
	}

	@Override
	public boolean deleteUserRoleById(long id) throws BusinessException {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());

			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);
			UserRole toDelete = (UserRole) entityManager.createNamedQuery(UserRole.NQ_ROLE_BY_ID)
					.setParameter("idRole", id).getSingleResult();
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

}
