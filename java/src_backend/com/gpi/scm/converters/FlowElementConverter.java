package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.FlowElement;
import com.gpi.scm.generic.dtos.FlowElementDto;
import com.gpi.scm.generic.utils.CommonEnums.ccpType;
import com.gpi.scm.generic.utils.CommonEnums.flowElementsType;

public class FlowElementConverter extends GenericConverter {
	public static FlowElementDto entityToDto(FlowElement entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		FlowElementDto result = new FlowElementDto();
		result.setId(entity.getId());
		result.setDescription(entity.getDescription());
		result.setName(entity.getName());
		result.setRisk(entity.getRisk());
		result.setType((flowElementsType)entity.getType());
		result.setTypeCCP((ccpType)entity.getTypeCCP());
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setShape((FlowShapeConverter.entityToDto(entity.getShape(), false)));
			result.setMaterial(MaterialConverter.entityToDto(entity.getMaterial(), false));
			result.setContext(ContextConverter.entityToDto(entity.getContext(), loadRelations));
		}
		return result;
	}
	public static FlowElement dtoToEntity(FlowElementDto dto) {
		if (dto == null) {
			return null;
		}
		FlowElement result = new FlowElement();
		result.setId(dto.getId());
		result.setName(dto.getName());
		result.setDescription(dto.getDescription());
		result.setRisk(dto.getRisk());
		result.setType((flowElementsType)dto.getType());
		result.setTypeCCP((ccpType)dto.getTypeCCP());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<FlowElementDto> entityToDto(List<FlowElement> entities, boolean loadRelations) {
		List<FlowElementDto> elements = new ArrayList<>();
		for (FlowElement element : entities) {
			elements.add(entityToDto(element, loadRelations));
		}
		return elements;
	}

}
