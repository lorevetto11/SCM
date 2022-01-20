package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.FlowRelationConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.FlowRelation;
import com.gpi.scm.ejb.entities.FlowAnchorPoint;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.generic.dtos.FlowRelationDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.generic.utils.CommonEnums.flowElementsType;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.FlowRelationLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class FlowRelationBean extends GenericBean implements FlowRelationLocal {
	private static final Logger logger = Logger.getLogger(FlowRelationBean.class);

	@Override
	public List<FlowRelationDto> findFlowRelation(Long diagramId) throws BusinessException {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			List<FlowRelation> entities = (List<FlowRelation>) entityManager
					.createNamedQuery(FlowRelation.NQ_RELATION_BY_DIAGRAM_ID).setParameter("idDiagram", diagramId)
					.setParameter("idOrganization", organizations).getResultList();
			return FlowRelationConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

	@Override
	public boolean deleteFlowRelation(long id) throws BusinessException {

		/*
		 * List<Long> organizations = OrganizationDelegate
		 * .organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		 */

		FlowRelation toDelete = entityManager.find(FlowRelation.class, id);
		if (toDelete == null || toDelete.getDeleted()) {
			return false;
		}
		toDelete.setDeleted(true);
		return true;
	}

	@Override
	public FlowRelationDto editFlowRelation(FlowRelationDto toSave) throws BusinessException {
		/*
		 * List<Long> organizations = OrganizationDelegate
		 * .organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		 * if (!organizations.contains(toSave.getOrganization().getId())) { throw new
		 * NoResultException("No access !"); }
		 */

		FlowRelation flowRelation = entityManager.find(FlowRelation.class, toSave.getId());
		if (flowRelation == null || flowRelation.getDeleted()) {
			throw new NoResultException("FlowRelation not found");
		}
		FlowAnchorPoint startPoint = entityManager.find(FlowAnchorPoint.class, toSave.getStartAnchorPoint().getId());
		if (startPoint == null || startPoint.getDeleted()) {
			throw new NoResultException("FlowAnchorPoint not found!");
		}
		FlowAnchorPoint endPoint = entityManager.find(FlowAnchorPoint.class, toSave.getEndAnchorPoint().getId());
		if (endPoint == null || endPoint.getDeleted()) {
			throw new NoResultException("FlowAnchorPoint not found!");
		}

		flowRelation.setStartPoint(startPoint);
		flowRelation.setEndPoint(endPoint);
		flowRelation.setName(toSave.getName());
		flowRelation.setDescription(toSave.getDescription());
		flowRelation.setOrder(toSave.getOrder());
		flowRelation.setType(toSave.getType());

		return FlowRelationConverter.entityToDto(entityManager.merge(flowRelation), true);

	}

	@Override
	public FlowRelationDto newFlowRelation(FlowRelationDto toSave) throws BusinessException {

		FlowRelation flowRelation = FlowRelationConverter.dtoToEntity(toSave);
		FlowAnchorPoint startPoint = entityManager.find(FlowAnchorPoint.class, toSave.getStartAnchorPoint().getId());
		if (startPoint == null || startPoint.getDeleted()) {
			throw new NoResultException("FlowAnchorPoint not found!");
		}
		FlowAnchorPoint endPoint = entityManager.find(FlowAnchorPoint.class, toSave.getEndAnchorPoint().getId());
		if (endPoint == null || endPoint.getDeleted()) {
			throw new NoResultException("FlowAnchorPoint not found!");
		}

		flowRelation.setStartPoint(startPoint);
		flowRelation.setEndPoint(endPoint);
		FlowRelation tmp = entityManager.merge(flowRelation);
		return FlowRelationConverter.entityToDto(tmp, true);

	}

}
