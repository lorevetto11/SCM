package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.UserProfile;
import com.gpi.scm.generic.dtos.UserProfileDto;

public class UserProfileConverter {


	public static UserProfileDto entityToDto(UserProfile entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		UserProfileDto result = new UserProfileDto();
		result.setId(entity.getId());
		result.setName(entity.getName());
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
		//	result.setRoles(UserRoleConverter.entityToDto(entity.getRoless(), false));
			result.setGrants(GrantConverter.entityToDto(entity.getGrants(), false));
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(), false));


		}
		return result;
	}

	public static UserProfile dtoToEntity(UserProfileDto dto) {
		if (dto == null) {
			return null;
		}
		UserProfile result = new UserProfile();
		result.setId(dto.getId());
		result.setName(dto.getName());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<UserProfileDto> entityToDto(List<UserProfile> entities, boolean loadRelations) {
		List<UserProfileDto> profiles = new ArrayList<>();
		for (UserProfile profile : entities) {
			profiles.add(entityToDto(profile, loadRelations));
		}
		return profiles;
	}
	public static List<UserProfile> dtoToEntity(List<UserProfileDto> entities) {
		List<UserProfile> profiles = new ArrayList<>();
		for (UserProfileDto profile : entities) {
			profiles.add(dtoToEntity(profile));
		}
		return profiles;
	}
}