package com.gpi.scm.generic.dtos;

import com.gpi.scm.generic.dtos.validators.AtLeast;

@AtLeast(required = { "processcheckPlanning","processCheck" ,"nonCompliance"})

public class ProcessCheckOutcomeDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String evidence;
	private NonComplianceDto nonCompliance;
	private ProcessCheckPlanningDto processCheckPlanning;
	private ContextDto context;
	private ProcessCheckDto processCheck;
	
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
	public ProcessCheckPlanningDto getProcesscheckPlanning() {
		return processCheckPlanning;
	}
	public void setProcesscheckPlanning(ProcessCheckPlanningDto systemcheckPlanning) {
		this.processCheckPlanning = systemcheckPlanning;
	}
	public ContextDto getContext() {
		return context;
	}
	public void setContext(ContextDto context) {
		this.context = context;
	}
	public ProcessCheckDto getProcessCheck() {
		return processCheck;
	}
	public void setProcessCheck(ProcessCheckDto processCheck) {
		this.processCheck = processCheck;
	}

}
