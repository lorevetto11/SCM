
package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.ProcessCheck;
import com.gpi.scm.generic.dtos.ProcessCheckDto;

public class ProcessCheckConverter extends GenericConverter {
	public static ProcessCheckDto entityToDto(ProcessCheck entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		ProcessCheckDto result = new ProcessCheckDto();
		result.setId(entity.getId());
		result.setName(entity.getName());
		result.setDescription(entity.getDescription());
		result.setPrivacy(entity.isPrivacy());
		result.setType(entity.getType());
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(), false));
			result.setPrerequisiteType(PrerequisiteTypeConverter.entityToDto(entity.getPrerequisiteType(), false));
			result.setFrequency(FrequencyConverter.entityToDto(entity.getFrequency(), false));
			result.setProcessCheckOutcomes(ProcessCheckOutcomeConverter.entityToDto(entity.getProcessCheckOutcomes(), false));

		}
		return result;
	}

	public static ProcessCheck dtoToEntity(ProcessCheckDto dto) {
		if (dto == null) {
			return null;
		}
		ProcessCheck result = new ProcessCheck();
		result.setId(dto.getId());
		result.setName(dto.getName());
		result.setDescription(dto.getDescription());
		result.setPrivacy(dto.isPrivacy());
		result.setType(dto.getType());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}

	public static List<ProcessCheckDto> entityToDto(List<ProcessCheck> entities, boolean loadRelations) {
		List<ProcessCheckDto> checks = new ArrayList<>();
		for (ProcessCheck check : entities) {
			checks.add(entityToDto(check, loadRelations));
		}
		return checks;
	}

}
