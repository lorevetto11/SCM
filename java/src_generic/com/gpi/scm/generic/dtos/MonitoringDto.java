package com.gpi.scm.generic.dtos;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;



public class MonitoringDto extends GenericDto {
	
	private static final long serialVersionUID = 1L;
	OrganizationDto organization;
	ContextDto context;
	FrequencyDto frequency;
	ProcedureDto procedure;
	List<OutcomeDto> outcomes = new ArrayList<>();

	
	public List<OutcomeDto> getOutcomes() {
		if(!outcomes.isEmpty()){
			return outcomes.subList(0, 1);
		}
		else
		{
			return null;
		}
	}
	public void setOutcomes(List<OutcomeDto> outcomes) {
		this.outcomes = outcomes;
	}
	@NotNull
	public OrganizationDto getOrganization() {
		return organization;
	}
	public void setOrganization(OrganizationDto organization) {
		this.organization = organization;
	}
	@NotNull
	public ContextDto getContext() {
		return context;
	}
	public void setContext(ContextDto context) {
		this.context = context;
	}
	@NotNull
	public FrequencyDto getFrequency() {
		return frequency;
	}
	public void setFrequency(FrequencyDto frequency) {
		this.frequency = frequency;
	}
	@NotNull
	public ProcedureDto getProcedure() {
		return procedure;
	}
	public void setProcedure(ProcedureDto procedure) {
		this.procedure = procedure;
	}
	
}
