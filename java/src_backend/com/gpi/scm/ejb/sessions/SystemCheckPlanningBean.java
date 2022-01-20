package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import com.gpi.scm.converters.SystemCheckPlanningConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.ejb.entities.SystemCheck;
import com.gpi.scm.ejb.entities.SystemCheckPlanning;
import com.gpi.scm.generic.dtos.SystemCheckDto;
import com.gpi.scm.generic.dtos.SystemCheckPlanningDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.SystemCheckPlanningLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)

public class SystemCheckPlanningBean extends GenericBean implements SystemCheckPlanningLocal {
	private static final Logger logger = Logger.getLogger(SystemCheckPlanningBean.class);

	@Override
	public SystemCheckPlanningDto savePlanning(SystemCheckPlanningDto toSave) throws BusinessException {
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}
		Organization company = entityManager.find(Organization.class, toSave.getCompany().getId());
		if (company == null || company.getDeleted()) {
			throw new NoResultException("Company not found!");
		}
		List<SystemCheckDto> systemcheckToInsert = toSave.getSystemchecks();
		List<SystemCheck> effectiveSystemChecks = new ArrayList<>();
		if (!systemcheckToInsert.isEmpty()) {
			for (SystemCheckDto systemcheck : systemcheckToInsert) {
				effectiveSystemChecks.add(entityManager.find(SystemCheck.class, systemcheck.getId()));
			}
		}

		SystemCheckPlanning syscheck = SystemCheckPlanningConverter.dtoToEntity(toSave);
		syscheck.setSystemChecks(effectiveSystemChecks);
		syscheck.setCompany(company);
		syscheck.setOrganization(organization);

		SystemCheckPlanning tmp = entityManager.merge(syscheck);

		return SystemCheckPlanningConverter.entityToDto(tmp, true);
	}

	@Override
	public SystemCheckPlanningDto editPlanning(SystemCheckPlanningDto toSave) throws BusinessException {
		SystemCheckPlanning syscheck = entityManager.find(SystemCheckPlanning.class, toSave.getId());
		if (syscheck == null || syscheck.getDeleted()) {
			throw new NoResultException("Check not found");
		}
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		if(!organizations.contains(syscheck.getOrganization().getId()))
		{
			throw new NoResultException("No access !");
		}
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}
		Organization company = entityManager.find(Organization.class, toSave.getCompany().getId());
		if (company == null || company.getDeleted()) {
			throw new NoResultException("Company not found!");
		}
		
		
		List<SystemCheckDto> systemcheckToInsert = toSave.getSystemchecks();
		List<SystemCheck> effectiveSystemChecks = new ArrayList<>();
		if (!systemcheckToInsert.isEmpty()) {
			for (SystemCheckDto systemcheck : systemcheckToInsert) {
				effectiveSystemChecks.add(entityManager.find(SystemCheck.class, systemcheck.getId()));
			}
		}

		syscheck.setSystemChecks(effectiveSystemChecks);
		syscheck.setStatus(toSave.getStatus());
		syscheck.setDate(toSave.getDate());
		syscheck.setStartDate(toSave.getStartDate());
		syscheck.setCloseDate(toSave.getCloseDate());
		syscheck.setCompany(company);
		syscheck.setOrganization(organization);

		entityManager.persist(syscheck);

		return SystemCheckPlanningConverter.entityToDto(syscheck, true);
	}

	@Override
	public boolean deletePlanning(long id) throws BusinessException {
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());

		SystemCheckPlanning toDelete = entityManager.find(SystemCheckPlanning.class, id);
		if (toDelete == null || toDelete.getDeleted() || !organizations.contains(toDelete.getOrganization().getId())) {
			return false;
		}
		toDelete.setDeleted(true);
		return true;
	}

	@Override
	public List<SystemCheckPlanningDto> findPlanning(Long organizations) throws BusinessException {

		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);

			List<SystemCheckPlanning> entities = (List<SystemCheckPlanning>) entityManager
					.createNamedQuery(SystemCheckPlanning.NQ_SYSTEMCHECKS_PLANNING)
					.setParameter("organizations", organizations).getResultList();
			return SystemCheckPlanningConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

}
