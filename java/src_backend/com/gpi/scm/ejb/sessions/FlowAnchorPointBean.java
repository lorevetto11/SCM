package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.FlowAnchorPointConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.FlowAnchorPoint;
import com.gpi.scm.ejb.entities.FlowShape;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.generic.dtos.FlowAnchorPointDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.generic.utils.CommonEnums.flowElementsType;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.FlowAnchorPointLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class FlowAnchorPointBean extends GenericBean implements FlowAnchorPointLocal {
	private static final Logger logger = Logger.getLogger(FlowAnchorPointBean.class);

	@Override
	public List<FlowAnchorPointDto> findFlowAnchorPoint(Long diagramId) throws BusinessException {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			List<FlowAnchorPoint> entities = (List<FlowAnchorPoint>) entityManager
					.createNamedQuery(FlowAnchorPoint.NQ_ANCOR_POINTS_BY_DIAGRAM_ID).setParameter("idDiagram", diagramId)
					.setParameter("organizations", organizations)
					.getResultList();
			return FlowAnchorPointConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

	@Override
	public boolean deleteFlowAnchorPoint(long id) throws BusinessException {

		/*
		 * List<Long> organizations = OrganizationDelegate
		 * .organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		 */

		FlowAnchorPoint toDelete = entityManager.find(FlowAnchorPoint.class, id);
		if (toDelete == null || toDelete.getDeleted()) {
			return false;
		}
		toDelete.setDeleted(true);
		return true;
	}

	@Override
	public FlowAnchorPointDto editFlowAnchorPoint(FlowAnchorPointDto toSave) throws BusinessException {
		/*
		 * List<Long> organizations = OrganizationDelegate
		 * .organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		 * if (!organizations.contains(toSave.getOrganization().getId())) { throw new
		 * NoResultException("No access !"); }
		 */

		FlowAnchorPoint flowAncorPoint = entityManager.find(FlowAnchorPoint.class, toSave.getId());
		if (flowAncorPoint == null || flowAncorPoint.getDeleted()) {
			throw new NoResultException("FlowAncorPoint not found");
		}
		FlowShape shape = entityManager.find(FlowShape.class, toSave.getShape().getId());
		if (shape == null || shape.getDeleted()) {
			throw new NoResultException("FlowShape not found!");
		}

		flowAncorPoint.setShape(shape);
		flowAncorPoint.setName(toSave.getName());
		flowAncorPoint.setOrder(toSave.getOrder());
		flowAncorPoint.setDescription(toSave.getDescription());
		flowAncorPoint.setHeight(toSave.getHeight());
		flowAncorPoint.setWidth(toSave.getWidth());
		flowAncorPoint.setTranslationX(toSave.getTranslationX());
		flowAncorPoint.setTranslationY(toSave.getTranslationY());

		return FlowAnchorPointConverter.entityToDto(entityManager.merge(flowAncorPoint), true);

	}

	@Override
	public FlowAnchorPointDto newFlowAnchorPoint(FlowAnchorPointDto toSave) throws BusinessException {

		FlowAnchorPoint flowAncorPoint = FlowAnchorPointConverter.dtoToEntity(toSave);
		FlowShape shape = entityManager.find(FlowShape.class, toSave.getShape().getId());
		if (shape == null || shape.getDeleted()) {
			throw new NoResultException("FlowShape not found!");
		}
		/*
		 * Organization organization = entityManager.find(Organization.class,
		 * toSave.getOrganization().getId()); if (organization == null ||
		 * organization.getDeleted()) { throw new
		 * NoResultException("Organization not found!"); }
		 */

		flowAncorPoint.setShape(shape);
		FlowAnchorPoint tmp = entityManager.merge(flowAncorPoint);
		return FlowAnchorPointConverter.entityToDto(tmp, true);

	}

}
