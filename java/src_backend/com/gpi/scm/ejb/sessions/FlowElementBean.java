package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.FlowElementConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.Context;
import com.gpi.scm.ejb.entities.FlowElement;
import com.gpi.scm.ejb.entities.FlowShape;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Material;
import com.gpi.scm.generic.dtos.FlowElementDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.generic.utils.CommonEnums.ccpType;
import com.gpi.scm.generic.utils.CommonEnums.flowElementsType;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.FlowElementLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class FlowElementBean extends GenericBean implements FlowElementLocal {
	private static final Logger logger = Logger.getLogger(FlowElementBean.class);

	@Override
	public List<FlowElementDto> findFlowElement(Long diagramId, Long shapeId) throws BusinessException {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			
			Query q = null;
			if(diagramId != null) {
				q = entityManager.createNamedQuery(FlowElement.NQ_ELEMENT_BY_DIAGRAM_ID).setParameter("idDiagram", diagramId)
				.setParameter("idOrganization", organizations);
			} else { //if(shapeId != null) {
				q = entityManager.createNamedQuery(FlowElement.NQ_ELEMENT_BY_SHAPE_ID).setParameter("idShape", shapeId)
				.setParameter("idOrganization", organizations);
			}
			List<FlowElement> entities = q.getResultList();
			return FlowElementConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}
	
	@Override
	public boolean deleteFlowElement(long id) throws BusinessException {

		/*
		 * List<Long> organizations = OrganizationDelegate
		 * .organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		 */

		FlowElement toDelete = entityManager.find(FlowElement.class, id);
		if (toDelete == null || toDelete.getDeleted()) {
			return false;
		}
		toDelete.setDeleted(true);
		return true;
	}

	@Override
	public FlowElementDto editFlowElement(FlowElementDto toSave) throws BusinessException {
		/*
		 * List<Long> organizations = OrganizationDelegate
		 * .organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		 * if (!organizations.contains(toSave.getOrganization().getId())) { throw new
		 * NoResultException("No access !"); }
		 */

		FlowElement flowElement = entityManager.find(FlowElement.class, toSave.getId());
		if (flowElement == null || flowElement.getDeleted()) {
			throw new NoResultException("FlowElement not found");
		}
		FlowShape flowShape = entityManager.find(FlowShape.class, toSave.getShape().getId());
		if (flowShape == null || flowShape.getDeleted()) {
			throw new NoResultException("FlowShape not found!");
		}
		if (toSave.getMaterial() != null) {
			Material material = entityManager.find(Material.class, toSave.getMaterial().getId());
			if (material == null || material.getDeleted()) {
				throw new NoResultException("Material not found!");
			}
			flowElement.setMaterial(material);

		}
		if (toSave.getContext() != null) {
			Context context = entityManager.find(Context.class, toSave.getContext().getId());
			if (context == null || context.getDeleted()) {
				throw new NoResultException("Context not found!");
			}
			flowElement.setContext(context);

		}

		flowElement.setShape(flowShape);
		flowElement.setName(toSave.getName());
		flowElement.setDescription(toSave.getDescription());
		flowElement.setRisk(toSave.getRisk());
		flowElement.setType((flowElementsType) toSave.getType());
		flowElement.setTypeCCP((ccpType)toSave.getTypeCCP());


		return FlowElementConverter.entityToDto(entityManager.merge(flowElement), true);

	}

	@Override
	public FlowElementDto newFlowElement(FlowElementDto toSave) throws BusinessException {

		FlowElement flowElement = FlowElementConverter.dtoToEntity(toSave);
		FlowShape flowShape = entityManager.find(FlowShape.class, toSave.getShape().getId());
		if (flowShape == null || flowShape.getDeleted()) {
			throw new NoResultException("FlowShape not found!");
		}
		if (toSave.getMaterial() != null) {
			Material material = entityManager.find(Material.class, toSave.getMaterial().getId());
			if (material == null || material.getDeleted()) {
				throw new NoResultException("Material not found!");
			}
			flowElement.setMaterial(material);
		}
		Context context = new Context();
		context.setClassName(this.getClass().getSimpleName());
		entityManager.persist(context);
		
		flowElement.setContext(context);
		flowElement.setShape(flowShape);
		FlowElement tmp = entityManager.merge(flowElement);
		return FlowElementConverter.entityToDto(tmp, true);

	}
	
	@Override
	public FlowElementDto findByContextId(long id) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);

			FlowElement flowElement = (FlowElement) entityManager.createNamedQuery(FlowElement.NQ_ELEMENT_BY_CONTEXT_ID)
					.setParameter("idContext", id).getSingleResult();
			return FlowElementConverter.entityToDto(flowElement, true);

		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return null;
	}

}
