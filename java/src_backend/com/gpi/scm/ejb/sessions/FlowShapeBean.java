package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.FlowAnchorPointConverter;
import com.gpi.scm.converters.FlowElementConverter;
import com.gpi.scm.converters.FlowShapeConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.Context;
import com.gpi.scm.ejb.entities.Diagram;
import com.gpi.scm.ejb.entities.FlowAnchorPoint;
import com.gpi.scm.ejb.entities.FlowElement;
import com.gpi.scm.ejb.entities.FlowShape;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Material;
import com.gpi.scm.generic.dtos.FlowAnchorPointDto;
import com.gpi.scm.generic.dtos.FlowShapeDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.CommonEnums.flowElementsType;
import com.gpi.scm.generic.utils.CommonEnums.flowShapeType;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.FlowShapeLocal;


@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class FlowShapeBean extends GenericBean implements FlowShapeLocal {
	private static final Logger logger = Logger.getLogger(FlowShapeBean.class);

	@Override
	public List<FlowShapeDto> findFlowShape(Long diagramId) throws BusinessException {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			List<FlowShape> entities = (List<FlowShape>) entityManager
					.createNamedQuery(FlowShape.NQ_SHAPE_BY_DIAGRAM_ID).setParameter("idDiagram", diagramId)
					.setParameter("idOrganization", organizations).getResultList();
			return FlowShapeConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

	@Override
	public boolean deleteFlowShape(long id) throws BusinessException {

		/*
		 * List<Long> organizations = OrganizationDelegate
		 * .organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		 */

		FlowShape toDelete = entityManager.find(FlowShape.class, id);
		if (toDelete == null || toDelete.getDeleted()) {
			return false;
		}
		toDelete.setDeleted(true);
		return true;
	}

	@Override
	public FlowShapeDto editFlowShape(FlowShapeDto toSave) throws BusinessException {
		/*
		 * List<Long> organizations = OrganizationDelegate
		 * .organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		 * if (!organizations.contains(toSave.getOrganization().getId())) { throw new
		 * NoResultException("No access !"); }
		 */

		FlowShape flowShape = entityManager.find(FlowShape.class, toSave.getId());
		if (flowShape == null || flowShape.getDeleted()) {
			throw new NoResultException("FlowShape not found");
		}
		Diagram diagram = entityManager.find(Diagram.class, toSave.getDiagram().getId());
		if (diagram == null || diagram.getDeleted()) {
			throw new NoResultException("Diagram not found!");
		}
			
		FlowElement element = entityManager.find(FlowElement.class, toSave.getElement().getId());
		if (element == null) {
			throw new NoResultException("Element not found!");
		} else {
			element.setName(toSave.getElement().getName());
			element.setDescription(toSave.getElement().getDescription());
			element.setType(toSave.getElement().getType());
			element.setDescription(toSave.getElement().getDescription());
			
			if(toSave.getElement().getMaterial() != null) {
				Material material = entityManager.find(Material.class, toSave.getElement().getMaterial().getId());
				if (material == null) {
					throw new NoResultException("Material not found!");
				}
				element.setMaterial(material);
			} else {
				element.setMaterial(null);
			}
		}
		
		flowShape.setElement(element);
		flowShape.setDiagram(diagram);
		flowShape.setName(toSave.getName());
		flowShape.setOrder(toSave.getzOrder());
		flowShape.setCenterX(toSave.getCenterX());
		flowShape.setCenterY(toSave.getCenterY());
		flowShape.setDescription(toSave.getDescription());
		flowShape.setFillColor(toSave.getFillColor());
		flowShape.setHeight(toSave.getHeight());
		flowShape.setWidth(toSave.getWidth());
		flowShape.setType((flowShapeType) toSave.getType());

		return FlowShapeConverter.entityToDto(entityManager.merge(flowShape), true);

	}

	@Override
	public FlowShapeDto newFlowShape(FlowShapeDto toSave) throws BusinessException {

		FlowShape flowShape = FlowShapeConverter.dtoToEntity(toSave);
		Diagram diagram = entityManager.find(Diagram.class, toSave.getDiagram().getId());
		if (diagram == null || diagram.getDeleted()) {
			throw new NoResultException("Diagram not found!");
		}
		flowShape.setDiagram(diagram);
		
		entityManager.persist(flowShape);
		
		if(!toSave.getAnchorPoints().isEmpty()) {
			flowShape.setAnchorPoints(new ArrayList<FlowAnchorPoint>());
			for(FlowAnchorPointDto ap : toSave.getAnchorPoints()) {
				FlowAnchorPoint flowAnchorPoint = FlowAnchorPointConverter.dtoToEntity(ap);
				flowAnchorPoint.setShape(flowShape);
				entityManager.persist(flowAnchorPoint);
				flowShape.getAnchorPoints().add(flowAnchorPoint);
			}
		}
		
		if(toSave.getElement() != null) {
			FlowElement flowElement = FlowElementConverter.dtoToEntity(toSave.getElement());
			flowElement.setShape(flowShape);
			
			Context context = new Context();
			context.setClassName(flowElement.getClass().getSimpleName());
			entityManager.persist(context);
			flowElement.setContext(context);
			entityManager.persist(flowElement);
			
			flowShape.setElement(flowElement);
			
		}
		
		FlowShape tmp = entityManager.merge(flowShape);
		return FlowShapeConverter.entityToDto(tmp, true);
	}

}
