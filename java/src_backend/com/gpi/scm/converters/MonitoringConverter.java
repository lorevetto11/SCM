package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.Monitoring;
import com.gpi.scm.generic.dtos.MonitoringDto;

public class MonitoringConverter extends GenericConverter {

	public static MonitoringDto entityToDto(Monitoring entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		MonitoringDto result = new MonitoringDto();
		result.setId(entity.getId());
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(), false));
			result.setProcedure(ProcedureConverter.entityToDto(entity.getProcedure(), false));
			result.setFrequency(FrequencyConverter.entityToDto(entity.getFrequency(), false));
			result.setContext(ContextConverter.entityToDto(entity.getContext(), false));
			result.setOutcomes(OutcomeConverter.entityToDto(entity.getOutcomes(), false));
		}
		return result;
	}

	public static Monitoring dtoToEntity(MonitoringDto dto) {
		if (dto == null) {
			return null;
		}
		Monitoring result = new Monitoring();
		result.setId(dto.getId());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<MonitoringDto> entityToDto(List<Monitoring> entities, boolean loadRelations) {
		List<MonitoringDto> monitorings = new ArrayList<>();
		for (Monitoring monitoring : entities) {
			monitorings.add(entityToDto(monitoring, loadRelations));
		}
		return monitorings;
	}

}
