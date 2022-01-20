package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.MaterialConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.Material;
import com.gpi.scm.ejb.entities.MaterialCategory;
import com.gpi.scm.ejb.entities.Context;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.ejb.entities.PrerequisiteType;
import com.gpi.scm.ejb.entities.Supplier;
import com.gpi.scm.generic.dtos.MaterialDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.CommonEnums.materialType;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.MaterialLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class MaterialBean extends GenericBean implements MaterialLocal {
	private static final Logger logger = Logger.getLogger(MaterialBean.class);

	@Override
	public List<MaterialDto> findMaterial(Long organization) throws BusinessException {
		try {

			List<Long> organizations = OrganizationDelegate.organizationsIdsTree(organization);

			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			List<Material> entities = (List<Material>) entityManager
					.createNamedQuery(Material.NQ_MATERIAL_IN_ORGANIZATIONS)
					.setParameter("organizations", organizations).getResultList();
			return MaterialConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

	@Override
	public boolean deleteMaterial(long id) throws BusinessException {

		/*
		 * List<Long> organizations = OrganizationDelegate
		 * .organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		 */

		Material toDelete = entityManager.find(Material.class, id);
		if (toDelete == null || toDelete.getDeleted()) {
			return false;
		}
		toDelete.setDeleted(true);
		return true;
	}

	@Override
	public MaterialDto editMaterial(MaterialDto toSave) throws BusinessException {
		/*
		 * List<Long> organizations = OrganizationDelegate
		 * .organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		 * if (!organizations.contains(toSave.getOrganization().getId())) { throw new
		 * NoResultException("No access !"); }
		 */

		Material material = entityManager.find(Material.class, toSave.getId());
		if (material == null || material.getDeleted()) {
			throw new NoResultException("Material not found");
		}
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}
		Supplier supplier = entityManager.find(Supplier.class, toSave.getSupplier().getId());
		if (supplier == null || supplier.getDeleted()) {
			throw new NoResultException("Supplier not found!");
		}
		PrerequisiteType prerequisiteType = entityManager.find(PrerequisiteType.class,
				toSave.getPrerequisiteType().getId());
		if (prerequisiteType == null || prerequisiteType.getDeleted()) {
			throw new NoResultException("PrerequisiteType not found!");
		}
		MaterialCategory materialCategory= entityManager.find(MaterialCategory.class, toSave.getMaterialCategory().getId());
		if(materialCategory==null||materialCategory.getDeleted())
		{
			throw new NoResultException("MaterialCategory not found");
		}
		if (toSave.getContext() != null) {
			Context context = entityManager.find(Context.class, toSave.getContext().getId());
			if (context == null || context.getDeleted()) {
				throw new NoResultException("Context not found!");
			}
			material.setContext(context);

		}
		material.setName(toSave.getName());
		material.setDescription(toSave.getDescription());
		material.setOrganization(organization);
		material.setPrerequisiteType(prerequisiteType);
		material.setSupplier(supplier);
		material.setMaterialCategory(materialCategory);
		material.setType((materialType) toSave.getType());

		return MaterialConverter.entityToDto(entityManager.merge(material), true);

	}

	@Override
	public MaterialDto newMaterial(MaterialDto toSave) throws BusinessException {

		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		Material material = MaterialConverter.dtoToEntity(toSave);

		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}
		Supplier supplier = entityManager.find(Supplier.class, toSave.getSupplier().getId());
		if (supplier == null || supplier.getDeleted()) {
			throw new NoResultException("Supplier not found!");
		}
		PrerequisiteType prerequisiteType = entityManager.find(PrerequisiteType.class,
				toSave.getPrerequisiteType().getId());
		if (prerequisiteType == null || prerequisiteType.getDeleted()) {
			throw new NoResultException("PrerequisiteType not found!");
		}
		MaterialCategory materialCategory= entityManager.find(MaterialCategory.class, toSave.getMaterialCategory().getId());
		if(materialCategory==null||materialCategory.getDeleted())
		{
			throw new NoResultException("MaterialCategory not found");
		}
		Context context = new Context();
		context.setClassName(this.getClass().getSimpleName());
		entityManager.persist(context);
		material.setContext(context);
		material.setOrganization(organization);
		material.setMaterialCategory(materialCategory);
		material.setPrerequisiteType(prerequisiteType);
		material.setSupplier(supplier);

		Material tmp = entityManager.merge(material);
		return MaterialConverter.entityToDto(tmp, true);

	}

}
