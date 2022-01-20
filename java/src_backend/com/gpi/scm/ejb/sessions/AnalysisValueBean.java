package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.AnalysisValueConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.AnalysisParameter;
import com.gpi.scm.ejb.entities.AnalysisValue;
import com.gpi.scm.ejb.entities.Context;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.generic.dtos.AnalysisValueDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.AnalysisValueLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class AnalysisValueBean extends GenericBean implements AnalysisValueLocal {
	private static final Logger logger = Logger.getLogger(AnalysisValueBean.class);

	@Override
	public boolean deleteAnalisisValue(long id) throws BusinessException {
		AnalysisValue toDelete = entityManager.find(AnalysisValue.class, id);
		if (toDelete == null || toDelete.getDeleted()) {
			return false;
		}
		toDelete.setDeleted(true);
		return true;
	}

	@Override
	public AnalysisValueDto editAnalisisValue(AnalysisValueDto toSave) throws BusinessException {
		AnalysisValue value = entityManager.find(AnalysisValue.class, toSave.getId());
		if (value == null || value.getDeleted()) {
			throw new NoResultException("AnalisisValue not found!");
		}
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}
		AnalysisParameter parameter = entityManager.find(AnalysisParameter.class,
				toSave.getAnalysisParameter().getId());
		if (parameter == null || parameter.getDeleted()) {
			throw new NoResultException("AnalisisParameter not found!");

		}
		if (toSave.getContext() != null) {
			Context context = entityManager.find(Context.class, toSave.getContext().getId());
			if (context == null || context.getDeleted()) {
				throw new NoResultException("Context not found");

			}
			value.setContext(context);
		}
		value.setAnalysisParameter(parameter);
		value.setOrganization(organization);
		value.setDate(toSave.getDate());
		value.setNote(toSave.getNote());
		value.setValue(toSave.getValue());

		return AnalysisValueConverter.entityToDto(entityManager.merge(value), true);
	}

	@Override
	public AnalysisValueDto newAnalisisValue(AnalysisValueDto toSave) throws BusinessException {
		AnalysisValue value = AnalysisValueConverter.dtoToEntity(toSave);
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}
		AnalysisParameter parameter = entityManager.find(AnalysisParameter.class,
				toSave.getAnalysisParameter().getId());
		if (parameter == null || parameter.getDeleted()) {
			throw new NoResultException("AnalisisParameter not found!");

		}
		if (toSave.getContext() != null) {
			Context context = entityManager.find(Context.class, toSave.getContext().getId());
			if (context == null || context.getDeleted()) {
				throw new NoResultException("Context not found");

			}
			value.setContext(context);
		}
		value.setAnalysisParameter(parameter);
		value.setOrganization(organization);

		return AnalysisValueConverter.entityToDto(entityManager.merge(value), true);

	}

	@Override
	public List<AnalysisValueDto> findAnalisisValue(Long parameterId) throws BusinessException {
		try {
			long organization = UserContextHolder.getUser().getOrganization().getId();
			List<Long> organizations = OrganizationDelegate.organizationsIdsTree(organization);
			AnalysisParameter parameter = entityManager.find(AnalysisParameter.class,parameterId);
			if (parameter == null || parameter.getDeleted() || !organizations.contains(parameter.getOrganization().getId())) {
				
				throw new NoResultException("AnalisisParameter not found or cannot be accessed!");

			}
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			List<AnalysisValue> entities = (List<AnalysisValue>) entityManager
					.createNamedQuery(AnalysisValue.NQ_VALUE_BY_PARAMETER_ID).setParameter("idParameter", parameterId)
					.getResultList();
			return AnalysisValueConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

}
