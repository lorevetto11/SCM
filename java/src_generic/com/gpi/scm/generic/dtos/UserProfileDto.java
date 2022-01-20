package com.gpi.scm.generic.dtos;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

public class UserProfileDto extends GenericDto {


	private static final long serialVersionUID = 1L;
	private String name;
	private OrganizationDto organization;
	//private List<UserRoleDto> roles= new ArrayList<>();
	private List<GrantDto> grants = new ArrayList<>();

	@NotNull
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/*public List<UserRoleDto> getRoles() {
		return roles;
	}
	public void setRoles(List<UserRoleDto> roles) {
		this.roles = roles;
	}*/
	public List<GrantDto> getGrants() {
		return grants;
	}
	public void setGrants(List<GrantDto> grants) {
		this.grants = grants;
	}
	//@NotNull
	public OrganizationDto getOrganization() {
		return organization;
	}
	public void setOrganization(OrganizationDto organizaton) {
		this.organization = organizaton;
	}

}
