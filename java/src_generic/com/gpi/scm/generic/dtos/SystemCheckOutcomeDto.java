package com.gpi.scm.generic.dtos;

import com.gpi.scm.generic.dtos.validators.AtLeast;

@AtLeast(required = { "systemcheckRequirement","systemcheckPlanning" ,"nonCompliance"})

public class SystemCheckOutcomeDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String evidence;
	private NonComplianceDto nonCompliance;
	private SystemCheckRequirementDto systemcheckRequirement;
	private SystemCheckPlanningDto systemcheckPlanning;
	private ContextDto context;
	
	public String getEvidence() {
		return evidence;
	}
	public void setEvidence(String evidence) {
		this.evidence = evidence;
	}
	public NonComplianceDto getNonCompliance() {
		return nonCompliance;
	}
	public void setNonCompliance(NonComplianceDto nonCompliance) {
		this.nonCompliance = nonCompliance;
	}
	public SystemCheckRequirementDto getSystemcheckRequirement() {
		return systemcheckRequirement;
	}
	public void setSystemcheckRequirement(SystemCheckRequirementDto systemcheckRequirements) {
		this.systemcheckRequirement = systemcheckRequirements;
	}
	public SystemCheckPlanningDto getSystemcheckPlanning() {
		return systemcheckPlanning;
	}
	public void setSystemcheckPlanning(SystemCheckPlanningDto systemcheckPlanning) {
		this.systemcheckPlanning = systemcheckPlanning;
	}
	public ContextDto getContext() {
		return context;
	}
	public void setContext(ContextDto context) {
		this.context = context;
	}

}
