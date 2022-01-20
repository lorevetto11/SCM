package com.gpi.scm.generic.dtos;


public class StaffHygieneDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PrerequisiteTypeDto prerequisiteType;
	private ContextDto context;
	private OrganizationDto organization;
	private UserRoleDto role;
	public PrerequisiteTypeDto getPrerequisiteType() {
		return prerequisiteType;
	}
	public void setPrerequisiteType(PrerequisiteTypeDto prerequisiteType) {
		this.prerequisiteType = prerequisiteType;
	}
	public ContextDto getContext() {
		return context;
	}
	public void setContext(ContextDto context) {
		this.context = context;
	}
	public OrganizationDto getOrganization() {
		return organization;
	}
	public void setOrganization(OrganizationDto organization) {
		this.organization = organization;
	}
	public UserRoleDto getRole() {
		return role;
	}
	public void setRole(UserRoleDto role) {
		this.role = role;
	}

}
