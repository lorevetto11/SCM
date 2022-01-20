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

import com.gpi.scm.converters.SystemCheckOutcomeConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.Context;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.NonCompliance;
import com.gpi.scm.ejb.entities.SystemCheckOutcome;
import com.gpi.scm.ejb.entities.SystemCheckPlanning;
import com.gpi.scm.ejb.entities.SystemCheckRequirement;
import com.gpi.scm.generic.dtos.SystemCheckOutcomeDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.SystemCheckOutcomeLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)

public class SystemCheckOutcomeBean extends GenericBean implements SystemCheckOutcomeLocal {
	private static final Logger logger = Logger.getLogger(SystemCheckOutcomeBean.class);

	@Override
	public SystemCheckOutcomeDto saveOutcome(SystemCheckOutcomeDto toSave) throws BusinessException {

		int atLeastOne = 0;
		SystemCheckOutcome outcome = SystemCheckOutcomeConverter.dtoToEntity(toSave);

		if (toSave.getSystemcheckPlanning() != null) {
			SystemCheckPlanning planning = entityManager.find(SystemCheckPlanning.class,
					toSave.getSystemcheckPlanning().getId());
			if (planning == null || planning.getDeleted()) {
				throw new NoResultException("No SystemCheckPlanning found !");
			}
			outcome.setSystemcheckPlanning(planning);
			atLeastOne++;
		}
		if (toSave.getSystemcheckRequirement() != null) {
			SystemCheckRequirement requirement = entityManager.find(SystemCheckRequirement.class,
					toSave.getSystemcheckRequirement().getId());
			if (requirement == null || requirement.getDeleted()) {
				throw new NoResultException("No SystemCheckRequirement found !");
			}
			outcome.setSystemcheckRequirement(requirement);
			atLeastOne++;
		}
		if (toSave.getNonCompliance() != null) {
			NonCompliance noncompliance = entityManager.find(NonCompliance.class, toSave.getNonCompliance().getId());
			if (noncompliance == null || noncompliance.getDeleted()) {
				throw new NoResultException("NonCompliance not found !");
			}
			outcome.setNonCompliance(noncompliance);
			atLeastOne++;
		}
		if (atLeastOne == 0) {
			throw new NoResultException("SystemCheckOutcome is not associated with any kind of context");
		}
		Context context = new Context();
		context.setClassName(this.getClass().getSimpleName());
		entityManager.persist(context);

		outcome.setContext(context);
		outcome.setEvidence(toSave.getEvidence());

		return SystemCheckOutcomeConverter.entityToDto(entityManager.merge(outcome), true);

	}

	@Override
	public SystemCheckOutcomeDto editOutcome(SystemCheckOutcomeDto toSave) throws BusinessException {

		long organization = UserContextHolder.getUser().getOrganization().getId();
		List<Long> organizations = OrganizationDelegate.organizationsIdsTree(organization);

		SystemCheckOutcome outcome = entityManager.find(SystemCheckOutcome.class, toSave.getId());
		if (outcome == null || outcome.getDeleted()) {
			throw new NoResultException("Outcome not found!");
		}
		Session ses = (Session) entityManager.getDelegate();
		ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);

		List<Object[]> entities = entityManager
				.createNamedQuery(SystemCheckOutcome.NQ_SYSTEMCHECK_OUTCOMES_ORGANIZATIONS)
				.setParameter("outcomeId", toSave.getId()).getResultList();
		
		Set<Long> orgs = new HashSet<Long>();
		if (!entities.isEmpty()) {
			for (Object[] num : entities) {
				if (num[0] != null)
					orgs.add((Long) num[0]);
				if (num[1] != null)
					orgs.add((Long) num[1]);
			}
		}
		if(!organizations.containsAll(orgs))
		{
			throw new NoResultException("Organization not found!");
		}
	/*	SystemCheckPlanning planning = outcome.getSystemcheckPlanning();
		if (planning != null && !planning.getDeleted() && !organizations.contains(planning.getOrganization().getId())) {
			throw new NoResultException("Organization does not match!");
		}
		SystemCheckRequirement requirement = outcome.getSystemcheckRequirement();
		if (requirement != null && !requirement.getDeleted()
				&& !organizations.contains(planning.getOrganization().getId())) {
			throw new NoResultException("Organization does not match!");

		}
		NonCompliance noncompliance = outcome.getNonCompliance();
		if (noncompliance != null && !noncompliance.getDeleted()
				&& !organizations.contains(noncompliance.getOrganization().getId())) {
			throw new NoResultException("Organization does not match!");
		}*/

		outcome.setEvidence(toSave.getEvidence());

		return SystemCheckOutcomeConverter.entityToDto(entityManager.merge(outcome), true);
	}

	@Override
	public boolean deleteOutcome(long id) throws BusinessException {
		long organization = UserContextHolder.getUser().getOrganization().getId();
		List<Long> organizations = OrganizationDelegate.organizationsIdsTree(organization);
		
		SystemCheckOutcome toDelete = entityManager.find(SystemCheckOutcome.class, id);
		if (toDelete == null || toDelete.getDeleted()) {
			throw new NoResultException("Outcome not found!");
		}
		Session ses = (Session) entityManager.getDelegate();
		ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);

		List<Object[]> entities = entityManager
				.createNamedQuery(SystemCheckOutcome.NQ_SYSTEMCHECK_OUTCOMES_ORGANIZATIONS)
				.setParameter("outcomeId", id).getResultList();
		
		Set<Long> orgs = new HashSet<Long>();
		if (!entities.isEmpty()) {
			for (Object[] num : entities) {
				if (num[0] != null)
					orgs.add((Long) num[0]);
				if (num[1] != null)
					orgs.add((Long) num[1]);
			}
		}
		if(!organizations.containsAll(orgs))
		{
			return false;
		}
		
		toDelete.setDeleted(true);
		return true;
	}

	@Override
	public List<SystemCheckOutcomeDto> findOutcome(Long systemCheckRequirementId) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);

			List<SystemCheckOutcome> entities = (List<SystemCheckOutcome>) entityManager
					.createNamedQuery(SystemCheckOutcome.NQ_SYSTEMCHECKS_OUTCOME_BY_SYSCHECK_REQUIREMENT)
					.setParameter("systemCheckRequirementId", systemCheckRequirementId).setMaxResults(1)
					.getResultList();
			return SystemCheckOutcomeConverter.entityToDto(entities, true);

		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

}
