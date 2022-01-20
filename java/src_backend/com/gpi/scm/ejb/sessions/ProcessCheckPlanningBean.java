package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import com.gpi.scm.converters.ProcessCheckPlanningConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.ejb.entities.ProcessCheck;
import com.gpi.scm.ejb.entities.ProcessCheckPlanning;
import com.gpi.scm.generic.dtos.ProcessCheckDto;
import com.gpi.scm.generic.dtos.ProcessCheckPlanningDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.ProcessCheckPlanningLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)

public class ProcessCheckPlanningBean extends GenericBean implements ProcessCheckPlanningLocal {
	private static final Logger logger = Logger.getLogger(ProcessCheckPlanningBean.class);

	@Override
	public ProcessCheckPlanningDto savePlanning(ProcessCheckPlanningDto toSave) throws BusinessException {
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}
		Organization company = entityManager.find(Organization.class, toSave.getCompany().getId());
		if (company == null || company.getDeleted()) {
			throw new NoResultException("Company not found!");
		}
		List<ProcessCheckDto> processcheckToInsert = toSave.getProcesschecks();
		List<ProcessCheck> effectiveProcessChecks = new ArrayList<>();
		if (!processcheckToInsert.isEmpty()) {
			for (ProcessCheckDto processcheck : processcheckToInsert) {
				effectiveProcessChecks.add(entityManager.find(ProcessCheck.class, processcheck.getId()));
			}
		}

		ProcessCheckPlanning syscheck = ProcessCheckPlanningConverter.dtoToEntity(toSave);
		syscheck.setProcessChecks(effectiveProcessChecks);
		syscheck.setCompany(company);
		syscheck.setOrganization(organization);

		ProcessCheckPlanning tmp = entityManager.merge(syscheck);

		return ProcessCheckPlanningConverter.entityToDto(tmp, true);
	}

	@Override
	public ProcessCheckPlanningDto editPlanning(ProcessCheckPlanningDto toSave) throws BusinessException {
		ProcessCheckPlanning syscheck = entityManager.find(ProcessCheckPlanning.class, toSave.getId());
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
		
		
		List<ProcessCheckDto> processcheckToInsert = toSave.getProcesschecks();
		List<ProcessCheck> effectiveProcessChecks = new ArrayList<>();
		if (!processcheckToInsert.isEmpty()) {
			for (ProcessCheckDto processcheck : processcheckToInsert) {
				effectiveProcessChecks.add(entityManager.find(ProcessCheck.class, processcheck.getId()));
			}
		}

		syscheck.setProcessChecks(effectiveProcessChecks);
		syscheck.setStatus(toSave.getStatus());
		syscheck.setDate(toSave.getDate());
		syscheck.setStartDate(toSave.getStartDate());
		syscheck.setCloseDate(toSave.getCloseDate());
		syscheck.setCompany(company);
		syscheck.setOrganization(organization);

		entityManager.persist(syscheck);

		return ProcessCheckPlanningConverter.entityToDto(syscheck, true);
	}

	@Override
	public boolean deletePlanning(long id) throws BusinessException {
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());

		ProcessCheckPlanning toDelete = entityManager.find(ProcessCheckPlanning.class, id);
		if (toDelete == null || toDelete.getDeleted() || !organizations.contains(toDelete.getOrganization().getId())) {
			return false;
		}
		toDelete.setDeleted(true);
		return true;
	}

	@Override
	public List<ProcessCheckPlanningDto> findPlanning(Long organizations) throws BusinessException {

		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);

			List<ProcessCheckPlanning> entities = (List<ProcessCheckPlanning>) entityManager
					.createNamedQuery(ProcessCheckPlanning.NQ_PROCESSCHECKS_PLANNING)
					.setParameter("organizations", organizations).getResultList();
			return ProcessCheckPlanningConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

}
