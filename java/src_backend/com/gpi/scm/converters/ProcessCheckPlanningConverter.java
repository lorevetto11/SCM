
package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.ProcessCheckPlanning;
import com.gpi.scm.generic.dtos.ProcessCheckPlanningDto;

public class ProcessCheckPlanningConverter extends GenericConverter {
	public static ProcessCheckPlanningDto entityToDto(ProcessCheckPlanning entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		ProcessCheckPlanningDto result = new ProcessCheckPlanningDto();
		result.setId(entity.getId());
		result.setDate(entity.getDate());
		result.setStatus(entity.getStatus());
		result.setCloseDate(entity.getCloseDate());
		result.setStartDate(entity.getStartDate());
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(), false));
			result.setCompany(OrganizationConverter.entityToDto(entity.getCompany(), false));
			result.setProcesschecks(ProcessCheckConverter.entityToDto(entity.getProcessChecks(), false));
		}
		return result;
	}

	public static ProcessCheckPlanning dtoToEntity(ProcessCheckPlanningDto dto) {
		if (dto == null) {
			return null;
		}
		ProcessCheckPlanning result = new ProcessCheckPlanning();
		result.setId(dto.getId());
		result.setDate(dto.getDate());
		result.setStatus(dto.getStatus());
		result.setCloseDate(dto.getCloseDate());
		result.setStartDate(dto.getStartDate());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}

	public static List<ProcessCheckPlanningDto> entityToDto(List<ProcessCheckPlanning> entities, boolean loadRelations) {
		List<ProcessCheckPlanningDto> checks = new ArrayList<>();
		for (ProcessCheckPlanning check : entities) {
			checks.add(entityToDto(check, loadRelations));
		}
		return checks;
	}

}
