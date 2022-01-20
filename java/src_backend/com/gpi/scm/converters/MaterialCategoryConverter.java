package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.MaterialCategory;
import com.gpi.scm.generic.dtos.MaterialCategoryDto;
import com.gpi.scm.generic.utils.CommonEnums.materialType;

public class MaterialCategoryConverter extends GenericConverter {
	public static MaterialCategoryDto entityToDto(MaterialCategory entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		MaterialCategoryDto result = new MaterialCategoryDto();
		result.setId(entity.getId());
		result.setName(entity.getName());
		result.setDescription(entity.getDescription());
		result.setType((materialType)entity.getType());
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
		}
		return result;
	}

	public static MaterialCategory dtoToEntity(MaterialCategoryDto dto) {
		if (dto == null) {
			return null;
		}
		MaterialCategory result = new MaterialCategory();
		result.setId(dto.getId());
		result.setName(dto.getName());
		result.setDescription(dto.getDescription());
		result.setType((materialType)dto.getType());

		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<MaterialCategoryDto> entityToDto(List<MaterialCategory> entities, boolean loadRelations) {
		List<MaterialCategoryDto> materials = new ArrayList<>();
		for (MaterialCategory material : entities) {
			materials.add(entityToDto(material, loadRelations));
		}
		return materials;
	}
	public static List<MaterialCategory> dtoToEntity(List<MaterialCategoryDto> entities) {
		List<MaterialCategory> materials = new ArrayList<>();
		for (MaterialCategoryDto material : entities) {
			materials.add(dtoToEntity(material));
		}
		return materials;
	}

}