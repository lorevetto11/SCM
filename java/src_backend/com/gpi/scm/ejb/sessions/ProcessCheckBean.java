package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.ProcessCheckConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.Frequency;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.ejb.entities.PrerequisiteType;
import com.gpi.scm.ejb.entities.ProcessCheck;
import com.gpi.scm.generic.dtos.ProcessCheckDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.ProcessCheckLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)

public class ProcessCheckBean extends GenericBean implements ProcessCheckLocal {
	private static final Logger logger = Logger.getLogger(ProcessCheckBean.class);

	@Override
	public ProcessCheckDto saveCheck(ProcessCheckDto toSave) throws BusinessException {
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}
		Frequency frequency = entityManager.find(Frequency.class, toSave.getFrequency().getId());
		if (frequency == null || frequency.getDeleted()) {
			
		} 
		PrerequisiteType ptype = entityManager.find(PrerequisiteType.class, toSave.getPrerequisiteType().getId());
		if (ptype == null || ptype.getDeleted()) {
			throw new NoResultException("PrerequisiteType not found!");
		}
	
		ProcessCheck syscheck = ProcessCheckConverter.dtoToEntity(toSave);
		syscheck.setOrganization(organization);
		syscheck.setFrequency(frequency);
		syscheck.setPrerequisiteType(ptype);

		ProcessCheck tmp = entityManager.merge(syscheck);
		return ProcessCheckConverter.entityToDto(tmp, true);
	}

	@Override
	public ProcessCheckDto editCheck(ProcessCheckDto toSave) throws BusinessException {
		ProcessCheck syscheck = entityManager.find(ProcessCheck.class, toSave.getId());
		if (syscheck == null || syscheck.getDeleted()) {
			throw new NoResultException("Check not found");
		}
		
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if(!organizations.contains(syscheck.getOrganization().getId()))
		{
			throw new NoResultException("No access!");

		}
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}
		Frequency frequency = entityManager.find(Frequency.class, toSave.getFrequency().getId());
		if (frequency == null || frequency.getDeleted()) {
			
		} 
		
		PrerequisiteType ptype = entityManager.find(PrerequisiteType.class, toSave.getPrerequisiteType().getId());
		if (ptype == null || ptype.getDeleted()) {
			throw new NoResultException("PrerequisiteType not found!");
		}
		
		syscheck.setFrequency(frequency);
		syscheck.setPrerequisiteType(ptype);
		syscheck.setName(toSave.getName());
		syscheck.setDescription(toSave.getDescription());
		syscheck.setOrganization(organization);
		syscheck.setPrivacy(toSave.isPrivacy());
		entityManager.persist(syscheck);

		return ProcessCheckConverter.entityToDto(syscheck, true);
	}

	@Override
	public boolean deleteCheck(long id) throws BusinessException {
		
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		ProcessCheck toDelete = entityManager.find(ProcessCheck.class, id);
		if (toDelete == null || toDelete.getDeleted() || !organizations.contains(toDelete.getOrganization().getId())) {
			return false;
		}
		toDelete.setDeleted(true);
		return true;
	}

	@Override
	public List<ProcessCheckDto> findChecks(Long organizationId) throws BusinessException {

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

			List<ProcessCheck> entitiesFalse = (List<ProcessCheck>) entityManager.createNamedQuery(ProcessCheck.NQ_PROCESSCHECKS)
					.getResultList();
			List<ProcessCheck> entitiesTrue = (List<ProcessCheck>) entityManager.createNamedQuery(ProcessCheck.NQ_PROCESSCHECKS_TRUE)
					.setParameter("organizations", organizations)
					.getResultList();
			
			entitiesFalse.addAll(entitiesTrue);
			return ProcessCheckConverter.entityToDto(entitiesFalse, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

}
