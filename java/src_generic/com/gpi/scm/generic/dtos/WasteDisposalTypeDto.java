package com.gpi.scm.generic.dtos;


import javax.validation.constraints.NotNull;


public class WasteDisposalTypeDto extends GenericDto {
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private OrganizationDto organization;
	private ShapeDto shape;
	

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
	@NotNull
	public OrganizationDto getOrganization() {
		return organization;
	}
	public void setOrganization(OrganizationDto organization) {
		this.organization = organization;
	}
	@NotNull
	public ShapeDto getShape() {
		return shape;
	}
	public void setShape(ShapeDto shape) {
		this.shape = shape;
	}

}
