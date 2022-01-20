package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.WasteDisposal;
import com.gpi.scm.generic.dtos.BasePrerequisiteDto;



public class WasteDisposalConverter extends GenericConverter {
	public static BasePrerequisiteDto entityToDto(WasteDisposal entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		BasePrerequisiteDto result = new BasePrerequisiteDto();
		result.setId(entity.getId());
		result.setName(entity.getName());
		result.setDescription(entity.getDescription());
		GenericConverter.entityToDto(result, entity);
		result.setPrerequisiteType(PrerequisiteTypeConverter.entityToDto(entity.getPrerequisiteType(), false));

		if (loadRelations) {
			result.setShape(ShapeConverter.entityToDto(entity.getShape(), false));
			result.setLayout(LayoutConverter.entityToDto(entity.getLayout(), false));
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(), false));
			result.setFloor(FloorConverter.entityToDto(entity.getFloor(), false));
			result.setContext(ContextConverter.entityToDto(entity.getContext(), true));



		}
		return result;
	}

	public static WasteDisposal dtoToEntity(BasePrerequisiteDto dto) {
		if (dto == null) {
			return null;
		}
		WasteDisposal result = new WasteDisposal();
		result.setId(dto.getId());
		result.setName(dto.getName());
		result.setDescription(dto.getDescription());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<BasePrerequisiteDto> entityToDto(List<WasteDisposal> entities, boolean loadRelations) {
		List<BasePrerequisiteDto> wastedisposals = new ArrayList<>();
		for (WasteDisposal wastedisposal : entities) {
			wastedisposals.add(entityToDto(wastedisposal, loadRelations));
		}
		return wastedisposals;
	}

}
