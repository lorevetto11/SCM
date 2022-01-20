package com.gpi.scm.converters;

import com.gpi.scm.ejb.entities.Parameter;
import com.gpi.scm.generic.dtos.ParameterDto;

public class ParameterConverter {

	public static ParameterDto entityToDto(Parameter entity, boolean loadRelations) {
		if (entity == null)
			return null;
		ParameterDto result = new ParameterDto();
		try {
			result.setId(entity.getId());
			result.setKey(entity.getKey());
			result.setValue(entity.getValue());
			result.setDeleted(entity.getDeleted());

			if (loadRelations)
				result.setRefOrganization(OrganizationConverter.entityToDto(entity.getRefOrganization(), false));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Parameter dtoToEntity(ParameterDto dto, boolean loadRelations) {
		if (dto == null)
			return null;
		Parameter result = new Parameter();
		try {
			result.setId(dto.getId());
			result.setKey(dto.getKey());
			result.setValue(dto.getValue());
			result.setDeleted(dto.getDeleted());

			result.setRefOrganization(OrganizationConverter.dtoToEntity(dto.getRefOrganization()));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
