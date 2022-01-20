package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.Floor;
import com.gpi.scm.generic.dtos.FloorDto;

public class FloorConverter extends GenericConverter {
	public static FloorDto entityToDto(Floor entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		FloorDto result = new FloorDto();
		result.setId(entity.getId());
		result.setDescription(entity.getDescription());
		result.setHeight(entity.getHeight());
		result.setWidth(entity.getWidth());
		result.setName(entity.getName());
		result.setOrder(entity.getOrder());
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setContext(ContextConverter.entityToDto(entity.getContext(), false));
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(), false));


		}
		return result;
	}
	public static Floor dtoToEntity(FloorDto dto) {
		if (dto == null) {
			return null;
		}
		Floor result = new Floor();
		result.setId(dto.getId());
		result.setDescription(dto.getDescription());
		result.setHeight(dto.getHeight());
		result.setWidth(dto.getWidth());
		result.setName(dto.getName());
		result.setOrder(dto.getOrder());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<FloorDto> entityToDto(List<Floor> entities, boolean loadRelations) {
		List<FloorDto> floors = new ArrayList<>();
		for (Floor floor : entities) {
			floors.add(entityToDto(floor, loadRelations));
		}
		return floors;
	}

}
