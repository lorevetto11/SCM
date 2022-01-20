package com.gpi.scm.generic.dtos;

public class ParameterDto extends GenericDto {

	private static final long serialVersionUID = -8836472235399996779L;
	
	private String key;
	private String value;
	private OrganizationDto refOrganization;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public OrganizationDto getRefOrganization() {
		return refOrganization;
	}
	public void setRefOrganization(OrganizationDto refOrganization) {
		this.refOrganization = refOrganization;
	}
	
	

}
