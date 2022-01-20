package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.Diagram;
import com.gpi.scm.generic.dtos.DiagramDto;

public class DiagramConverter extends GenericConverter {
	public static DiagramDto entityToDto(Diagram entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		DiagramDto result = new DiagramDto();
		result.setId(entity.getId());
		result.setName(entity.getName());
		result.setDescription(entity.getDescription());
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(), false));
		}
		return result;
	}

	public static Diagram dtoToEntity(DiagramDto dto) {
		if (dto == null) {
			return null;
		}
		Diagram result = new Diagram();
		result.setId(dto.getId());
		result.setName(dto.getName());
		result.setDescription(dto.getDescription());

		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<DiagramDto> entityToDto(List<Diagram> entities, boolean loadRelations) {
		List<DiagramDto> diagrams = new ArrayList<>();
		for (Diagram diagram : entities) {
			diagrams.add(entityToDto(diagram, loadRelations));
		}
		return diagrams;
	}
	public static List<Diagram> dtoToEntity(List<DiagramDto> entities) {
		List<Diagram> diagrams = new ArrayList<>();
		for (DiagramDto diagram : entities) {
			diagrams.add(dtoToEntity(diagram));
		}
		return diagrams;
	}

}