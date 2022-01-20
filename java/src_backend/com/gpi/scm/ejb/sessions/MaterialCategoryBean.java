package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.MaterialCategoryConverter;
import com.gpi.scm.ejb.entities.MaterialCategory;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.generic.dtos.MaterialCategoryDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.CommonEnums.materialType;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.MaterialCategoryLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class MaterialCategoryBean extends GenericBean implements MaterialCategoryLocal {
	private static final Logger logger = Logger.getLogger(MaterialCategoryBean.class);

	@Override
	public List<MaterialCategoryDto> findMaterialCategory() throws BusinessException {
		try {

			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			List<MaterialCategory> entities = (List<MaterialCategory>) entityManager
					.createNamedQuery(MaterialCategory.NQ_MATERIALCATEGORIES)
					.getResultList();
			return MaterialCategoryConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

	@Override
	public boolean deleteMaterialCategory(long id) throws BusinessException {

		/*
		 * List<Long> organizations = OrganizationDelegate
		 * .organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		 */

		MaterialCategory toDelete = entityManager.find(MaterialCategory.class, id);
		if (toDelete == null || toDelete.getDeleted()) {
			return false;
		}
		toDelete.setDeleted(true);
		return true;
	}

	@Override
	public MaterialCategoryDto editMaterialCategory(MaterialCategoryDto toSave) throws BusinessException {
		/*
		 * List<Long> organizations = OrganizationDelegate
		 * .organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		 * if (!organizations.contains(toSave.getOrganization().getId())) { throw new
		 * NoResultException("No access !"); }
		 */

		MaterialCategory material = entityManager.find(MaterialCategory.class, toSave.getId());
		if (material == null || material.getDeleted()) {
			throw new NoResultException("MaterialCategory not found");
		}
		material.setName(toSave.getName());
		material.setDescription(toSave.getDescription());
		material.setType((materialType)toSave.getType());

		return MaterialCategoryConverter.entityToDto(entityManager.merge(material), true);

	}

	@Override
	public MaterialCategoryDto newMaterialCategory(MaterialCategoryDto toSave) throws BusinessException {

		MaterialCategory material = MaterialCategoryConverter.dtoToEntity(toSave);
		MaterialCategory tmp = entityManager.merge(material);
		return MaterialCategoryConverter.entityToDto(tmp, true);

	}

}
