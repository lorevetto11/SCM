package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.PrerequisiteType;
import com.gpi.scm.generic.dtos.PrerequisiteTypeDto;



public class PrerequisiteTypeConverter extends GenericConverter {
	public static PrerequisiteTypeDto entityToDto(PrerequisiteType entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		PrerequisiteTypeDto result = new PrerequisiteTypeDto();
		result.setId(entity.getId());
		result.setName(entity.getName());
		result.setDescription(entity.getDescription());
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {

		}
		return result;
	}

	public static PrerequisiteType dtoToEntity(PrerequisiteTypeDto dto) {
		if (dto == null) {
			return null;
		}
		PrerequisiteType result = new PrerequisiteType();
		result.setId(dto.getId());
		result.setName(dto.getName());
		result.setDescription(dto.getDescription());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<PrerequisiteTypeDto> entityToDto(List<PrerequisiteType> entities, boolean loadRelations) {
		List<PrerequisiteTypeDto> prptypes = new ArrayList<>();
		for (PrerequisiteType prptype : entities) {
			prptypes.add(entityToDto(prptype, loadRelations));
		}
		return prptypes;
	}

}
