package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.PestControlType;
import com.gpi.scm.generic.dtos.PestControlTypeDto;



public class PestControlTypeConverter extends GenericConverter {
	public static PestControlTypeDto entityToDto(PestControlType entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		PestControlTypeDto result = new PestControlTypeDto();
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

	public static PestControlType dtoToEntity(PestControlTypeDto dto) {
		if (dto == null) {
			return null;
		}
		PestControlType result = new PestControlType();
		result.setId(dto.getId());
		result.setName(dto.getName());
		result.setDescription(dto.getDescription());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<PestControlTypeDto> entityToDto(List<PestControlType> entities, boolean loadRelations) {
		List<PestControlTypeDto> pestcontrols = new ArrayList<>();
		for (PestControlType pestcontrol : entities) {
			pestcontrols.add(entityToDto(pestcontrol, loadRelations));
		}
		return pestcontrols;
	}

}
