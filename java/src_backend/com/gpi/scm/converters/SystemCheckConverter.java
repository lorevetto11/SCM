
package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.SystemCheck;
import com.gpi.scm.generic.dtos.SystemCheckDto;

public class SystemCheckConverter extends GenericConverter {
	public static SystemCheckDto entityToDto(SystemCheck entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		SystemCheckDto result = new SystemCheckDto();
		result.setId(entity.getId());
		result.setName(entity.getName());
		result.setDescription(entity.getDescription());
		result.setPrivacy(entity.isPrivacy());
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(), false));

		}
		return result;
	}

	public static SystemCheck dtoToEntity(SystemCheckDto dto) {
		if (dto == null) {
			return null;
		}
		SystemCheck result = new SystemCheck();
		result.setId(dto.getId());
		result.setName(dto.getName());
		result.setDescription(dto.getDescription());
		result.setPrivacy(dto.isPrivacy());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}

	public static List<SystemCheckDto> entityToDto(List<SystemCheck> entities, boolean loadRelations) {
		List<SystemCheckDto> checks = new ArrayList<>();
		for (SystemCheck check : entities) {
			checks.add(entityToDto(check, loadRelations));
		}
		return checks;
	}

}
