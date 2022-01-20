package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.Grant;
import com.gpi.scm.generic.dtos.GrantDto;

public class GrantConverter extends GenericConverter {
	public static GrantDto entityToDto(Grant entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		GrantDto result = new GrantDto();
		result.setId(entity.getId());
		result.setName(entity.getName());
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
		}
		return result;
	}

	public static Grant dtoToEntity(GrantDto dto) {
		if (dto == null) {
			return null;
		}
		Grant result = new Grant();
		result.setId(dto.getId());
		result.setName(dto.getName());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<GrantDto> entityToDto(List<Grant> entities, boolean loadRelations) {
		List<GrantDto> grants = new ArrayList<>();
		for (Grant grant : entities) {
			grants.add(entityToDto(grant, loadRelations));
		}
		return grants;
	}
	public static List<Grant> dtoToEntity(List<GrantDto> entities) {
		List<Grant> grants = new ArrayList<>();
		for (GrantDto grant : entities) {
			grants.add(dtoToEntity(grant));
		}
		return grants;
	}

}
