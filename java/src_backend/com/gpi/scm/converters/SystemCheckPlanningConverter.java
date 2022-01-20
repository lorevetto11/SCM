
package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.SystemCheckPlanning;
import com.gpi.scm.generic.dtos.SystemCheckPlanningDto;

public class SystemCheckPlanningConverter extends GenericConverter {
	public static SystemCheckPlanningDto entityToDto(SystemCheckPlanning entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		SystemCheckPlanningDto result = new SystemCheckPlanningDto();
		result.setId(entity.getId());
		result.setDate(entity.getDate());
		result.setStatus(entity.getStatus());
		result.setCloseDate(entity.getCloseDate());
		result.setStartDate(entity.getStartDate());
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(), false));
			result.setCompany(OrganizationConverter.entityToDto(entity.getCompany(), false));
			result.setSystemchecks(SystemCheckConverter.entityToDto(entity.getSystemChecks(), false));
		}
		return result;
	}

	public static SystemCheckPlanning dtoToEntity(SystemCheckPlanningDto dto) {
		if (dto == null) {
			return null;
		}
		SystemCheckPlanning result = new SystemCheckPlanning();
		result.setId(dto.getId());
		result.setDate(dto.getDate());
		result.setStatus(dto.getStatus());
		result.setCloseDate(dto.getCloseDate());
		result.setStartDate(dto.getStartDate());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}

	public static List<SystemCheckPlanningDto> entityToDto(List<SystemCheckPlanning> entities, boolean loadRelations) {
		List<SystemCheckPlanningDto> checks = new ArrayList<>();
		for (SystemCheckPlanning check : entities) {
			checks.add(entityToDto(check, loadRelations));
		}
		return checks;
	}

}
