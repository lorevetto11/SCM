package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.AnalysisValue;
import com.gpi.scm.generic.dtos.AnalysisValueDto;



public class AnalysisValueConverter extends GenericConverter {
	public static AnalysisValueDto entityToDto(AnalysisValue entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		AnalysisValueDto result = new AnalysisValueDto();
		result.setId(entity.getId());
		result.setDate(entity.getDate());
		result.setNote(entity.getNote());
		result.setValue(entity.getValue());
	
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setContext(ContextConverter.entityToDto(entity.getContext(), false));
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(), false));
			result.setAnalysisParameter(AnalysisParameterConverter.entityToDto(entity.getAnalysisParameter(), false));

		}
		return result;
	}

	public static AnalysisValue dtoToEntity(AnalysisValueDto dto) {
		if (dto == null) {
			return null;
		}
		AnalysisValue result = new AnalysisValue();
		result.setId(dto.getId());
		result.setDate(dto.getDate());
		result.setNote(dto.getNote());
		result.setValue(dto.getValue());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<AnalysisValueDto> entityToDto(List<AnalysisValue> entities, boolean loadRelations) {
		List<AnalysisValueDto> values = new ArrayList<>();
		for (AnalysisValue value : entities) {
			values.add(entityToDto(value, loadRelations));
		}
		return values;
	}

}
