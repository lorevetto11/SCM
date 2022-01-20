package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.EquipmentPrerequisite;
import com.gpi.scm.generic.dtos.EquipmentPrerequisiteDto;



public class EquipmentPrerequisiteConverter extends GenericConverter {
	public static EquipmentPrerequisiteDto entityToDto(EquipmentPrerequisite entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		EquipmentPrerequisiteDto result = new EquipmentPrerequisiteDto();
		result.setId(entity.getId());
		result.setName(entity.getName());
		result.setDescription(entity.getDescription());
		GenericConverter.entityToDto(result, entity);
		result.setPrerequisiteType(PrerequisiteTypeConverter.entityToDto(entity.getPrerequisiteType(), false));

		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setShape(ShapeConverter.entityToDto(entity.getShape(), false));
			result.setLayout(LayoutConverter.entityToDto(entity.getLayout(), false));
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(), false));
			result.setFloor(FloorConverter.entityToDto(entity.getFloor(), false));
			result.setEquipmentType(EquipmentTypeConverter.entityToDto(entity.getEquipmentType(), false));
			result.setEquipment(EquipmentConverter.entityToDto(entity.getEquipment(), false));
			result.setContext(ContextConverter.entityToDto(entity.getContext(), true));


		}
		return result;
	}

	public static EquipmentPrerequisite dtoToEntity(EquipmentPrerequisiteDto dto) {
		if (dto == null) {
			return null;
		}
		EquipmentPrerequisite result = new EquipmentPrerequisite();
		result.setId(dto.getId());
		result.setName(dto.getName());
		result.setDescription(dto.getDescription());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<EquipmentPrerequisiteDto> entityToDto(List<EquipmentPrerequisite> entities, boolean loadRelations) {
		List<EquipmentPrerequisiteDto> equipments = new ArrayList<>();
		for (EquipmentPrerequisite equipment : entities) {
			equipments.add(entityToDto(equipment, loadRelations));
		}
		return equipments;
	}

}
