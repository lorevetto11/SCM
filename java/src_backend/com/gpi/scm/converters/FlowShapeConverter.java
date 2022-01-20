package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.FlowAnchorPoint;
import com.gpi.scm.ejb.entities.FlowShape;
import com.gpi.scm.generic.dtos.FlowAnchorPointDto;
import com.gpi.scm.generic.dtos.FlowShapeDto;
import com.gpi.scm.generic.utils.CommonEnums.flowShapeType;

public class FlowShapeConverter extends GenericConverter {
	public static FlowShapeDto entityToDto(FlowShape entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		FlowShapeDto result = new FlowShapeDto();
		result.setId(entity.getId());
		result.setzOrder(entity.getOrder());
		result.setCenterX(entity.getCenterX());
		result.setCenterY(entity.getCenterY());
		result.setDescription(entity.getDescription());
		result.setFillColor(entity.getFillColor());
		result.setName(entity.getName());
		result.setHeight(entity.getHeight());
		result.setWidth(entity.getWidth());
		result.setType((flowShapeType)entity.getType());
		GenericConverter.entityToDto(result, entity);

		if (loadRelations) {
			result.setDiagram(DiagramConverter.entityToDto(entity.getDiagram(), false));
			
			if(entity.getAnchorPoints() != null) {
				result.setAnchorPoints(new ArrayList<FlowAnchorPointDto>());
				for(FlowAnchorPoint anchorPoint : entity.getAnchorPoints()){
					result.getAnchorPoints().add(FlowAnchorPointConverter.entityToDto(anchorPoint, loadRelations));
				}
			}
			
			result.setElement(FlowElementConverter.entityToDto(entity.getElement(), loadRelations));
		}
		return result;
	}
	public static FlowShape dtoToEntity(FlowShapeDto dto) {
		if (dto == null) {
			return null;
		}
		FlowShape result = new FlowShape();
		result.setId(dto.getId());
		result.setName(dto.getName());
		result.setOrder(dto.getzOrder());
		result.setCenterX(dto.getCenterX());
		result.setCenterY(dto.getCenterY());
		result.setDescription(dto.getDescription());
		result.setFillColor(dto.getFillColor());
		result.setHeight(dto.getHeight());
		result.setWidth(dto.getWidth());
		result.setType((flowShapeType)dto.getType());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<FlowShapeDto> entityToDto(List<FlowShape> entities, boolean loadRelations) {
		List<FlowShapeDto> shapes = new ArrayList<>();
		for (FlowShape shape : entities) {
			shapes.add(entityToDto(shape, loadRelations));
		}
		return shapes;
	}

}
