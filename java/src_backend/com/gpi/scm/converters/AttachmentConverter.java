package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.Attachment;
import com.gpi.scm.generic.dtos.AttachmentDto;

public class AttachmentConverter extends GenericConverter {
	public static AttachmentDto entityToDto(Attachment entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		AttachmentDto result = new AttachmentDto();
		result.setFilename(entity.getFilename());
		result.setId(entity.getId());
		
		result.setData(entity.getData());
		result.setFileSize(entity.getFileSize());
		result.setMimeType(entity.getMimeType());
		result.setType(entity.getType());
		result.setDescription(entity.getDescription());
		result.setLink(entity.getLink());
		
		GenericConverter.entityToDto(result, entity);
		if (loadRelations)
		{
			result.setContext(ContextConverter.entityToDto(entity.getContext(),true));
		}

		return result;
	}

	public static Attachment dtoToEntity(AttachmentDto dto) {
		if (dto == null) {
			return null;
		}
		Attachment result = new Attachment();
		result.setFilename(dto.getFilename());
		result.setId(dto.getId());
		result.setData(dto.getData());
		result.setFileSize(dto.getFileSize());
		result.setMimeType(dto.getMimeType());
		result.setType(dto.getType());
		result.setDescription(dto.getDescription());
		result.setLink(dto.getLink());
		GenericConverter.dtoToEntity(result, dto);

		return result;
	}
	public static List<AttachmentDto> entityToDto(List<Attachment> entities, boolean loadRelations) {
		List<AttachmentDto> attachments = new ArrayList<>();
		for (Attachment attachment : entities) {
			attachments.add(entityToDto(attachment, loadRelations));
		}
		return attachments;
	}

}
