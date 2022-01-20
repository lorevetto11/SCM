package com.gpi.scm.generic.dtos;

import javax.validation.constraints.NotNull;

public class OrganizationPlaceDto extends GenericDto {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private String address;
	private String description;
	private OrganizationDto organization;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@NotNull
	public OrganizationDto getOrganization() {
		return organization;
	}
	public void setOrganization(OrganizationDto organization) {
		this.organization = organization;
	}


}
