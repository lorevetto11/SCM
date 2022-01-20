package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.Cleaning;
import com.gpi.scm.generic.dtos.BasePrerequisiteDto;



public class CleaningConverter extends GenericConverter {
	public static BasePrerequisiteDto entityToDto(Cleaning entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		BasePrerequisiteDto result = new BasePrerequisiteDto();
		result.setId(entity.getId());
		result.setName(entity.getName());
		result.setDescription(entity.getDescription());
		result.setPrerequisiteType(PrerequisiteTypeConverter.entityToDto(entity.getPrerequisiteType(), false));
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setShape(ShapeConverter.entityToDto(entity.getShape(), false));
			result.setLayout(LayoutConverter.entityToDto(entity.getLayout(), false));
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(), false));
			result.setFloor(FloorConverter.entityToDto(entity.getFloor(), false));
			result.setContext(ContextConverter.entityToDto(entity.getContext(), true));



		}
		return result;
	}

	public static Cleaning dtoToEntity(BasePrerequisiteDto dto) {
		if (dto == null) {
			return null;
		}
		Cleaning result = new Cleaning();
		result.setId(dto.getId());
		result.setName(dto.getName());
		result.setDescription(dto.getDescription());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<BasePrerequisiteDto> entityToDto(List<Cleaning> entities, boolean loadRelations) {
		List<BasePrerequisiteDto> profiles = new ArrayList<>();
		for (Cleaning profile : entities) {
			profiles.add(entityToDto(profile, loadRelations));
		}
		return profiles;
	}

}
