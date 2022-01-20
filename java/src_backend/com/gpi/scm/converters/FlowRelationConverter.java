package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.FlowRelation;
import com.gpi.scm.generic.dtos.FlowRelationDto;
import com.gpi.scm.generic.utils.CommonEnums.flowElementsType;

public class FlowRelationConverter extends GenericConverter {
	public static FlowRelationDto entityToDto(FlowRelation entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		FlowRelationDto result = new FlowRelationDto();
		result.setId(entity.getId());
		result.setDescription(entity.getDescription());
		result.setName(entity.getName());
		result.setOrder(entity.getOrder());
		result.setType(entity.getType());
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setStartAnchorPoint(FlowAnchorPointConverter.entityToDto(entity.getStartPoint(), loadRelations));
			result.setEndAnchorPoint(FlowAnchorPointConverter.entityToDto(entity.getEndPoint(), loadRelations));
		}
		return result;
	}
	public static FlowRelation dtoToEntity(FlowRelationDto dto) {
		if (dto == null) {
			return null;
		}
		FlowRelation result = new FlowRelation();
		result.setId(dto.getId());
		result.setName(dto.getName());
		result.setDescription(dto.getDescription());
		result.setOrder(dto.getOrder());
		result.setType(dto.getType());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<FlowRelationDto> entityToDto(List<FlowRelation> entities, boolean loadRelations) {
		List<FlowRelationDto> relations = new ArrayList<>();
		for (FlowRelation relation : entities) {
			relations.add(entityToDto(relation, loadRelations));
		}
		return relations;
	}

}
