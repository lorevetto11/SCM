package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.LayoutConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.Floor;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Layout;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.ejb.entities.PrerequisiteType;
import com.gpi.scm.ejb.entities.RiskClass;
import com.gpi.scm.ejb.entities.Shape;
import com.gpi.scm.generic.dtos.LayoutDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.LayoutLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class LayoutBean extends GenericBean implements LayoutLocal {
	private static final Logger logger = Logger.getLogger(LayoutBean.class);

	@Override
	public List<LayoutDto> findLayouts(Long organizations) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			List<Layout> entities = (List<Layout>) entityManager.createNamedQuery(Layout.NQ_LAYOUTS_IN_ORGANIZATIONS)
					.setParameter("organizations", organizations).getResultList();
			return LayoutConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

	@Override
	public LayoutDto findLayoutsById(long id, List<Long> organizations) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			Layout layout = (Layout) entityManager.createNamedQuery(Layout.NQ_LAYOUT_BY_ID).setParameter("idLayout", id)
					.getSingleResult();
			return LayoutConverter.entityToDto(layout, true);

		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return null;
	}

	@Override
	public LayoutDto newLayout(LayoutDto toSave) throws BusinessException {
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		if(!organizations.contains(toSave.getOrganization().getId()))
		{
			throw new NoResultException("No access!");
		}
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}
		RiskClass riskc = entityManager.find(RiskClass.class, toSave.getRiskClass().getId());
		if (riskc == null || riskc.getDeleted() ) {
			throw new NoResultException("Riskclass not found!");
		}
		Floor floor = entityManager.find(Floor.class, toSave.getFloor().getId());
		if (floor == null || floor.getDeleted()) {
			throw new NoResultException("Floor not found!");
		}
		Shape shape = entityManager.find(Shape.class, toSave.getShape().getId());
		if (shape == null || shape.getDeleted()) {
			throw new NoResultException("Shape not found!");
		}
		PrerequisiteType ptype = entityManager.find(PrerequisiteType.class, toSave.getPrerequisiteType().getId());
		if (ptype == null || ptype.getDeleted()) {
			throw new NoResultException("PrerequisiteType not found!");
		}
		Layout layout = LayoutConverter.dtoToEntity(toSave);

		layout.setPrerequisiteType(ptype);
		layout.setRiskClass(riskc);
		layout.setOrganization(organization);
		layout.setFloor(floor);
		layout.setShape(shape);
		Layout tmp = entityManager.merge(layout);
		
		return LayoutConverter.entityToDto(tmp, true);
	}

	@Override
	public LayoutDto editLayout(LayoutDto toSave) throws BusinessException {
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		if(!organizations.contains(toSave.getOrganization().getId()))
		{
			throw new NoResultException("No access!");
		}
		Layout layout = entityManager.find(Layout.class, toSave.getId());
		if (layout == null || layout.getDeleted()) {
			throw new NoResultException("No Layout Found!");
		}
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()|| !organizations.contains(organization.getId()) ) {
			throw new NoResultException("Organization not found!");
		}
		RiskClass riskc = entityManager.find(RiskClass.class, toSave.getRiskClass().getId());
		if (riskc == null || riskc.getDeleted()) {
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
		PrerequisiteType ptype = entityManager.find(PrerequisiteType.class, toSave.getPrerequisiteType().getId());
		if (ptype == null || ptype.getDeleted()) {
			throw new NoResultException("PrerequisiteType not found!");
		}
		layout.setPrerequisiteType(ptype);
		layout.setRiskClass(riskc);
		layout.setOrganization(organization);
		layout.setFloor(floor);
		layout.setShape(shape);
		layout.setName(toSave.getName());
		layout.setDescription(toSave.getDescription());

		Layout tmp = entityManager.merge(layout);
		return LayoutConverter.entityToDto(tmp, true);

	}

	@Override
	public boolean deleteLayout(long id) throws BusinessException {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
			
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			Layout toDelete = (Layout) entityManager.createNamedQuery(Layout.NQ_LAYOUT_BY_ID)
					.setParameter("idLayout", id).getSingleResult();
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

}
