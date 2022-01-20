package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.WaterSupply;
import com.gpi.scm.generic.dtos.BasePrerequisiteDto;



public class WaterSupplyConverter extends GenericConverter {
	public static BasePrerequisiteDto entityToDto(WaterSupply entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		BasePrerequisiteDto result = new BasePrerequisiteDto();
		result.setId(entity.getId());
		result.setName(entity.getName());
		result.setDescription(entity.getDescription());
		GenericConverter.entityToDto(result, entity);
		result.setPrerequisiteType(PrerequisiteTypeConverter.entityToDto(entity.getPrerequisiteType(), false));

		if (loadRelations) {
			result.setShape(ShapeConverter.entityToDto(entity.getShape(), false));
			result.setLayout(LayoutConverter.entityToDto(entity.getLayout(), false));
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(), false));
			result.setFloor(FloorConverter.entityToDto(entity.getFloor(), false));
			result.setContext(ContextConverter.entityToDto(entity.getContext(), true));



		}
		return result;
	}

	public static WaterSupply dtoToEntity(BasePrerequisiteDto dto) {
		if (dto == null) {
			return null;
		}
		WaterSupply result = new WaterSupply();
		result.setId(dto.getId());
		result.setName(dto.getName());
		result.setDescription(dto.getDescription());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<BasePrerequisiteDto> entityToDto(List<WaterSupply> entities, boolean loadRelations) {
		List<BasePrerequisiteDto> watersupplys = new ArrayList<>();
		for (WaterSupply watersupply : entities) {
			watersupplys.add(entityToDto(watersupply, loadRelations));
		}
		return watersupplys;
	}

}
