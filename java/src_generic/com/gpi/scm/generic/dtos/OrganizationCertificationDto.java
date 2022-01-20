package com.gpi.scm.generic.dtos;

import java.util.Date;

import javax.validation.constraints.NotNull;



public class OrganizationCertificationDto extends GenericDto {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private String authority ;
	private Date date;
	private Date expiryDate;
	private ContextDto context;
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
	@NotNull
	public OrganizationDto getOrganization() {
		return organization;
	}
	public void setOrganization(OrganizationDto organization) {
		this.organization = organization;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public ContextDto getContext() {
		return context;
	}
	public void setContext(ContextDto context) {
		this.context = context;
	}


}
