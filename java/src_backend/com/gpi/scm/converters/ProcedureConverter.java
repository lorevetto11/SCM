package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.Procedure;
import com.gpi.scm.generic.dtos.ProcedureDto;

public class ProcedureConverter extends GenericConverter {
	public static ProcedureDto entityToDto(Procedure entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		ProcedureDto result = new ProcedureDto();
		result.setId(entity.getId());
		result.setTitle(entity.getTitle());
		result.setPrivacy(entity.getPrivacy());
		result.setPurpose(entity.getPurpose());
		result.setDescription(entity.getDescription());
		result.setActivities(entity.getActivities());
		result.setResults_check(entity.getResults_check());
		result.setProcess_check(entity.getProcess_check());
		result.setRevision(entity.getRevision());
		result.setEquipments(entity.getEquipments());
		result.setPrerequisiteType(PrerequisiteTypeConverter.entityToDto(entity.getPrerequisiteType(), false));
		result.setUserRole(UserRoleConverter.entityToDto(entity.getUserRole(), false));

		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(), false));
			result.setRiskClass(RiskClassConverter.entityToDto(entity.getRiskClass(), false));
		}
		return result;
	}
	public static Procedure dtoToEntity(ProcedureDto dto) {
		if (dto == null) {
			return null;
		}
		Procedure result = new Procedure();
		result.setId(dto.getId());
		result.setTitle(dto.getTitle());
		result.setPrivacy(dto.getPrivacy());
		result.setPurpose(dto.getPurpose());
		result.setDescription(dto.getDescription());
		result.setActivities(dto.getActivities());
		result.setResults_check(dto.getResults_check());
		result.setProcess_check(dto.getProcess_check());
		result.setRevision(dto.getRevision());
		result.setEquipments(dto.getEquipments());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<ProcedureDto> entityToDto(List<Procedure> entities, boolean loadRelations) {
		List<ProcedureDto> procedures = new ArrayList<>();
		for (Procedure procedure : entities) {
			procedures.add(entityToDto(procedure, loadRelations));
		}
		return procedures;
	}

}

