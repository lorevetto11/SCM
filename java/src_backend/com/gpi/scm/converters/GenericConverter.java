package com.gpi.scm.converters;

import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.generic.dtos.GenericDto;

public class GenericConverter {

	public static void entityToDto(GenericDto dto, GenericEntity entity) {
		dto.setVersion(entity.getVersion());
		dto.setDeleted(entity.getDeleted());
		dto.setInsertTime(entity.getInsertTime());
		dto.setInsertUser(entity.getInsertUser());
		dto.setUpdateTime(entity.getUpdateTime());
		dto.setUpdateUser(entity.getUpdateUser());
		dto.setDeleteTime(entity.getDeleteTime());
		dto.setDeleteUser(entity.getDeleteUser());
	}

	public static void dtoToEntity(GenericEntity entity, GenericDto dto) {
		/*entity.setVersion(dto.getVersion());
		entity.setDeleted(dto.getDeleted());
		entity.setInsertTime(dto.getInsertTime());
		entity.setInsertUser(dto.getInsertUser());
		entity.setUpdateTime(dto.getUpdateTime());
		entity.setUpdateUser(dto.getUpdateUser());
		entity.setDeleteTime(dto.getDeleteTime());
		entity.setDeleteUser(dto.getDeleteUser());*/
	}
}
