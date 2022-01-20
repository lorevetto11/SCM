
package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.SystemCheckOutcome;
import com.gpi.scm.generic.dtos.SystemCheckOutcomeDto;

public class SystemCheckOutcomeConverter extends GenericConverter {
	public static SystemCheckOutcomeDto entityToDto(SystemCheckOutcome entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		SystemCheckOutcomeDto result = new SystemCheckOutcomeDto();
		result.setId(entity.getId());
		result.setEvidence(entity.getEvidence());

		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setContext(ContextConverter.entityToDto(entity.getContext(), false));
			result.setNonCompliance(NonComplianceConverter.entityToDto(entity.getNonCompliance(), false));
			result.setSystemcheckPlanning(SystemCheckPlanningConverter.entityToDto(entity.getSystemcheckPlanning(), false));
			result.setSystemcheckRequirement(SystemCheckRequirementConverter.entityToDto(entity.getSystemcheckRequirement(), false));
		}
		return result;
	}

	public static SystemCheckOutcome dtoToEntity(SystemCheckOutcomeDto dto) {
		if (dto == null) {
			return null;
		}
		SystemCheckOutcome result = new SystemCheckOutcome();
		result.setId(dto.getId());
		result.setEvidence(dto.getEvidence());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}

	public static List<SystemCheckOutcomeDto> entityToDto(List<SystemCheckOutcome> entities, boolean loadRelations) {
		List<SystemCheckOutcomeDto> outcomes = new ArrayList<>();
		for (SystemCheckOutcome outcome : entities) {
			outcomes.add(entityToDto(outcome, loadRelations));
		}
		return outcomes;
	}

}
