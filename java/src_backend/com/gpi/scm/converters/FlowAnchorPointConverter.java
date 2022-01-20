package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.FlowAnchorPoint;
import com.gpi.scm.generic.dtos.FlowAnchorPointDto;
import com.gpi.scm.generic.utils.CommonEnums.flowElementsType;

public class FlowAnchorPointConverter extends GenericConverter {
	public static FlowAnchorPointDto entityToDto(FlowAnchorPoint entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		FlowAnchorPointDto result = new FlowAnchorPointDto();
		result.setId(entity.getId());
		result.setOrder(entity.getOrder());
		result.setDescription(entity.getDescription());
		result.setName(entity.getName());
		result.setHeight(entity.getHeight());
		result.setWidth(entity.getWidth());
		result.setTranslationX(entity.getTranslationX());
		result.setTranslationY(entity.getTranslationY());
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setShape(FlowShapeConverter.entityToDto(entity.getShape(), false));
		}
		return result;
	}
	public static FlowAnchorPoint dtoToEntity(FlowAnchorPointDto dto) {
		if (dto == null) {
			return null;
		}
		FlowAnchorPoint result = new FlowAnchorPoint();
		result.setId(dto.getId());
		result.setName(dto.getName());
		result.setOrder(dto.getOrder());
		result.setDescription(dto.getDescription());
		result.setHeight(dto.getHeight());
		result.setWidth(dto.getWidth());
		result.setTranslationX(dto.getTranslationX());
		result.setTranslationY(dto.getTranslationY());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<FlowAnchorPointDto> entityToDto(List<FlowAnchorPoint> entities, boolean loadRelations) {
		List<FlowAnchorPointDto> points = new ArrayList<>();
		for (FlowAnchorPoint point : entities) {
			points.add(entityToDto(point, loadRelations));
		}
		return points;
	}

}
