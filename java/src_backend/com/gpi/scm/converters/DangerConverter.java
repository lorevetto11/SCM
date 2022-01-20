package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.Danger;
import com.gpi.scm.generic.dtos.DangerDto;



public class DangerConverter extends GenericConverter {
	public static DangerDto entityToDto(Danger entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		DangerDto result = new DangerDto();
		result.setId(entity.getId());
		result.setName(entity.getName());
		result.setDescription(entity.getDescription());
		result.setType(entity.getType());
		result.setRisk(entity.getRisk());
		result.setControlMeasure(entity.getControlMeasure());
		result.setCriticalLimit(entity.getCriticalLimit());
		result.setAcceptanceLimit(entity.getAcceptanceLimit());
		result.setProcedures(entity.getProcedures());
		result.setCcp(entity.isCcp());
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setContext(ContextConverter.entityToDto(entity.getContext(), false));
			result.setPrerequisiteType(PrerequisiteTypeConverter.entityToDto(entity.getPrerequisiteType(), true));
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(), false));
			result.setMaterials(MaterialConverter.entityToDto(entity.getMaterials(), false));

		}
		return result;
	}

	public static Danger dtoToEntity(DangerDto dto) {
		if (dto == null) {
			return null;
		}
		Danger result = new Danger();
		result.setId(dto.getId());
		result.setName(dto.getName());
		result.setDescription(dto.getDescription());
		result.setType(dto.getType());
		result.setRisk(dto.getRisk());
		result.setControlMeasure(dto.getControlMeasure());
		result.setCriticalLimit(dto.getCriticalLimit());
		result.setAcceptanceLimit(dto.getAcceptanceLimit());
		result.setProcedures(dto.getProcedures());
		result.setCcp(dto.isCcp());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<DangerDto> entityToDto(List<Danger> entities, boolean loadRelations) {
		List<DangerDto> dangers = new ArrayList<>();
		for (Danger danger : entities) {
			dangers.add(entityToDto(danger, loadRelations));
		}
		return dangers;
	}

}
