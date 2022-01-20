package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.AirConditioningConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.Floor;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Layout;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.ejb.entities.PrerequisiteType;
import com.gpi.scm.ejb.entities.Shape;
import com.gpi.scm.ejb.entities.AirConditioning;
import com.gpi.scm.ejb.entities.Context;
import com.gpi.scm.generic.dtos.BasePrerequisiteDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.AirConditioningLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class AirConditioningBean extends GenericBean implements AirConditioningLocal {
	private static final Logger logger = Logger.getLogger(AirConditioningBean.class);

	@Override
	public List<BasePrerequisiteDto> findAirConditioning(Long organizations) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			List<AirConditioning> entities = (List<AirConditioning>) entityManager
					.createNamedQuery(AirConditioning.NQ_AIRCONDITIONINGS_IN_ORGANIZATIONS)
					.setParameter("organizations", organizations).getResultList();
			return AirConditioningConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

	@Override
	public BasePrerequisiteDto editAirConditioning(BasePrerequisiteDto toSave) throws BusinessException {
		

		AirConditioning conditioning = entityManager.find(AirConditioning.class, toSave.getId());
		if (conditioning == null || conditioning.getDeleted()) {
			throw new NoResultException("No AirConditioning Found!");
		}
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		if (!organizations.contains(toSave.getOrganization().getId())||!organizations.contains(conditioning.getOrganization().getId())) {
			throw new NoResultException("No access!");

		}
	
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}

		PrerequisiteType ptype = entityManager.find(PrerequisiteType.class, toSave.getPrerequisiteType().getId());
		if (ptype == null || ptype.getDeleted()) {
			throw new NoResultException("PrerequisiteType not found!");
		}


		Layout layout = entityManager.find(Layout.class, toSave.getLayout().getId());
		if (layout == null || layout.getDeleted()) {
			throw new NoResultException("Context not found!");
		}

		Floor floor = entityManager.find(Floor.class, toSave.getFloor().getId());
		if (floor == null || floor.getDeleted()) {
			throw new NoResultException("Floor not found!");
		}

		Shape shape = entityManager.find(Shape.class, toSave.getShape().getId());
		if (shape == null || shape.getDeleted()) {
			throw new NoResultException("Shape not found!");
		}

		conditioning.setPrerequisiteType(ptype);
		conditioning.setLayout(layout);
		conditioning.setOrganization(organization);
		conditioning.setFloor(floor);
		conditioning.setShape(shape);
		conditioning.setName(toSave.getName());
		conditioning.setDescription(toSave.getDescription());

		AirConditioning tmp = entityManager.merge(conditioning);
		return AirConditioningConverter.entityToDto(tmp, true);
	}

	@Override
	public BasePrerequisiteDto newAirConditioning(BasePrerequisiteDto toSave) throws BusinessException {
		
		PrerequisiteType ptype = entityManager.find(PrerequisiteType.class, toSave.getPrerequisiteType().getId());
		if (ptype == null || ptype.getDeleted()) {
			throw new NoResultException("PrerequisiteType not found!");
		}

		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		if (!organizations.contains(toSave.getOrganization().getId()) || !organizations.contains(organization.getId())) {
			throw new NoResultException("No access !");
		}

		Layout layout = entityManager.find(Layout.class, toSave.getLayout().getId());
		if (layout == null || layout.getDeleted()) {
			throw new NoResultException("Layout not found!");
		}

		Floor floor = entityManager.find(Floor.class, toSave.getFloor().getId());
		if (floor == null || floor.getDeleted() ) {
			throw new NoResultException("Floor not found!");
		}

		Shape shape = entityManager.find(Shape.class, toSave.getShape().getId());
		if (shape == null || shape.getDeleted()) {
			throw new NoResultException("Shape not found!");
		}

		AirConditioning conditioning = AirConditioningConverter.dtoToEntity(toSave);

		Context context = new Context();
		context.setClassName(this.getClass().getSimpleName());
		entityManager.persist(context);

		conditioning.setContext(context);
		conditioning.setLayout(layout);
		conditioning.setOrganization(organization);
		conditioning.setFloor(floor);
		conditioning.setShape(shape);
		conditioning.setPrerequisiteType(ptype);

		AirConditioning tmp = entityManager.merge(conditioning);
		return AirConditioningConverter.entityToDto(tmp, true);
	}

	@Override
	public boolean deleteAirConditioning(long id) throws BusinessException {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
			
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			AirConditioning toDelete = (AirConditioning) entityManager
					.createNamedQuery(AirConditioning.NQ_AIRCONDITIONING_BY_ID).setParameter("idAirConditioning", id)
					.getSingleResult();
			toDelete.setDeleted(true);
			entityManager.merge(toDelete);
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
	public BasePrerequisiteDto findAirConditioningById(long id) throws BusinessException {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
			
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			AirConditioning conditioning = (AirConditioning) entityManager
					.createNamedQuery(AirConditioning.NQ_AIRCONDITIONING_BY_ID).setParameter("idAirConditioning", id)
					.getSingleResult();
			return AirConditioningConverter.entityToDto(conditioning, true);

		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return null;
	}
	
	@Override
	public BasePrerequisiteDto findByContextId(long id,List<Long> organizations) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			AirConditioning airConditioning = (AirConditioning) entityManager.createNamedQuery(AirConditioning.NQ_AIRCONDITIONING_BY_CONTEXT_ID)
					.setParameter("idContext", id).getSingleResult();
			return AirConditioningConverter.entityToDto(airConditioning, true);

		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return null;
	}

}
