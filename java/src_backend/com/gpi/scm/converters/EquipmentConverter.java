package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.Equipment;
import com.gpi.scm.generic.dtos.EquipmentDto;



public class EquipmentConverter extends GenericConverter {
	public static EquipmentDto entityToDto(Equipment entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		EquipmentDto result = new EquipmentDto();
		result.setId(entity.getId());
		result.setName(entity.getName());
		result.setDescription(entity.getDescription());
		result.setMaintainer(entity.getMaintainer());
	//	result.setSupplier(entity.getSupplier());
		result.setStartupDate(entity.getStartupDate());
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setEquipmentType(EquipmentTypeConverter.entityToDto(entity.getEquipmentType(), false));
			result.setContext(ContextConverter.entityToDto(entity.getContext(), false));
			result.setFrequency(FrequencyConverter.entityToDto(entity.getFrequency(), false));
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(), false));
			result.setSupplier(SupplierConverter.entityToDto(entity.getSupplier(), false));

		}
		return result;
	}

	public static Equipment dtoToEntity(EquipmentDto dto) {
		if (dto == null) {
			return null;
		}
		Equipment result = new Equipment();
		result.setId(dto.getId());
		result.setName(dto.getName());
		result.setDescription(dto.getDescription());
		result.setMaintainer(dto.getMaintainer());
		//result.setSupplier(dto.getSupplier());
		result.setStartupDate(dto.getStartupDate());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<EquipmentDto> entityToDto(List<Equipment> entities, boolean loadRelations) {
		List<EquipmentDto> equipments = new ArrayList<>();
		for (Equipment equipment : entities) {
			equipments.add(entityToDto(equipment, loadRelations));
		}
		return equipments;
	}

}
