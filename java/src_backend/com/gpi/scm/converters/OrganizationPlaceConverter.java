package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.OrganizationPlace;
import com.gpi.scm.generic.dtos.OrganizationPlaceDto;

public class OrganizationPlaceConverter extends GenericConverter {
	public static OrganizationPlaceDto entityToDto(OrganizationPlace entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		OrganizationPlaceDto result = new OrganizationPlaceDto();
		result.setId(entity.getId());
		result.setName(entity.getName());
		result.setAddress(entity.getAddress());
		result.setDescription(entity.getDescription());
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(),false));

		}
		return result;
	}

	public static OrganizationPlace dtoToEntity(OrganizationPlaceDto dto) {
		if (dto == null) {
			return null;
		}
		OrganizationPlace result = new OrganizationPlace();
		result.setId(dto.getId());
		result.setName(dto.getName());
		result.setAddress(dto.getAddress());
		result.setDescription(dto.getDescription());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<OrganizationPlaceDto> entityToDto(List<OrganizationPlace> entities, boolean loadRelations) {
		List<OrganizationPlaceDto> orgPlaces = new ArrayList<>();
		for (OrganizationPlace orgPlace : entities) {
			orgPlaces.add(entityToDto(orgPlace, loadRelations));
		}
		return orgPlaces;
	}

}