package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.StaffHygieneConverter;
import com.gpi.scm.ejb.entities.Context;
import com.gpi.scm.ejb.entities.StaffHygiene;
import com.gpi.scm.ejb.entities.UserRole;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.ejb.entities.PrerequisiteType;
import com.gpi.scm.generic.dtos.BasePrerequisiteDto;
import com.gpi.scm.generic.dtos.StaffHygieneDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.StaffHygieneLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class StaffHygieneBean extends GenericBean implements StaffHygieneLocal {
	private static final Logger logger = Logger.getLogger(StaffHygieneBean.class);

	@Override
	public boolean deleteStaffHygiene(long id) throws BusinessException {
		StaffHygiene toDelete= entityManager.find(StaffHygiene.class, id);
		if(toDelete==null||toDelete.getDeleted())
		{
			return false;
		}
		toDelete.setDeleted(true);
		return true;
	}

	@Override
	public StaffHygieneDto editStaffHygiene(StaffHygieneDto toSave) throws BusinessException {
		StaffHygiene hygiene= entityManager.find(StaffHygiene.class, toSave.getId());
		if(hygiene==null||hygiene.getDeleted())
		{
			throw new NoResultException("StaffHygiene not found!");
		}
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if(organization==null||organization.getDeleted())
		{
			throw new NoResultException("Organization not found!");
		}
		PrerequisiteType type= entityManager.find(PrerequisiteType.class, toSave.getPrerequisiteType().getId());
		if(type==null||type.getDeleted())
		{
			throw new NoResultException("PrerequisiteType not found!");
		}
		UserRole role = entityManager.find(UserRole.class, toSave.getRole().getId());
		if(role==null||role.getDeleted())
		{
			throw new NoResultException("UserRole not found!");
		}
		
		if (toSave.getContext() != null) {
			Context context = entityManager.find(Context.class, toSave.getContext().getId());
			if (context == null || context.getDeleted()) {
				throw new NoResultException("Context not found");
			}
			hygiene.setContext(context);

		}
		hygiene.setRole(role);
		hygiene.setPrerequisiteType(type);
		hygiene.setOrganization(organization);
		
		return StaffHygieneConverter.entityToDto(entityManager.merge(hygiene), true);
	}

	@Override
	public StaffHygieneDto newStaffHygiene(StaffHygieneDto toSave) throws BusinessException {
		
		StaffHygiene hygiene= StaffHygieneConverter.dtoToEntity(toSave);
		PrerequisiteType type= entityManager.find(PrerequisiteType.class, toSave.getPrerequisiteType().getId());
		if(type==null||type.getDeleted())
		{
			throw new NoResultException("PrerequisiteType not found!");
		}
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if(organization==null||organization.getDeleted())
		{
			throw new NoResultException("Organization not found!");
		}
		UserRole role = entityManager.find(UserRole.class, toSave.getRole().getId());
		if(role==null||role.getDeleted())
		{
			throw new NoResultException("UserRole not found!");
		}
		
		hygiene.setRole(role);
		hygiene.setOrganization(organization);
		hygiene.setPrerequisiteType(type);
		Context context = new Context();
		context.setClassName(this.getClass().getSimpleName());
		entityManager.persist(context);
		hygiene.setContext(context);
		
		return StaffHygieneConverter.entityToDto(entityManager.merge(hygiene), true);
		
		
	}

	@Override
	public List<StaffHygieneDto> findStaffHygiene(Long organizationId) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			
			List<StaffHygiene> entities = (List<StaffHygiene>) entityManager.createNamedQuery(StaffHygiene.NQ_STAFFHYGIENE_BY_ORG)
					.setParameter("idOrganization", organizationId).getResultList();
			return StaffHygieneConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

	@Override
	public StaffHygieneDto findById(long id) throws BusinessException {
		StaffHygiene toFind= entityManager.find(StaffHygiene.class, id);
		if(toFind==null||toFind.getDeleted())
		{
			return null;
		}
		return StaffHygieneConverter.entityToDto(toFind,true);
	}

	@Override
	public StaffHygieneDto findByContextId(long id,List<Long> organizations) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			StaffHygiene supply = (StaffHygiene) entityManager.createNamedQuery(StaffHygiene.NQ_STAFFHYGIENE_BY_CONTEXT_ID)
					.setParameter("idContext", id).getSingleResult();
			return StaffHygieneConverter.entityToDto(supply, true);

		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return null;
	}

}
