package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.EquipmentType;
import com.gpi.scm.generic.dtos.EquipmentTypeDto;



public class EquipmentTypeConverter extends GenericConverter {
	public static EquipmentTypeDto entityToDto(EquipmentType entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		EquipmentTypeDto result = new EquipmentTypeDto();
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

	public static EquipmentType dtoToEntity(EquipmentTypeDto dto) {
		if (dto == null) {
			return null;
		}
		EquipmentType result = new EquipmentType();
		result.setId(dto.getId());
		result.setName(dto.getName());
		result.setDescription(dto.getDescription());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<EquipmentTypeDto> entityToDto(List<EquipmentType> entities, boolean loadRelations) {
		List<EquipmentTypeDto> equipments = new ArrayList<>();
		for (EquipmentType equipment : entities) {
			equipments.add(entityToDto(equipment, loadRelations));
		}
		return equipments;
	}

}
