package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.Material;
import com.gpi.scm.generic.dtos.MaterialDto;
import com.gpi.scm.generic.utils.CommonEnums.materialType;

public class MaterialConverter extends GenericConverter {
	public static MaterialDto entityToDto(Material entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		MaterialDto result = new MaterialDto();
		result.setId(entity.getId());
		result.setName(entity.getName());
		result.setDescription(entity.getDescription());
		result.setType((materialType)entity.getType());
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setContext(ContextConverter.entityToDto(entity.getContext(), false));
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(), false));
			result.setPrerequisiteType(PrerequisiteTypeConverter.entityToDto(entity.getPrerequisiteType(), false));
			result.setSupplier(SupplierConverter.entityToDto(entity.getSupplier(), false));
			result.setMaterialCategory(MaterialCategoryConverter.entityToDto(entity.getMaterialCategory(), false));

		}
		return result;
	}

	public static Material dtoToEntity(MaterialDto dto) {
		if (dto == null) {
			return null;
		}
		Material result = new Material();
		result.setId(dto.getId());
		result.setName(dto.getName());
		result.setDescription(dto.getDescription());
		result.setType((materialType)dto.getType());

		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<MaterialDto> entityToDto(List<Material> entities, boolean loadRelations) {
		List<MaterialDto> materials = new ArrayList<>();
		for (Material material : entities) {
			materials.add(entityToDto(material, loadRelations));
		}
		return materials;
	}
	public static List<Material> dtoToEntity(List<MaterialDto> entities) {
		List<Material> materials = new ArrayList<>();
		for (MaterialDto material : entities) {
			materials.add(dtoToEntity(material));
		}
		return materials;
	}

}