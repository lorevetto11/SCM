package com.gpi.scm.converters;

import com.gpi.scm.ejb.entities.Context;
import com.gpi.scm.generic.dtos.ContextDto;

public class ContextConverter extends GenericConverter {
	public static ContextDto entityToDto(Context entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		ContextDto result = new ContextDto();
		result.setId(entity.getId());
		result.setClassName((entity.getClassName()));
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
		}
		return result;
	}

	public static Context dtoToEntity(ContextDto dto) {
		if (dto == null) {
			return null;
		}
		Context result = new Context();
		result.setId(dto.getId());
		result.setClassName(dto.getClassName());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
}
