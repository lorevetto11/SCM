
package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.NonCompliance;
import com.gpi.scm.generic.dtos.NonComplianceDto;

public class NonComplianceConverter extends GenericConverter {
	public static NonComplianceDto entityToDto(NonCompliance entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		NonComplianceDto result = new NonComplianceDto();
		result.setId(entity.getId());
		result.setDescription(entity.getDescription());
		result.setCloseDate(entity.getCloseDate());
		result.setStartDate(entity.getStartDate());
		result.setTreatment(entity.getTreatment());
		result.setRetrieval(entity.getRetrieval());
		result.setCauses(entity.getCauses());
		result.setCorrections(entity.getCorrections());
		result.setChecks(entity.getChecks());
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(), false));
			result.setProcessCheck(ProcessCheckConverter.entityToDto(entity.getProcessCheck(), false));
			result.setContext(ContextConverter.entityToDto(entity.getContext(), false));
			result.setSystemCheckRequirement(SystemCheckRequirementConverter.entityToDto(entity.getSystemCheckRequirement(), false));
			result.setCloseUser(UserConverter.entityToDto(entity.getCloseUser(), false));

		}
		return result;
	}

	public static NonCompliance dtoToEntity(NonComplianceDto dto) {
		if (dto == null) {
			return null;
		}
		NonCompliance result = new NonCompliance();
		result.setId(dto.getId());
		result.setDescription(dto.getDescription());
		result.setCloseDate(dto.getCloseDate());
		result.setStartDate(dto.getStartDate());
		result.setTreatment(dto.getTreatment());
		result.setRetrieval(dto.getRetrieval());
		result.setCauses(dto.getCauses());
		result.setCorrections(dto.getCorrections());
		result.setChecks(dto.getChecks());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}

	public static List<NonComplianceDto> entityToDto(List<NonCompliance> entities, boolean loadRelations) {
		List<NonComplianceDto> compliances = new ArrayList<>();
		for (NonCompliance compliance : entities) {
			compliances.add(entityToDto(compliance, loadRelations));
		}
		return compliances;
	}

}
