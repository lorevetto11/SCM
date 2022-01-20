package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.RiskMap;
import com.gpi.scm.generic.dtos.RiskMapDto;

public class RiskMapConverter extends GenericConverter {
	public static RiskMapDto entityToDto(RiskMap entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		RiskMapDto result = new RiskMapDto();
		result.setId(entity.getId());
		result.setValue(entity.getValue());
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(),false));
			result.setDanger(DangerConverter.entityToDto(entity.getDanger(), false));
			result.setElement(FlowElementConverter.entityToDto(entity.getElement(), false));

		}
		return result;
	}

	public static RiskMap dtoToEntity(RiskMapDto dto) {
		if (dto == null) {
			return null;
		}
		RiskMap result = new RiskMap();
		result.setId(dto.getId());
		result.setValue(dto.getValue());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<RiskMapDto> entityToDto(List<RiskMap> entities, boolean loadRelations) {
		List<RiskMapDto> riskcs = new ArrayList<>();
		for (RiskMap riskc : entities) {
			riskcs.add(entityToDto(riskc, loadRelations));
		}
		return riskcs;
	}

}
