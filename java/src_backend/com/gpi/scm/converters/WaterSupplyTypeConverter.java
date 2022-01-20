package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.WaterSupplyType;
import com.gpi.scm.generic.dtos.WaterSupplyTypeDto;



public class WaterSupplyTypeConverter extends GenericConverter {
	public static WaterSupplyTypeDto entityToDto(WaterSupplyType entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		WaterSupplyTypeDto result = new WaterSupplyTypeDto();
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

	public static WaterSupplyType dtoToEntity(WaterSupplyTypeDto dto) {
		if (dto == null) {
			return null;
		}
		WaterSupplyType result = new WaterSupplyType();
		result.setId(dto.getId());
		result.setName(dto.getName());
		result.setDescription(dto.getDescription());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<WaterSupplyTypeDto> entityToDto(List<WaterSupplyType> entities, boolean loadRelations) {
		List<WaterSupplyTypeDto> watersupplys = new ArrayList<>();
		for (WaterSupplyType watersupply : entities) {
			watersupplys.add(entityToDto(watersupply, loadRelations));
		}
		return watersupplys;
	}

}
