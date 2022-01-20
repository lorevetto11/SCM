package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.AnalysisParameterConverter;
import com.gpi.scm.ejb.entities.AnalysisParameter;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.ejb.entities.UserRole;
import com.gpi.scm.generic.dtos.AnalysisParameterDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.AnalysisParameterLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class AnalysisParameterBean extends GenericBean implements AnalysisParameterLocal {
	private static final Logger logger = Logger.getLogger(AnalysisParameterBean.class);

	@Override
	public boolean deleteAnalisisParameter(long id) throws BusinessException {
		AnalysisParameter toDelete= entityManager.find(AnalysisParameter.class, id);
		if(toDelete==null||toDelete.getDeleted())
		{
			return false;
		}
		toDelete.setDeleted(true);
		return true;
	}

	@Override
	public AnalysisParameterDto editAnalisisParameter(AnalysisParameterDto toSave) throws BusinessException {
		AnalysisParameter parameter= entityManager.find(AnalysisParameter.class, toSave.getId());
		if(parameter==null||parameter.getDeleted())
		{
			throw new NoResultException("AnalisisParameter not found!");
		}
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if(organization==null||organization.getDeleted())
		{
			throw new NoResultException("Organization not found!");
		}
		UserRole role =entityManager.find(UserRole.class, toSave.getUserRole().getId());
		if(role==null||role.getDeleted())
		{
			throw new NoResultException("Role not found!");
		}
		parameter.setOrganization(organization);
		parameter.setUserRole(role);
		parameter.setName(toSave.getName());
		parameter.setDescription(toSave.getDescription());
		parameter.setThresholdValue(toSave.getThresholdValue());
	

		
		return AnalysisParameterConverter.entityToDto(entityManager.merge(parameter), true);
	}

	@Override
	public AnalysisParameterDto newAnalisisParameter(AnalysisParameterDto toSave) throws BusinessException {
		
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if(organization==null||organization.getDeleted())
		{
			throw new NoResultException("Organization not found!");
		}
		UserRole role =entityManager.find(UserRole.class, toSave.getUserRole().getId());
		if(role==null||role.getDeleted())
		{
			throw new NoResultException("Role not found!");
		}
		
		AnalysisParameter parameter= AnalysisParameterConverter.dtoToEntity(toSave);
		parameter.setUserRole(role);
		parameter.setOrganization(organization);

		return AnalysisParameterConverter.entityToDto(entityManager.merge(parameter), true);
		
		
	}

	@Override
	public List<AnalysisParameterDto> findAnalisisParameter(Long organizationId) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			List<AnalysisParameter> entities = (List<AnalysisParameter>) entityManager.createNamedQuery(AnalysisParameter.NQ_PARAMETER_BY_ORG_ID)
					.setParameter("idOrg", organizationId).getResultList();
			return AnalysisParameterConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}


}
