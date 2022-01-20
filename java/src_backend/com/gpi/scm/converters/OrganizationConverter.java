package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.generic.dtos.OrganizationDto;

public class OrganizationConverter {

	public static OrganizationDto entityToDto(Organization entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		OrganizationDto result = new OrganizationDto();
		result.setId(entity.getId());
		result.setName(entity.getName());
		result.setDescription(entity.getDescription());
		result.setVatNumber(entity.getVatNumber());
		result.setLegalResidence(entity.getLegalResidence());
		result.setEmail(entity.getEmail());
		result.setPhone(entity.getPhone());
		result.setStatus(entity.getStatus());
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setOrganization(entityToDto(entity.getOrganization(), false));
			result.setUsers(UserConverter.entityToDto(entity.getUsers(), false));
			result.setRoles(UserRoleConverter.entityToDto(entity.getRoles(), false));
			result.setContext(ContextConverter.entityToDto(entity.getContext(), false));

		}
		return result;
	}

	public static Organization dtoToEntity(OrganizationDto dto) {
		if (dto == null) {
			return null;
		}
		Organization result = new Organization();
		result.setId(dto.getId());
		result.setName(dto.getName());
		result.setDescription(dto.getDescription());
		result.setVatNumber(dto.getVatNumber());
		result.setLegalResidence(dto.getLegalResidence());
		result.setEmail(dto.getEmail());
		result.setPhone(dto.getPhone());
		result.setStatus(dto.getStatus());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<OrganizationDto> entityToDto(List<Organization> entities, boolean loadRelations) {
		List<OrganizationDto> orgs = new ArrayList<>();
		for (Organization org : entities) {
			orgs.add(entityToDto(org, loadRelations));
		}
		return orgs;
	}
}
