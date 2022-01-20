package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.EquipmentTypeConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.ejb.entities.Shape;
import com.gpi.scm.ejb.entities.EquipmentType;
import com.gpi.scm.generic.dtos.EquipmentTypeDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.EquipmentTypeLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)

public class EquipmentTypeBean extends GenericBean implements EquipmentTypeLocal {
	private static final Logger logger = Logger.getLogger(EquipmentTypeBean.class);

	@Override
	public EquipmentTypeDto saveType(EquipmentTypeDto toSave) throws BusinessException {
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}
		Shape shape = entityManager.find(Shape.class, toSave.getShape().getId());
		if (shape == null || shape.getDeleted()) {
			throw new NoResultException("Shape not found!");
		}
		EquipmentType type = EquipmentTypeConverter.dtoToEntity(toSave);
		type.setOrganization(organization);
		type.setShape(shape);

		EquipmentType tmp = entityManager.merge(type);

		return EquipmentTypeConverter.entityToDto(tmp, true);
	}

	@Override
	public EquipmentTypeDto editType(EquipmentTypeDto toSave) throws BusinessException {
		
		EquipmentType type = entityManager.find(EquipmentType.class, toSave.getId());
		if (type == null || type.getDeleted()) {
			throw new NoResultException("Type not found");
		}
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		if (!organizations.contains(toSave.getOrganization().getId()) || !organizations.contains(type.getOrganization().getId())) {
			throw new NoResultException("No access!");

		}
		
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}
		Shape shape = entityManager.find(Shape.class, toSave.getShape().getId());
		if (shape == null || shape.getDeleted()) {
			throw new NoResultException("Shape not found!");
		}
		type.setName(toSave.getName());
		type.setDescription(toSave.getDescription());
		type.setOrganization(organization);
		type.setShape(shape);
		EquipmentType tmp = entityManager.merge(type);

		return EquipmentTypeConverter.entityToDto(tmp, true);
	}

	@Override
	public boolean deleteType(long id) throws BusinessException {
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());

		EquipmentType toDelete = entityManager.find(EquipmentType.class, id);
		if (toDelete == null || toDelete.getDeleted() || !organizations.contains(toDelete.getOrganization().getId())) {
			return false;
		}
		toDelete.setDeleted(true);
		return true;
	}

	@Override
	public List<EquipmentTypeDto> findTypes(List<Long> organizations) throws BusinessException {

		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);

			List<EquipmentType> entities = (List<EquipmentType>) entityManager
					.createNamedQuery(EquipmentType.NQ_EQUIPMENT_TYPES).setParameter("organizations", organizations)
					.getResultList();
			return EquipmentTypeConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

}
