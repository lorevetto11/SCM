package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.AirConditioningType;
import com.gpi.scm.generic.dtos.AirConditioningTypeDto;



public class AirConditioningTypeConverter extends GenericConverter {
	public static AirConditioningTypeDto entityToDto(AirConditioningType entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		AirConditioningTypeDto result = new AirConditioningTypeDto();
		result.setId(entity.getId());
		result.setName(entity.getName());
		result.setDescription(entity.getDescription());
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(), false));
			result.setShape(ShapeConverter.entityToDto(entity.getShape(), false));

		}
		return result;
	}

	public static AirConditioningType dtoToEntity(AirConditioningTypeDto dto) {
		if (dto == null) {
			return null;
		}
		AirConditioningType result = new AirConditioningType();
		result.setId(dto.getId());
		result.setName(dto.getName());
		result.setDescription(dto.getDescription());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<AirConditioningTypeDto> entityToDto(List<AirConditioningType> entities, boolean loadRelations) {
		List<AirConditioningTypeDto> airconditionings = new ArrayList<>();
		for (AirConditioningType airconditioning : entities) {
			airconditionings.add(entityToDto(airconditioning, loadRelations));
		}
		return airconditionings;
	}

}
