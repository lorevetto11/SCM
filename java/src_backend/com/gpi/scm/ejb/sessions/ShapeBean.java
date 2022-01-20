package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.ShapeConverter;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Shape;
import com.gpi.scm.generic.dtos.ShapeDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.ShapeLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class ShapeBean extends GenericBean implements ShapeLocal {
	private static final Logger logger = Logger.getLogger(ShapeBean.class);

	@Override
	public List<ShapeDto> findShapes( ) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			List<Shape> entities = (List<Shape>) entityManager.createNamedQuery(Shape.NQ_SHAPES).getResultList();

			return ShapeConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

	@Override
	public ShapeDto findShapesById(long id) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
	
			Shape shape = (Shape) entityManager.createNamedQuery(Shape.NQ_SHAPE_BY_ID).setParameter("idShape", id)
					.getSingleResult();
			return ShapeConverter.entityToDto(shape, true);

		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return null;
	}

	@Override
	public ShapeDto newShape(ShapeDto toSave) throws BusinessException {

		Shape shape = ShapeConverter.dtoToEntity(toSave);
		Shape tmp = entityManager.merge(shape);

		return ShapeConverter.entityToDto(tmp, true);
	}

	@Override
	public ShapeDto editShape(ShapeDto toSave) throws BusinessException {
		Shape shape = entityManager.find(Shape.class, toSave.getId());
		if (shape == null || shape.getDeleted()) {
			throw new NoResultException("No Shape Found!");
		}
		shape.setOrder(toSave.getOrder());
		shape.setRadius(toSave.getRadius());
		shape.setSizeX(toSave.getSizeX());
		shape.setSizeY(toSave.getSizeY());
		shape.setStartX(toSave.getStartX());
		shape.setStartY(toSave.getStartY());
		shape.setType(toSave.getType());
		shape.setColor(toSave.getColor());

		Shape tmp = entityManager.merge(shape);
		// return toSave;
		return ShapeConverter.entityToDto(tmp, true);

	}

	@Override
	public boolean deleteShape(long id) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);


			Shape toDelete = (Shape) entityManager.createNamedQuery(Shape.NQ_SHAPE_BY_ID).setParameter("idShape", id)
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

}
