package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import com.gpi.scm.ejb.entities.User;
import com.gpi.scm.generic.dtos.UserDto;

public class UserConverter {

	public static UserDto entityToDto(User entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		UserDto result = new UserDto();
		try {	
			result.setId(entity.getId());
		}
		catch(Exception e)
		{
			throw new NoResultException("User" + entity.getId() + "");

		}
		result.setFirstname(entity.getFirstname());
		result.setLastname(entity.getLastname());
		result.setUsername(entity.getUsername());
		result.setEmail(entity.getEmail());
		result.setPhone(entity.getPhone());
		result.setStatus(entity.getStatus());
		result.setLanguage(entity.getLanguage());
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(), false));
			result.setRole(UserRoleConverter.entityToDto(entity.getRole(), false));
		}
		return result;
	}

	public static List<UserDto> entityToDto(List<User> entities, boolean loadRelations) {
		List<UserDto> users = new ArrayList<>();
		for (User user : entities) {
			users.add(entityToDto(user, loadRelations));
		}
		return users;
	}

	public static User dtoToEntity(UserDto dto) {
		if (dto == null) {
			return null;
		}
		User result = new User();
		result.setId(dto.getId());
		result.setFirstname(dto.getFirstname());
		result.setLastname(dto.getLastname());
		result.setUsername(dto.getEmail()); // if Username is empty? 
		result.setPassword(dto.getPassword());
		result.setEmail(dto.getEmail());
		result.setPhone(dto.getPhone());
		result.setStatus(dto.getStatus());
		result.setLanguage(dto.getLanguage());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}

	public static List<User> dtoToEntity(List<UserDto> dtos) {
		List<User> users = new ArrayList<>();
		for (UserDto user : dtos) {
			users.add(dtoToEntity(user));
		}
		return users;
	}
}
