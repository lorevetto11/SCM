package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.Participant;
import com.gpi.scm.generic.dtos.ParticipantDto;

public class ParticipantConverter extends GenericConverter {
	public static ParticipantDto entityToDto(Participant entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		ParticipantDto result = new ParticipantDto();
		result.setId(entity.getId());
		result.setNote(entity.getNote());
		result.setPassed(entity.isPassed());
		result.setUser(UserConverter.entityToDto(entity.getUser(), false));
		result.setContext(ContextConverter.entityToDto(entity.getContext(), false));

		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
		}
		
		return result;
	}

	public static Participant dtoToEntity(ParticipantDto dto) {
		if (dto == null) {
			return null;
		}
		Participant result = new Participant();
		result.setId(dto.getId());
		result.setPassed(dto.isPassed());
		result.setNote(dto.getNote());

		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<ParticipantDto> entityToDto(List<Participant> entities, boolean loadRelations) {
		List<ParticipantDto> participants = new ArrayList<>();
		for (Participant participant : entities) {
			participants.add(entityToDto(participant, loadRelations));
		}
		return participants;
	}
	public static List<Participant> dtoToEntity(List<ParticipantDto> entities) {
		List<Participant> participants = new ArrayList<>();
		for (ParticipantDto participant : entities) {
			participants.add(dtoToEntity(participant));
		}
		return participants;
	

	}
}
