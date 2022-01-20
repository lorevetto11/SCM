package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.AttachmentMetadata;
import com.gpi.scm.generic.dtos.AttachmentDto;

public class AttachmentMetaConverter extends GenericConverter {
	public static AttachmentDto entityToDto(AttachmentMetadata entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		AttachmentDto result = new AttachmentDto();
		result.setFilename(entity.getFilename());
		result.setId(entity.getId());
		result.setData(null);
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

	public static AttachmentMetadata dtoToEntity(AttachmentDto dto) {
		if (dto == null) {
			return null;
		}
		AttachmentMetadata result = new AttachmentMetadata();
		result.setFilename(dto.getFilename());
		result.setId(dto.getId());
		result.setFileSize(dto.getFileSize());
		result.setMimeType(dto.getMimeType());
		result.setType(dto.getType());
		result.setDescription(dto.getDescription());
		result.setLink(dto.getLink());
		GenericConverter.dtoToEntity(result, dto);

		return result;
	}
	public static List<AttachmentDto> entityToDto(List<AttachmentMetadata> entities, boolean loadRelations) {
		List<AttachmentDto> attachments = new ArrayList<>();
		for (AttachmentMetadata attachment : entities) {
			attachments.add(entityToDto(attachment, loadRelations));
		}
		return attachments;
	}

}
