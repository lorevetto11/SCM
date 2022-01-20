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

import com.gpi.scm.converters.ProcessCheckOutcomeConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.Context;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.NonCompliance;
import com.gpi.scm.ejb.entities.ProcessCheck;
import com.gpi.scm.ejb.entities.ProcessCheckOutcome;
import com.gpi.scm.ejb.entities.ProcessCheckPlanning;
import com.gpi.scm.generic.dtos.ProcessCheckOutcomeDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.ProcessCheckOutcomeLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)

public class ProcessCheckOutcomeBean extends GenericBean implements ProcessCheckOutcomeLocal {
	private static final Logger logger = Logger.getLogger(ProcessCheckOutcomeBean.class);

	@Override
	public ProcessCheckOutcomeDto saveOutcome(ProcessCheckOutcomeDto toSave) throws BusinessException {
		int atLeastOne = 0;
		ProcessCheckOutcome outcome = ProcessCheckOutcomeConverter.dtoToEntity(toSave);
		if (toSave.getProcessCheck() != null) {
			ProcessCheck processCheck = entityManager.find(ProcessCheck.class, toSave.getProcessCheck().getId());
			if (processCheck == null || processCheck.getDeleted()) {
				throw new NoResultException("No ProcessCheck found !");
			}
			atLeastOne++;
			outcome.setProcessCheck(processCheck);
		}
		if (toSave.getProcesscheckPlanning() != null) {
			ProcessCheckPlanning planning = entityManager.find(ProcessCheckPlanning.class,
					toSave.getProcesscheckPlanning().getId());
			if (planning == null || planning.getDeleted()) {
				throw new NoResultException("No ProcessCheckPlanning found !");
			}
			atLeastOne++;
			outcome.setProcesscheckPlanning(planning);
		}
		if (toSave.getNonCompliance() != null) {
			NonCompliance noncompliance = entityManager.find(NonCompliance.class, toSave.getNonCompliance().getId());
			if (noncompliance == null || noncompliance.getDeleted()) {
				throw new NoResultException("NonCompliance not found !");
			}
			atLeastOne++;
			outcome.setNonCompliance(noncompliance);
		}
		if (atLeastOne == 0) {
			throw new NoResultException("ProcessCheckOutcome is not associated with any kind of context");
		}

		Context context = new Context();
		context.setClassName(this.getClass().getSimpleName());
		entityManager.persist(context);

		outcome.setContext(context);
		outcome.setEvidence(toSave.getEvidence());

		return ProcessCheckOutcomeConverter.entityToDto(entityManager.merge(outcome), true);

	}

	@Override
	public ProcessCheckOutcomeDto editOutcome(ProcessCheckOutcomeDto toSave) throws BusinessException {

		long organization = UserContextHolder.getUser().getOrganization().getId();
		List<Long> organizations = OrganizationDelegate.organizationsIdsTree(organization);

		ProcessCheckOutcome outcome = entityManager.find(ProcessCheckOutcome.class, toSave.getId());

		if (outcome == null || outcome.getDeleted()) {
			throw new NoResultException("No ProcessOutcome found !");
		}
		Session ses = (Session) entityManager.getDelegate();
		ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);

		List<Object[]> entities = entityManager
				.createNamedQuery(ProcessCheckOutcome.NQ_PROCESSCHECK_OUTCOMES_ORGANIZATIONS)
				.setParameter("outcomeId", toSave.getId()).getResultList();

		Set<Long> orgs = new HashSet<Long>();
		if (!entities.isEmpty()) {
			for (Object[] num : entities) {
				if (num[0] != null)
					orgs.add((Long) num[0]);
				if (num[1] != null)
					orgs.add((Long) num[1]);
				if (num[2] != null)
					orgs.add((Long) num[2]);
			}
		}
		if (!organizations.containsAll(orgs)) {
			throw new NoResultException("Organization not found!");
		}

		outcome.setEvidence(toSave.getEvidence());

		return ProcessCheckOutcomeConverter.entityToDto(entityManager.merge(outcome), true);
	}

	@Override
	public boolean deleteOutcome(long id) throws BusinessException {
		long organization = UserContextHolder.getUser().getOrganization().getId();
		List<Long> organizations = OrganizationDelegate.organizationsIdsTree(organization);
		ProcessCheckOutcome toDelete = entityManager.find(ProcessCheckOutcome.class, id);
		if (toDelete == null || toDelete.getDeleted()) {
			return false;
		}
		Session ses = (Session) entityManager.getDelegate();
		ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);

		List<Object[]> entities = entityManager
				.createNamedQuery(ProcessCheckOutcome.NQ_PROCESSCHECK_OUTCOMES_ORGANIZATIONS)
				.setParameter("outcomeId", id).getResultList();

		Set<Long> orgs = new HashSet<Long>();
		if (!entities.isEmpty()) {
			for (Object[] num : entities) {
				if (num[0] != null)
					orgs.add((Long) num[0]);
				if (num[1] != null)
					orgs.add((Long) num[1]);
				if (num[2] != null)
					orgs.add((Long) num[2]);
			}
		}
		if (!organizations.containsAll(orgs)) {
			return false;
		}
		toDelete.setDeleted(true);
		return true;
	}

	@Override
	public List<ProcessCheckOutcomeDto> findOutcome(Long processCheckPlanningId, Long processCheckId)
			throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);

			List<ProcessCheckOutcome> entities = (List<ProcessCheckOutcome>) entityManager
					.createNamedQuery(ProcessCheckOutcome.NQ_PROCESSCHECKS_OUTCOME_BY_PLANNING)
					.setParameter("processCheckId", processCheckId)
					.setParameter("processCheckPlanningId", processCheckPlanningId).setMaxResults(1).getResultList();
			return ProcessCheckOutcomeConverter.entityToDto(entities, true);

		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

}
