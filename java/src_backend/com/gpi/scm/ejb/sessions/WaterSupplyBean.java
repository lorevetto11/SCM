package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.WaterSupplyConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.Context;
import com.gpi.scm.ejb.entities.Floor;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Layout;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.ejb.entities.PrerequisiteType;
import com.gpi.scm.ejb.entities.Shape;
import com.gpi.scm.ejb.entities.WaterSupply;
import com.gpi.scm.generic.dtos.BasePrerequisiteDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.WaterSupplyLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class WaterSupplyBean extends GenericBean implements WaterSupplyLocal {
	private static final Logger logger = Logger.getLogger(WaterSupplyBean.class);

	@Override
	public List<BasePrerequisiteDto> findWaterSupply(Long organizations) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			List<WaterSupply> entities = (List<WaterSupply>) entityManager
					.createNamedQuery(WaterSupply.NQ_WATERSUPPLYS_IN_ORGANIZATIONS)
					.setParameter("organizations", organizations).getResultList();
			return WaterSupplyConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

	@Override
	public BasePrerequisiteDto editWaterSupply(BasePrerequisiteDto toSave) throws BusinessException {

		WaterSupply supply = entityManager.find(WaterSupply.class, toSave.getId());
		if (supply == null || supply.getDeleted()) {
			throw new NoResultException("No WaterSupply Found!");
		}
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		if (!organizations.contains(toSave.getOrganization().getId())) {
			throw new NoResultException("No access!");
		}
		PrerequisiteType ptype = entityManager.find(PrerequisiteType.class, toSave.getPrerequisiteType().getId());
		if (ptype == null || ptype.getDeleted()) {
			throw new NoResultException("PrerequisiteType not found!");
		}

		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted() || !organizations.contains(organization.getId())) {
			throw new NoResultException("Organization not found!");
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

		supply.setPrerequisiteType(ptype);
		supply.setLayout(layout);
		supply.setOrganization(organization);
		supply.setFloor(floor);
		supply.setShape(shape);
		supply.setName(toSave.getName());
		supply.setDescription(toSave.getDescription());

		WaterSupply tmp = entityManager.merge(supply);
		return WaterSupplyConverter.entityToDto(tmp, true);
	}

	@Override
	public BasePrerequisiteDto newWaterSupply(BasePrerequisiteDto toSave) throws BusinessException {
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		if (!organizations.contains(toSave.getOrganization().getId())) {
			throw new NoResultException("No access!");
		}
		PrerequisiteType ptype = entityManager.find(PrerequisiteType.class, toSave.getPrerequisiteType().getId());
		if (ptype == null || ptype.getDeleted()) {
			throw new NoResultException("PrerequisiteType not found!");
		}

		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
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

		WaterSupply supply = WaterSupplyConverter.dtoToEntity(toSave);

		Context context = new Context();
		context.setClassName(this.getClass().getSimpleName());
		entityManager.persist(context);

		supply.setContext(context);
		supply.setLayout(layout);
		supply.setOrganization(organization);
		supply.setFloor(floor);
		supply.setShape(shape);
		supply.setPrerequisiteType(ptype);

		WaterSupply tmp = entityManager.merge(supply);
		return WaterSupplyConverter.entityToDto(tmp, true);
	}

	@Override
	public boolean deleteWaterSupply(long id) throws BusinessException {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());

			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			WaterSupply toDelete = (WaterSupply) entityManager.createNamedQuery(WaterSupply.NQ_WATERSUPPLY_BY_ID)
					.setParameter("idWaterSupply", id).getSingleResult();
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
	public BasePrerequisiteDto findSupplyById(long id, List<Long> organizations) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			WaterSupply supply = (WaterSupply) entityManager.createNamedQuery(WaterSupply.NQ_WATERSUPPLY_BY_ID)
					.setParameter("idWaterSupply", id).getSingleResult();
			return WaterSupplyConverter.entityToDto(supply, true);

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

			WaterSupply supply = (WaterSupply) entityManager.createNamedQuery(WaterSupply.NQ_WATERSUPPLY_BY_CONTEXT_ID)
					.setParameter("idContext", id).getSingleResult();
			return WaterSupplyConverter.entityToDto(supply, true);

		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return null;
	}

}
