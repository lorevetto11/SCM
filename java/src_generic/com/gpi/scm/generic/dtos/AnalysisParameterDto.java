package com.gpi.scm.generic.dtos;

import javax.validation.constraints.NotNull;

public class AnalysisParameterDto extends GenericDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private Float thresholdValue;
	private UserRoleDto userRole;
	private OrganizationDto organization;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Float getThresholdValue() {
		return thresholdValue;
	}
	public void setThresholdValue(Float thresholdValue) {
		this.thresholdValue = thresholdValue;
	}
	public UserRoleDto getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRoleDto userRole) {
		this.userRole = userRole;
	}
	@NotNull
	public OrganizationDto getOrganization() {
		return organization;
	}
	public void setOrganization(OrganizationDto organization) {
		this.organization = organization;
	}
}
