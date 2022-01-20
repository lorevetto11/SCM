package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.OrganizationCertification;
import com.gpi.scm.generic.dtos.OrganizationCertificationDto;

public class OrganizationCertificationConverter extends GenericConverter {
	public static OrganizationCertificationDto entityToDto(OrganizationCertification entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		OrganizationCertificationDto result = new OrganizationCertificationDto();
		result.setId(entity.getId());
		result.setName(entity.getName());
		result.setDescription(entity.getDescription());
		result.setAuthority(entity.getAuthority());
		result.setDate(entity.getDate());
		result.setExpiryDate(entity.getExpiryDate());
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(),false));
			result.setContext(ContextConverter.entityToDto(entity.getContext(), false));

		}
		return result;
	}

	public static OrganizationCertification dtoToEntity(OrganizationCertificationDto dto) {
		if (dto == null) {
			return null;
		}
		OrganizationCertification result = new OrganizationCertification();
		result.setId(dto.getId());
		result.setName(dto.getName());
		result.setDescription(dto.getDescription());
		result.setAuthority(dto.getAuthority());
		result.setDate(dto.getDate());
		result.setExpiryDate(dto.getExpiryDate());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<OrganizationCertificationDto> entityToDto(List<OrganizationCertification> entities, boolean loadRelations) {
		List<OrganizationCertificationDto> orgCertifications = new ArrayList<>();
		for (OrganizationCertification orgCertification : entities) {
			orgCertifications.add(entityToDto(orgCertification, loadRelations));
		}
		return orgCertifications;
	}

}