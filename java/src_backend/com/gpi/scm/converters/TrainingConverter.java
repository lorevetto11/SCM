package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.Training;
import com.gpi.scm.generic.dtos.TrainingDto;

public class TrainingConverter extends GenericConverter {
	public static TrainingDto entityToDto(Training entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		TrainingDto result = new TrainingDto();
		result.setId(entity.getId());
		result.setName(entity.getName());
		result.setDate(entity.getDate());
		result.setArchived(entity.isArchived());
	
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			
			result.setCourse(CourseConverter.entityToDto(entity.getCourse(), false));
			result.setUserRole(UserRoleConverter.entityToDto(entity.getUserRole(), false));
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(), false));
			result.setParticipants(ParticipantConverter.entityToDto(entity.getParticipants(), false));
		}
		
		return result;
	}

	public static Training dtoToEntity(TrainingDto dto) {
		if (dto == null) {
			return null;
		}
		Training result = new Training();
		result.setId(dto.getId());
		result.setName(dto.getName());
		result.setDate(dto.getDate());
		result.setArchived(dto.isArchived());
		
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<TrainingDto> entityToDto(List<Training> entities, boolean loadRelations) {
		List<TrainingDto> trainings = new ArrayList<>();
		for (Training training : entities) {
			trainings.add(entityToDto(training, loadRelations));
		}
		return trainings;
	}
	public static List<Training> dtoToEntity(List<TrainingDto> entities) {
		List<Training> trainings = new ArrayList<>();
		for (TrainingDto training : entities) {
			trainings.add(dtoToEntity(training));
		}
		return trainings;
	

	}

}
