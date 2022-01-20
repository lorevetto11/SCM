package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.Outcome;
import com.gpi.scm.generic.dtos.OutcomeDto;

public class OutcomeConverter extends GenericConverter {

	public static OutcomeDto entityToDto(Outcome entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		OutcomeDto result = new OutcomeDto();
		result.setId(entity.getId());
		result.setNote(entity.getNote());
		result.setResult(entity.isResult());
		GenericConverter.entityToDto(result, entity);
		result.setUser(UserConverter.entityToDto(entity.getUser(), false));
		if (loadRelations) {
			result.setContext(ContextConverter.entityToDto(entity.getContext(), false));
			result.setMonitoring(MonitoringConverter.entityToDto(entity.getMonitoring(), false));
		}
		return result;
	}

	public static Outcome dtoToEntity(OutcomeDto dto) {
		if (dto == null) {
			return null;
		}
		Outcome result = new Outcome();
		result.setId(dto.getId());
		result.setNote(dto.getNote());
		result.setResult(dto.isResult());
		
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<OutcomeDto> entityToDto(List<Outcome> entities, boolean loadRelations) {
		List<OutcomeDto> outcomes = new ArrayList<>();
		for (Outcome outcome : entities) {
			outcomes.add(entityToDto(outcome, loadRelations));
		}
		return outcomes;
	}

}
