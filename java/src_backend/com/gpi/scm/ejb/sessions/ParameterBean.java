package com.gpi.scm.ejb.sessions;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.ParameterConverter;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Parameter;
import com.gpi.scm.generic.dtos.ParameterDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.ParameterLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class ParameterBean extends GenericBean implements ParameterLocal {

	private static final Logger logger = Logger.getLogger(ParameterBean.class);

	@Override
	public ParameterDto getParameter(String key, Long idOrganization) throws BusinessException {

		ParameterDto result = null;
		try {

			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);

			Parameter entity = (Parameter) entityManager.createNamedQuery(Parameter.NQ_GET_ORG_PARAMETER_BY_KEY)
					.setParameter(Parameter.NQ_PARAM_KEY, key)
					.setParameter(Parameter.NQ_PARAM_ID_ORGANIZATION, idOrganization).getSingleResult();

			result = ParameterConverter.entityToDto(entity, false);

		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}

		return result;
	}

	@Override
	public ParameterDto getParameterByOrganizationCode(String key, String orgCode) throws BusinessException {

		ParameterDto result = null;
		try {

			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);

			Parameter entity = (Parameter) entityManager
					.createNamedQuery(Parameter.NQ_GET_ORG_PARAMETER_BY_KEY_AND_ORG_CODE)
					.setParameter(Parameter.NQ_PARAM_KEY, key)
					.setParameter(Parameter.NQ_PARAM_ORGANIZATION_CODE, orgCode).getSingleResult();

			result = ParameterConverter.entityToDto(entity, false);

		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}

		return result;
	}

}
