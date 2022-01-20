package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.SystemCheckRequirementConverter;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.SystemCheck;
import com.gpi.scm.ejb.entities.SystemCheckRequirement;
import com.gpi.scm.generic.dtos.SystemCheckRequirementDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.SystemCheckRequirementLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class SystemCheckRequirementBean extends GenericBean implements SystemCheckRequirementLocal {
	private static final Logger logger = Logger.getLogger(SystemCheckRequirementBean.class);


	@Override
	public SystemCheckRequirementDto saveRequirement(SystemCheckRequirementDto toSave)
			throws BusinessException {
		SystemCheck sysCheck = entityManager.find(SystemCheck.class, toSave.getSystemCheck().getId());
		if (sysCheck == null || sysCheck.getDeleted()) {
			throw new NoResultException("SystemCheck not found!");
		}
		SystemCheckRequirement syscheckRequirement = SystemCheckRequirementConverter.dtoToEntity(toSave);
		syscheckRequirement.setSystemCheck(sysCheck);

		SystemCheckRequirement tmp = entityManager.merge(syscheckRequirement);

		return SystemCheckRequirementConverter.entityToDto(tmp, true);
	}

	@Override
	public SystemCheckRequirementDto editRequirement(SystemCheckRequirementDto toSave)
			throws BusinessException {
		SystemCheckRequirement syscheckRequirement = entityManager.find(SystemCheckRequirement.class, toSave.getId());
		if (syscheckRequirement == null || syscheckRequirement.getDeleted()) {
			throw new NoResultException("SystemCheckRequirements not found!");
		}
		SystemCheck sysCheck = entityManager.find(SystemCheck.class, toSave.getSystemCheck().getId());
		if (sysCheck == null || sysCheck.getDeleted()) {
			throw new NoResultException("SystemCheck not found!");
		}
		syscheckRequirement.setSystemCheck(sysCheck);
		syscheckRequirement.setName(toSave.getName());
		syscheckRequirement.setDescription(toSave.getDescription());
		
		SystemCheckRequirement tmp = entityManager.merge(syscheckRequirement);

		return SystemCheckRequirementConverter.entityToDto(tmp, true);
	}

	@Override
	public boolean deleteRequirement(long id) throws BusinessException {
		SystemCheckRequirement toDelete = entityManager.find(SystemCheckRequirement.class, id);
		if (toDelete == null || toDelete.getDeleted()) {
			return false;
		}
		toDelete.setDeleted(true);
		return true;
	}

	@Override
	public List<SystemCheckRequirementDto> findRequirement(Long systemCheckId) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);

			List<SystemCheckRequirement> entities = (List<SystemCheckRequirement>) entityManager
					.createNamedQuery(SystemCheckRequirement.NQ_SYSTEMCHECKS_REQUIREMENT_BY_SYSCHECK).setParameter("systemCheckId", systemCheckId)
					.getResultList();
			return SystemCheckRequirementConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

}
