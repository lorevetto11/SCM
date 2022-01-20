package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.UserRole;
import com.gpi.scm.generic.dtos.UserRoleDto;

public class UserRoleConverter {

	public static UserRoleDto entityToDto(UserRole entity, boolean loadRelations) {
		return entityToDto(entity, loadRelations, false);
	}
	
	public static UserRoleDto entityToDto(UserRole entity, boolean loadRelations, boolean loadProfileRelations) {
		if (entity == null) {
			return null;
		}
		UserRoleDto result = new UserRoleDto();
		result.setId(entity.getId());
		result.setName(entity.getName());
		result.setDescription(entity.getDescription());
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(), false));
			result.setProfiles(UserProfileConverter.entityToDto(entity.getProfiles(), loadProfileRelations));

		}
		return result;
	}

	public static UserRole dtoToEntity(UserRoleDto dto) {
		if (dto == null) {
			return null;
		}
		UserRole result = new UserRole();
		result.setId(dto.getId());
		result.setName(dto.getName());
		result.setDescription(dto.getDescription());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<UserRoleDto> entityToDto(List<UserRole> entities, boolean loadRelations) {
		List<UserRoleDto> roles = new ArrayList<>();
		for (UserRole role : entities) {
			roles.add(entityToDto(role, loadRelations));
		}
		return roles;
	}
	public static List<UserRole> dtoToEntity(List<UserRoleDto> entities) {
		List<UserRole> roles = new ArrayList<>();
		for (UserRoleDto role : entities) {
			roles.add(dtoToEntity(role));
		}
		return roles;
	}
}
