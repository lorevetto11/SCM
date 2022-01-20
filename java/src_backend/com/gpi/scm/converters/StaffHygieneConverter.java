package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.StaffHygiene;
import com.gpi.scm.generic.dtos.StaffHygieneDto;



public class StaffHygieneConverter extends GenericConverter {
	public static StaffHygieneDto entityToDto(StaffHygiene entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		StaffHygieneDto result = new StaffHygieneDto();
		result.setId(entity.getId());
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setContext(ContextConverter.entityToDto(entity.getContext(), false));
			result.setPrerequisiteType(PrerequisiteTypeConverter.entityToDto(entity.getPrerequisiteType(), true));
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(), false));
			result.setRole(UserRoleConverter.entityToDto(entity.getRole(), false));

		}
		return result;
	}

	public static StaffHygiene dtoToEntity(StaffHygieneDto dto) {
		if (dto == null) {
			return null;
		}
		StaffHygiene result = new StaffHygiene();
		result.setId(dto.getId());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<StaffHygieneDto> entityToDto(List<StaffHygiene> entities, boolean loadRelations) {
		List<StaffHygieneDto> hygienes = new ArrayList<>();
		for (StaffHygiene hygiene : entities) {
			hygienes.add(entityToDto(hygiene, loadRelations));
		}
		return hygienes;
	}

}
