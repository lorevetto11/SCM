package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.EquipmentConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.Context;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.ejb.entities.Supplier;
import com.gpi.scm.ejb.entities.Equipment;
import com.gpi.scm.ejb.entities.EquipmentType;
import com.gpi.scm.ejb.entities.Frequency;
import com.gpi.scm.generic.dtos.EquipmentDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.EquipmentLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)

public class EquipmentBean extends GenericBean implements EquipmentLocal {
	private static final Logger logger = Logger.getLogger(EquipmentBean.class);

	@Override
	public EquipmentDto save(EquipmentDto toSave) throws BusinessException {
		Equipment equipment = EquipmentConverter.dtoToEntity(toSave);
		EquipmentType type = entityManager.find(EquipmentType.class, toSave.getEquipmentType().getId());
		if (type == null || type.getDeleted()) {
			throw new NoResultException("EquipmentType not found !");
		}
		Frequency frequency = entityManager.find(Frequency.class, toSave.getFrequency().getId());
		if (frequency == null || frequency.getDeleted()) {
			throw new NoResultException("FrequencyNotFound");
		}
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}
		if (toSave.getSupplier() != null) {
			Supplier supplier = entityManager.find(Supplier.class, toSave.getSupplier().getId());
			if(supplier==null||supplier.getDeleted())
			{
				throw new NoResultException("Supplier not found!");
			}
			equipment.setSupplier(supplier);
			
		}
		equipment.setOrganization(organization);
		equipment.setEquipmentType(type);
		equipment.setFrequency(frequency);

		Context context = new Context();
		context.setClassName(this.getClass().getSimpleName());
		entityManager.persist(context);

		equipment.setContext(context);

		Equipment tmp = entityManager.merge(equipment);
		return EquipmentConverter.entityToDto(tmp, true);

	}

	@Override
	public EquipmentDto edit(EquipmentDto toSave) throws BusinessException {
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());

		if (!organizations.contains(toSave.getOrganization().getId())) {
			throw new NoResultException("No access");
		}
		Equipment equipment = entityManager.find(Equipment.class, toSave.getId());

		if (equipment == null || equipment.getDeleted()
				|| !organizations.contains(equipment.getOrganization().getId())) {
			throw new NoResultException(" Equipment not found");
		}
		EquipmentType type = entityManager.find(EquipmentType.class, toSave.getEquipmentType().getId());
		if (type == null || type.getDeleted() || !organizations.contains(type.getOrganization().getId())) {
			throw new NoResultException("EquipmentType not found !");
		}

		Context context = equipment.getContext();
		if (toSave.getContext() != null) {
			context = entityManager.find(Context.class, toSave.getContext().getId());
			if (context == null || context.getDeleted()) {
				throw new NoResultException("Context not found");
			}
		}

		Frequency frequency = entityManager.find(Frequency.class, toSave.getFrequency().getId());
		if (frequency == null || frequency.getDeleted()) {
			throw new NoResultException("Frequency not found");
		}
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}
		if (toSave.getSupplier() != null) {
			Supplier supplier = entityManager.find(Supplier.class, toSave.getSupplier().getId());
			if(supplier==null||supplier.getDeleted())
			{
				throw new NoResultException("Supplier not found!");
			}
			equipment.setSupplier(supplier);
			
		}

		equipment.setName(toSave.getName());
		equipment.setDescription(toSave.getDescription());
		equipment.setMaintainer(toSave.getMaintainer());
		// equipment.setSupplier(toSave.getSupplier());
		equipment.setStartupDate(toSave.getStartupDate());
		equipment.setContext(context);
		equipment.setFrequency(frequency);
		equipment.setEquipmentType(type);
		equipment.setOrganization(organization);

		Equipment tmp = entityManager.merge(equipment);

		return EquipmentConverter.entityToDto(tmp, true);
	}

	@Override
	public boolean delete(long id) throws BusinessException {
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());

		Equipment toDelete = entityManager.find(Equipment.class, id);
		if (!organizations.contains(toDelete.getOrganization().getId()) || toDelete.getDeleted()) {
			return false;
		}
		toDelete.setDeleted(true);
		return true;

	}

	@Override
	public List<EquipmentDto> finds(Long organizationId) throws BusinessException {

		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);

			List<Equipment> entities = (List<Equipment>) entityManager.createNamedQuery(Equipment.NQ_EQUIPMENT)
					.setParameter("organizations", organizationId).getResultList();
			return EquipmentConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

}
