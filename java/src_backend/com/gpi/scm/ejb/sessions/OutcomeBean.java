package com.gpi.scm.ejb.sessions;

import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.OutcomeConverter;
import com.gpi.scm.ejb.entities.Context;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Monitoring;
import com.gpi.scm.ejb.entities.Outcome;
import com.gpi.scm.ejb.entities.User;
import com.gpi.scm.generic.dtos.OutcomeDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.OutcomeLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class OutcomeBean extends GenericBean implements OutcomeLocal {

	private static final Logger logger = Logger.getLogger(OutcomeBean.class);

	@Override
	public OutcomeDto newOutcome(OutcomeDto toSave) throws BusinessException {

		User user = entityManager.find(User.class, toSave.getUser().getId());
		if (user == null || user.getDeleted()) {
			throw new NoResultException("User Not found");
		}
		Monitoring monitoring = entityManager.find(Monitoring.class, toSave.getMonitoring().getId());
		if (monitoring == null || monitoring.getDeleted()) {
			throw new NoResultException("Monitoring Not found");
		}

		Context context = new Context();
		context.setClassName(this.getClass().getSimpleName());
		entityManager.persist(context);

		Outcome outcome = OutcomeConverter.dtoToEntity(toSave);
		outcome.setContext(context);
		outcome.setUser(user);
		outcome.setMonitoring(monitoring);

		Outcome tmp = entityManager.merge(outcome);
		return OutcomeConverter.entityToDto(tmp, true);

	}

	@Override
	public OutcomeDto editOutcome(OutcomeDto toSave) throws BusinessException {

		Outcome outcome = entityManager.find(Outcome.class, toSave.getId());
		if (outcome == null || outcome.getDeleted()) {
			throw new NoResultException("Outcome not found!");
		}
		Context context = entityManager.find(Context.class, toSave.getContext().getId());
		if (context == null || context.getDeleted()) {
			throw new NoResultException("Context Not found");
		}
		User user = entityManager.find(User.class, toSave.getUser().getId());
		if (user == null || user.getDeleted()) {
			throw new NoResultException("User Not found");
		}
		Monitoring monitoring = entityManager.find(Monitoring.class, toSave.getMonitoring().getId());
		if (monitoring == null || monitoring.getDeleted()) {
			throw new NoResultException("Monitoring Not found");
		}
		outcome.setMonitoring(monitoring);
		outcome.setContext(context);
		outcome.setUser(user);
		outcome.setNote(toSave.getNote());
		outcome.setResult(toSave.isResult());

		return OutcomeConverter.entityToDto(entityManager.merge(outcome), true);
	}

	@Override
	public boolean deleteOutcome(long id) throws BusinessException {
		Outcome toDelete = entityManager.find(Outcome.class, id);
		if (toDelete == null || toDelete.getDeleted()) {
			return false;
		}
		toDelete.setDeleted(true);
		return true;
	}

	@Override
	public OutcomeDto findOutcomeByMonitorId(long id, List<Long> organizations) throws BusinessException {
		try {

			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);

			Outcome outcomes = (Outcome) entityManager.createNamedQuery(Outcome.NQ_OUTCOME_BY_MONITOR_ID)
					.setParameter("idMonitoring", id).setParameter("organizations", organizations).setMaxResults(1)
					.getSingleResult();
			return OutcomeConverter.entityToDto(outcomes, true);

		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return null;
	}
}
