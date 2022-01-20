package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.Shape;
import com.gpi.scm.generic.dtos.ShapeDto;

public class ShapeConverter extends GenericConverter {
	public static ShapeDto entityToDto(Shape entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		ShapeDto result = new ShapeDto();
		result.setId(entity.getId());
		result.setOrder(entity.getOrder());
		result.setRadius(entity.getRadius());
		result.setSizeX(entity.getSizeX());
		result.setSizeY(entity.getSizeY());
		result.setStartX(entity.getStartX());
		result.setStartY(entity.getStartY());
		result.setType(entity.getType());
		result.setColor(entity.getColor());
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {

		}
		return result;
	}
	public static Shape dtoToEntity(ShapeDto dto) {
		if (dto == null) {
			return null;
		}
		Shape result = new Shape();
		result.setId(dto.getId());
		result.setOrder(dto.getOrder());
		result.setRadius(dto.getRadius());
		result.setSizeX(dto.getSizeX());
		result.setSizeY(dto.getSizeY());
		result.setStartX(dto.getStartX());
		result.setStartY(dto.getStartY());
		result.setType(dto.getType());
		result.setColor(dto.getColor());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<ShapeDto> entityToDto(List<Shape> entities, boolean loadRelations) {
		List<ShapeDto> shapes = new ArrayList<>();
		for (Shape shape : entities) {
			shapes.add(entityToDto(shape, loadRelations));
		}
		return shapes;
	}

}
