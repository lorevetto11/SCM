package com.gpi.scm.generic.dtos;

import javax.validation.constraints.NotNull;

public class ProcedureDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;       
	private String description;            
	private String purpose;          
	private  String equipments;
	private String activities;       
	private String process_check ;         
	private String results_check; 
	private Long revision;        
	
	@NotNull
	private boolean privacy;      
	private RiskClassDto riskClass;          
	@NotNull
	private OrganizationDto organization;
	@NotNull
	private UserRoleDto userRole;
	@NotNull
	private PrerequisiteTypeDto prerequisiteType;     

    public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getEquipments() {
		return equipments;
	}
	public void setEquipments(String equipments) {
		this.equipments = equipments;
	}
	public String getActivities() {
		return activities;
	}
	public void setActivities(String activities) {
		this.activities = activities;
	}
	public String getProcess_check() {
		return process_check;
	}
	public void setProcess_check(String process_check) {
		this.process_check = process_check;
	}
	public String getResults_check() {
		return results_check;
	}
	public void setResults_check(String results_check) {
		this.results_check = results_check;
	}
	public Long getRevision() {
		return revision;
	}
	public void setRevision(Long revision) {
		this.revision = revision;
	}
	public boolean getPrivacy() {
		return privacy;
	}
	public void setPrivacy(boolean privacy) {
		this.privacy = privacy;
	}
	public RiskClassDto getRiskClass() {
		return riskClass;
	}
	public void setRiskClass(RiskClassDto rClass) {
		this.riskClass = rClass;
	}
	public OrganizationDto getOrganization() {
		return organization;
	}
	public void setOrganization(OrganizationDto organization) {
		this.organization = organization;
	}
	public UserRoleDto getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRoleDto uRole) {
		this.userRole = uRole;
	}
	public PrerequisiteTypeDto getPrerequisiteType() {
		return prerequisiteType;
	}
	public void setPrerequisiteType(PrerequisiteTypeDto pType) {
		this.prerequisiteType = pType;
	}

}
