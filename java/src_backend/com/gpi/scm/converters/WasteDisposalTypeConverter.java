package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.WasteDisposalType;
import com.gpi.scm.generic.dtos.WasteDisposalTypeDto;



public class WasteDisposalTypeConverter extends GenericConverter {
	public static WasteDisposalTypeDto entityToDto(WasteDisposalType entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		WasteDisposalTypeDto result = new WasteDisposalTypeDto();
		result.setId(entity.getId());
		result.setName(entity.getName());
		result.setDescription(entity.getDescription());
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(), false));
			result.setShape(ShapeConverter.entityToDto(entity.getShape(), false));

		}
		return result;
	}

	public static WasteDisposalType dtoToEntity(WasteDisposalTypeDto dto) {
		if (dto == null) {
			return null;
		}
		WasteDisposalType result = new WasteDisposalType();
		result.setId(dto.getId());
		result.setName(dto.getName());
		result.setDescription(dto.getDescription());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<WasteDisposalTypeDto> entityToDto(List<WasteDisposalType> entities, boolean loadRelations) {
		List<WasteDisposalTypeDto> wastedisposals = new ArrayList<>();
		for (WasteDisposalType wastedisposal : entities) {
			wastedisposals.add(entityToDto(wastedisposal, loadRelations));
		}
		return wastedisposals;
	}

}
