
package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.ProcessCheckOutcome;
import com.gpi.scm.generic.dtos.ProcessCheckOutcomeDto;

public class ProcessCheckOutcomeConverter extends GenericConverter {
	public static ProcessCheckOutcomeDto entityToDto(ProcessCheckOutcome entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		ProcessCheckOutcomeDto result = new ProcessCheckOutcomeDto();
		result.setId(entity.getId());
		result.setEvidence(entity.getEvidence());

		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setContext(ContextConverter.entityToDto(entity.getContext(), false));
			result.setNonCompliance(NonComplianceConverter.entityToDto(entity.getNonCompliance(), false));
			result.setProcesscheckPlanning(ProcessCheckPlanningConverter.entityToDto(entity.getProcesscheckPlanning(), false));
			result.setProcessCheck(ProcessCheckConverter.entityToDto(entity.getProcessCheck(), false));
		}
		return result;
	}

	public static ProcessCheckOutcome dtoToEntity(ProcessCheckOutcomeDto dto) {
		if (dto == null) {
			return null;
		}
		ProcessCheckOutcome result = new ProcessCheckOutcome();
		result.setId(dto.getId());
		result.setEvidence(dto.getEvidence());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}

	public static List<ProcessCheckOutcomeDto> entityToDto(List<ProcessCheckOutcome> entities, boolean loadRelations) {
		List<ProcessCheckOutcomeDto> outcomes = new ArrayList<>();
		for (ProcessCheckOutcome outcome : entities) {
			outcomes.add(entityToDto(outcome, loadRelations));
		}
		return outcomes;
	}

}
