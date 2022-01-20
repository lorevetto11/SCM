package com.gpi.scm.generic.dtos;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.gpi.scm.generic.dtos.validators.AtLeast;

@AtLeast(required = { "systemCheckRequirement","processCheck" ,"context"})

public class NonComplianceDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date closeDate;
	private Date startDate;
	private UserDto closeUser;
	private String description;
	private String treatment;
	private String retrieval;
	private String causes;
	private String corrections;
	private String checks;
	private OrganizationDto organization;
	private ProcessCheckDto processCheck;
	private ContextDto context;
	private SystemCheckRequirementDto systemCheckRequirement;
	
	public Date getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTreatment() {
		return treatment;
	}
	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}
	public String getRetrieval() {
		return retrieval;
	}
	public void setRetrieval(String retrieval) {
		this.retrieval = retrieval;
	}
	public String getCauses() {
		return causes;
	}
	public void setCauses(String causes) {
		this.causes = causes;
	}
	public String getCorrections() {
		return corrections;
	}
	public void setCorrections(String corrections) {
		this.corrections = corrections;
	}
	public String getChecks() {
		return checks;
	}
	public void setChecks(String checks) {
		this.checks = checks;
	}
	@NotNull
	public OrganizationDto getOrganization() {
		return organization;
	}
	public void setOrganization(OrganizationDto organization) {
		this.organization = organization;
	}
	
	public ProcessCheckDto getProcessCheck() {
		return processCheck;
	}
	public void setProcessCheck(ProcessCheckDto processcheck) {
		this.processCheck = processcheck;
	}
	public ContextDto getContext() {
		return context;
	}
	public void setContext(ContextDto context) {
		this.context = context;
	}
	public SystemCheckRequirementDto getSystemCheckRequirement() {
		return systemCheckRequirement;
	}
	public void setSystemCheckRequirement(SystemCheckRequirementDto systemCheckRequirement) {
		this.systemCheckRequirement = systemCheckRequirement;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public UserDto getCloseUser() {
		return closeUser;
	}
	public void setCloseUser(UserDto closeUser) {
		this.closeUser = closeUser;
	}
	

}
