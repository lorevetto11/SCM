package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.Layout;
import com.gpi.scm.generic.dtos.LayoutDto;

public class LayoutConverter extends GenericConverter {
	public static LayoutDto entityToDto(Layout entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		LayoutDto result = new LayoutDto();
		result.setId(entity.getId());
		result.setDescription(entity.getDescription());
		result.setName(entity.getName());
		GenericConverter.entityToDto(result, entity);
		result.setRiskClass(RiskClassConverter.entityToDto(entity.getRiskClass(), false));
		result.setShape(ShapeConverter.entityToDto(entity.getShape(), false));
		result.setPrerequisiteType(PrerequisiteTypeConverter.entityToDto(entity.getPrerequisiteType(), false));

		
		if (loadRelations) {
			result.setFloor(FloorConverter.entityToDto(entity.getFloor(), false));
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(), false));
		}
		return result;
	}
	public static Layout dtoToEntity(LayoutDto dto) {
		if (dto == null) {
			return null;
		}
		Layout result = new Layout();
		result.setId(dto.getId());
		result.setDescription(dto.getDescription());
		result.setName(dto.getName());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<LayoutDto> entityToDto(List<Layout> entities, boolean loadRelations) {
		List<LayoutDto> layouts = new ArrayList<>();
		for (Layout layout : entities) {
			layouts.add(entityToDto(layout, loadRelations));
		}
		return layouts;
	}
}
