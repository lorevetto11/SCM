package com.gpi.scm.generic.dtos;

import javax.validation.constraints.NotNull;



public class RiskClassDto extends GenericDto {


	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private String color;
	private Long order;
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
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Long getOrder() {
		return order;
	}
	public void setOrder(Long order) {
		this.order = order;
	}
	@NotNull
	public OrganizationDto getOrganization() {
		return organization;
	}
	public void setOrganization(OrganizationDto organization) {
		this.organization = organization;
	}

	

}
