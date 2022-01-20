package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.RiskClass;
import com.gpi.scm.generic.dtos.RiskClassDto;

public class RiskClassConverter extends GenericConverter {
	public static RiskClassDto entityToDto(RiskClass entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		RiskClassDto result = new RiskClassDto();
		result.setId(entity.getId());
		result.setName(entity.getName());
		result.setColor(entity.getColor());
		result.setOrder(entity.getOrder());
		result.setDescription(entity.getDescription());
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(),false));

		}
		return result;
	}

	public static RiskClass dtoToEntity(RiskClassDto dto) {
		if (dto == null) {
			return null;
		}
		RiskClass result = new RiskClass();
		result.setId(dto.getId());
		result.setName(dto.getName());
		result.setColor(dto.getColor());
		result.setOrder(dto.getOrder());
		result.setDescription(dto.getDescription());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<RiskClassDto> entityToDto(List<RiskClass> entities, boolean loadRelations) {
		List<RiskClassDto> riskcs = new ArrayList<>();
		for (RiskClass riskc : entities) {
			riskcs.add(entityToDto(riskc, loadRelations));
		}
		return riskcs;
	}

}
