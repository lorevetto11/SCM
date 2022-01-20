package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.EquipmentPrerequisiteConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.Floor;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Layout;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.ejb.entities.PrerequisiteType;
import com.gpi.scm.ejb.entities.Shape;
import com.gpi.scm.ejb.entities.Context;
import com.gpi.scm.ejb.entities.Equipment;
import com.gpi.scm.ejb.entities.EquipmentPrerequisite;
import com.gpi.scm.ejb.entities.EquipmentType;
import com.gpi.scm.generic.dtos.BasePrerequisiteDto;
import com.gpi.scm.generic.dtos.EquipmentPrerequisiteDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.EquipmentPrerequisiteLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class EquipmentPrerequisiteBean extends GenericBean implements EquipmentPrerequisiteLocal {
	private static final Logger logger = Logger.getLogger(EquipmentPrerequisiteBean.class);

	@Override
	public List<EquipmentPrerequisiteDto> findEquipmentPrerequisite(Long organizations) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			List<EquipmentPrerequisite> entities = (List<EquipmentPrerequisite>) entityManager
					.createNamedQuery(EquipmentPrerequisite.NQ_EQUIPMENTPREREQUISITES_IN_ORGANIZATIONS)
					.setParameter("organizations", organizations).getResultList();
			return EquipmentPrerequisiteConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

	@Override
	public EquipmentPrerequisiteDto editEquipmentPrerequisite(EquipmentPrerequisiteDto toSave)
			throws BusinessException {
		EquipmentPrerequisite equipment = entityManager.find(EquipmentPrerequisite.class, toSave.getId());
		if (equipment == null) {
			throw new NoResultException("No EquipmentPrerequisite Found!");
		}
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		if (!organizations.contains(toSave.getOrganization().getId()) || !organizations.contains(equipment.getOrganization().getId())) {
			throw new NoResultException("No access!");

		}
		PrerequisiteType ptype = entityManager.find(PrerequisiteType.class, toSave.getPrerequisiteType().getId());
		if (ptype == null || ptype.getDeleted()) {
			throw new NoResultException("PrerequisiteType not found!");
		}

		Equipment equip = entityManager.find(Equipment.class, toSave.getEquipment().getId());
		if (equip == null || equip.getDeleted()) {
			throw new NoResultException("Equipment not found!");
		}

		EquipmentType equipType = entityManager.find(EquipmentType.class, toSave.getEquipmentType().getId());
		if (equipType == null || equipType.getDeleted()) {
			throw new NoResultException("EquipmentType not found!");
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

		equipment.setPrerequisiteType(ptype);
		equipment.setLayout(layout);
		equipment.setOrganization(organization);
		equipment.setFloor(floor);
		equipment.setShape(shape);
		equipment.setEquipment(equip);
		equipment.setEquipmentType(equipType);
		equipment.setName(toSave.getName());
		equipment.setDescription(toSave.getDescription());

		EquipmentPrerequisite tmp = entityManager.merge(equipment);
		return EquipmentPrerequisiteConverter.entityToDto(tmp, true);
	}

	@Override
	public EquipmentPrerequisiteDto newEquipmentPrerequisite(EquipmentPrerequisiteDto toSave) throws BusinessException {

		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		if (!organizations.contains(toSave.getOrganization().getId())) {
			throw new NoResultException("No access!");

		}
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());

		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}
		Layout layout = entityManager.find(Layout.class, toSave.getLayout().getId());

		if (layout == null || layout.getDeleted()) {
			throw new NoResultException("Layout not found!");
		}
		Floor floor = entityManager.find(Floor.class, toSave.getFloor().getId());

		if (floor == null || floor.getDeleted()) {
			throw new NoResultException("Floor not found!");
		}
		Shape shape = entityManager.find(Shape.class, toSave.getShape().getId());

		if (shape == null || shape.getDeleted()) {
			throw new NoResultException("Shape not found!");
		}
		Equipment equip = entityManager.find(Equipment.class, toSave.getEquipment().getId());

		if (equip == null || equip.getDeleted()) {
			throw new NoResultException("Equipment not found!");
		}
		EquipmentType equipType = entityManager.find(EquipmentType.class, toSave.getEquipmentType().getId());

		if (equipType == null || equipType.getDeleted()) {
			throw new NoResultException("EquipmentType not found!");
		}
		PrerequisiteType ptype = entityManager.find(PrerequisiteType.class, toSave.getPrerequisiteType().getId());
		if (ptype == null || ptype.getDeleted()) {
			throw new NoResultException("PrerequisiteType not found!");
		}
		EquipmentPrerequisite equipment = EquipmentPrerequisiteConverter.dtoToEntity(toSave);

		Context context = new Context();
		context.setClassName(this.getClass().getSimpleName());
		entityManager.persist(context);

		equipment.setContext(context);
		equipment.setLayout(layout);
		equipment.setOrganization(organization);
		equipment.setFloor(floor);
		equipment.setShape(shape);
		equipment.setEquipment(equip);
		equipment.setEquipmentType(equipType);
		equipment.setPrerequisiteType(ptype);

		EquipmentPrerequisite tmp = entityManager.merge(equipment);
		return EquipmentPrerequisiteConverter.entityToDto(tmp, true);
	}

	@Override
	public boolean deleteEquipmentPrerequisite(long id) throws BusinessException {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			EquipmentPrerequisite toDelete = (EquipmentPrerequisite) entityManager
					.createNamedQuery(EquipmentPrerequisite.NQ_EQUIPMENTPREREQUISITE_BY_ID)
					.setParameter("idEquipmentPrerequisite", id).getSingleResult();
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
	public EquipmentPrerequisiteDto findEquipmentPrerequisiteById(long id) throws BusinessException {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			EquipmentPrerequisite equipment = (EquipmentPrerequisite) entityManager
					.createNamedQuery(EquipmentPrerequisite.NQ_EQUIPMENTPREREQUISITE_BY_ID)
					.setParameter("idEquipmentPrerequisite", id).getSingleResult();
			return EquipmentPrerequisiteConverter.entityToDto(equipment, true);

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

			EquipmentPrerequisite equipment = (EquipmentPrerequisite) entityManager.createNamedQuery(EquipmentPrerequisite.NQ_EQUIPMENTPREREQUISITES_BY_CONTEXT_ID)
					.setParameter("idContext", id).getSingleResult();
			return EquipmentPrerequisiteConverter.entityToDto(equipment, true);

		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return null;
	}
	
}
