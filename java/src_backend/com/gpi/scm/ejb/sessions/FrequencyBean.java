package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.FrequencyConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.Frequency;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.ejb.entities.PrerequisiteType;
import com.gpi.scm.ejb.entities.RiskClass;
import com.gpi.scm.generic.dtos.FrequencyDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.FrequencyLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class FrequencyBean extends GenericBean implements FrequencyLocal {
	private static final Logger logger = Logger.getLogger(FrequencyBean.class);


	@Override
	public List<FrequencyDto> findFrequency(List<Long> organizations) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			List<Frequency> entities = (List<Frequency>) entityManager
					.createNamedQuery(Frequency.NQ_FREQUENCIES_IN_ORGANIZATIONS)
					.setParameter("organizations", organizations).getResultList();
			return FrequencyConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

	@Override
	public boolean deleteFrequency(long id) throws BusinessException {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			Frequency toDelete = (Frequency) entityManager.createNamedQuery(Frequency.NQ_FREQUENCY_BY_ID)
					.setParameter("freqId", id).getSingleResult();
			toDelete.setDeleted(true);
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
	public FrequencyDto editFrequency(FrequencyDto toSave) throws BusinessException {
		RiskClass rclass = null;
		Frequency frequency = entityManager.find(Frequency.class, toSave.getId());
		if (frequency == null || frequency.getDeleted()) {
			throw new NoResultException("Frequency not found");
		}
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		if (!organizations.contains(toSave.getOrganization().getId()) || !organizations.contains(frequency.getOrganization().getId())) {
			throw new NoResultException("No access!");

		}
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}
		if (toSave.getType().equals("DEFAULT")) {
			rclass = entityManager.find(RiskClass.class, toSave.getRiskClass().getId());
			if (rclass == null || rclass.getDeleted()) {
				throw new NoResultException("RiskClass not found!");
			}
		}
		PrerequisiteType ptype = entityManager.find(PrerequisiteType.class, toSave.getPrerequisiteType().getId());
		if (ptype == null || ptype.getDeleted()) {
			throw new NoResultException("prerequisite type not found!");
		}
		frequency.setPeriod(toSave.getPeriod());
		frequency.setValue(toSave.getValue());
		frequency.setOrganization(organization);
		frequency.setPrerequisiteType(ptype);
		frequency.setRiskClass(rclass);
		frequency.setAsNeeded(toSave.isAsNeeded());
		frequency.setJustOnce(toSave.isJustOnce());
		frequency.setType(toSave.getType());
		return FrequencyConverter.entityToDto(entityManager.merge(frequency), true);

	}

	@Override
	public FrequencyDto newFrequency(FrequencyDto toSave) throws BusinessException {
		
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		if (!organizations.contains(toSave.getOrganization().getId())) {
			throw new NoResultException("No access!");

		}
		RiskClass rclass = null;
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}
		if (toSave.getType().equals("DEFAULT")) {
			rclass = entityManager.find(RiskClass.class, toSave.getRiskClass().getId());
			if (rclass == null || rclass.getDeleted()) {
				throw new NoResultException("RiskClass not found!");
			}
		}
		PrerequisiteType ptype = entityManager.find(PrerequisiteType.class, toSave.getPrerequisiteType().getId());
		if (ptype == null || ptype.getDeleted()) {
			throw new NoResultException("PrerequisiteType not found!");
		}

		Frequency freq = FrequencyConverter.dtoToEntity(toSave);
		freq.setRiskClass(rclass);
		freq.setOrganization(organization);
		freq.setPrerequisiteType(ptype);

		return FrequencyConverter.entityToDto(entityManager.merge(freq), true);

	}

}
