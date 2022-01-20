package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.AnalysisParameter;
import com.gpi.scm.generic.dtos.AnalysisParameterDto;



public class AnalysisParameterConverter extends GenericConverter {
	public static AnalysisParameterDto entityToDto(AnalysisParameter entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		AnalysisParameterDto result = new AnalysisParameterDto();
		result.setId(entity.getId());
		result.setName(entity.getName());
		result.setDescription(entity.getDescription());
		result.setThresholdValue(entity.getThresholdValue());

		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setUserRole(UserRoleConverter.entityToDto(entity.getUserRole(), false));
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(), false));


		}
		return result;
	}

	public static AnalysisParameter dtoToEntity(AnalysisParameterDto dto) {
		if (dto == null) {
			return null;
		}
		AnalysisParameter result = new AnalysisParameter();
		result.setId(dto.getId());
		result.setName(dto.getName());
		result.setDescription(dto.getDescription());
		result.setThresholdValue(dto.getThresholdValue());

		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<AnalysisParameterDto> entityToDto(List<AnalysisParameter> entities, boolean loadRelations) {
		List<AnalysisParameterDto> parameters = new ArrayList<>();
		for (AnalysisParameter parameter : entities) {
			parameters.add(entityToDto(parameter, loadRelations));
		}
		return parameters;
	}

}
