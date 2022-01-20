package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.PrerequisiteTypeConverter;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.PrerequisiteType;
import com.gpi.scm.generic.dtos.PrerequisiteTypeDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.PrerequisiteTypeLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)

public class PrerequisiteTypeBean extends GenericBean implements PrerequisiteTypeLocal {
	private static final Logger logger = Logger.getLogger(PrerequisiteTypeBean.class);

	@Override
	public List<PrerequisiteTypeDto> findPrerequisiteType() throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			
			List<PrerequisiteType> entities = (List<PrerequisiteType>) entityManager
					.createNamedQuery(PrerequisiteType.NQ_PRPTYPES)
					.getResultList();
			return PrerequisiteTypeConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

}
