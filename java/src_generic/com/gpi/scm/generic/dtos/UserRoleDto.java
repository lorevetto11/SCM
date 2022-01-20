package com.gpi.scm.generic.dtos;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;


public class UserRoleDto extends GenericDto {

	private static final long serialVersionUID = 4440639228367190906L;

	private String name;
	private String description;
	private OrganizationDto organization;
	private List<UserProfileDto> profiles = new ArrayList<>() ;


	@NotNull
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

	public OrganizationDto getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationDto organization) {
		this.organization = organization;
	}

	public List<UserProfileDto> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<UserProfileDto> profiles) {
		this.profiles = profiles;
	}
}
