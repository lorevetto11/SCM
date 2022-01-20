package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.SystemCheckConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.ejb.entities.SystemCheck;
import com.gpi.scm.generic.dtos.SystemCheckDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.SystemCheckLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)

public class SystemCheckBean extends GenericBean implements SystemCheckLocal {
	private static final Logger logger = Logger.getLogger(SystemCheckBean.class);

	@Override
	public SystemCheckDto saveCheck(SystemCheckDto toSave) throws BusinessException {
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}
		SystemCheck syscheck = SystemCheckConverter.dtoToEntity(toSave);
		syscheck.setOrganization(organization);

		SystemCheck tmp = entityManager.merge(syscheck);

		return SystemCheckConverter.entityToDto(tmp, true);
	}

	@Override
	public SystemCheckDto editCheck(SystemCheckDto toSave) throws BusinessException {
		SystemCheck syscheck = entityManager.find(SystemCheck.class, toSave.getId());
		if (syscheck == null || syscheck.getDeleted()) {
			throw new NoResultException("Check not found");
		}

		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (!organizations.contains(syscheck.getOrganization().getId())) {
			throw new NoResultException("No access!");

		}
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}

		syscheck.setName(toSave.getName());
		syscheck.setDescription(toSave.getDescription());
		syscheck.setOrganization(organization);
		syscheck.setPrivacy(toSave.isPrivacy());
		entityManager.persist(syscheck);

		return SystemCheckConverter.entityToDto(syscheck, true);
	}

	@Override
	public boolean deleteCheck(long id) throws BusinessException {

		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		SystemCheck toDelete = entityManager.find(SystemCheck.class, id);
		if (toDelete == null || toDelete.getDeleted() || !organizations.contains(toDelete.getOrganization().getId())) {
			return false;
		}
		toDelete.setDeleted(true);
		return true;
	}

	@Override
	public List<SystemCheckDto> findChecks(Long organizationId) throws BusinessException {
		try {
			if (organizationId != null) {
				List<Long> myOrganizations = OrganizationDelegate
						.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
				if (!myOrganizations.contains(organizationId)) {
					throw new NoResultException("No access !");

				}
			} else {
				organizationId = UserContextHolder.getUser().getOrganization().getId();
			}

			Organization organization = entityManager.find(Organization.class, organizationId);
			if (organization == null || organization.getDeleted()) {
				throw new NoResultException("Organization does not exist or got deleted");
			}
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsReverseTree(organizationId);

			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);

			List<SystemCheck> entitiesFalse = (List<SystemCheck>) entityManager
					.createNamedQuery(SystemCheck.NQ_SYSTEMCHECKS)
					.getResultList();
			
			List<SystemCheck> entitiesTrue = (List<SystemCheck>) entityManager
					.createNamedQuery(SystemCheck.NQ_SYSTEMCHECKS_TRUE)
					.setParameter("organizations", organizations)
					.getResultList();
			entitiesFalse.addAll(entitiesTrue);
			return SystemCheckConverter.entityToDto(entitiesFalse, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

}
