package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.Frequency;
import com.gpi.scm.generic.dtos.FrequencyDto;

public class FrequencyConverter extends GenericConverter {
	public static FrequencyDto entityToDto(Frequency entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		FrequencyDto result = new FrequencyDto();
		result.setId(entity.getId());
		result.setPeriod(entity.getPeriod());
		result.setValue(entity.getValue());
		result.setAsNeeded(entity.isAsNeeded());
		result.setJustOnce(entity.isJustOnce());
		result.setType(entity.getType());
		result.setPrerequisiteType(PrerequisiteTypeConverter.entityToDto(entity.getPrerequisiteType(), false));

		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(), false));
			result.setRiskClass(RiskClassConverter.entityToDto(entity.getRiskClass(), false));

		}
		return result;
	}
	public static Frequency dtoToEntity(FrequencyDto dto) {
		if (dto == null) {
			return null;
		}
		Frequency result = new Frequency();
		result.setId(dto.getId());
		result.setPeriod(dto.getPeriod());
		result.setValue(dto.getValue());
		result.setAsNeeded(dto.isAsNeeded());
		result.setJustOnce(dto.isJustOnce());
		result.setType(dto.getType());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<FrequencyDto> entityToDto(List<Frequency> entities, boolean loadRelations) {
		List<FrequencyDto> freqs = new ArrayList<>();
		for (Frequency freq : entities) {
			freqs.add(entityToDto(freq, loadRelations));
		}
		return freqs;
	}
}
