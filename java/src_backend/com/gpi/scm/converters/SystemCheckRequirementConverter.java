
package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.SystemCheckRequirement;
import com.gpi.scm.generic.dtos.SystemCheckRequirementDto;

public class SystemCheckRequirementConverter extends GenericConverter {
	public static SystemCheckRequirementDto entityToDto(SystemCheckRequirement entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		SystemCheckRequirementDto result = new SystemCheckRequirementDto();
		result.setId(entity.getId());
		result.setName(entity.getName());
		result.setDescription(entity.getDescription());
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setSystemCheck(SystemCheckConverter.entityToDto(entity.getSystemCheck(), false));
		}
		return result;
	}

	public static SystemCheckRequirement dtoToEntity(SystemCheckRequirementDto dto) {
		if (dto == null) {
			return null;
		}
		SystemCheckRequirement result = new SystemCheckRequirement();
		result.setId(dto.getId());
		result.setName(dto.getName());
		result.setDescription(dto.getDescription());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}

	public static List<SystemCheckRequirementDto> entityToDto(List<SystemCheckRequirement> entities, boolean loadRelations) {
		List<SystemCheckRequirementDto> checks = new ArrayList<>();
		for (SystemCheckRequirement check : entities) {
			checks.add(entityToDto(check, loadRelations));
		}
		return checks;
	}

}
