package com.gpi.scm.generic.dtos;

import javax.validation.constraints.NotNull;



public class RiskMapDto extends GenericDto {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long value;
	private OrganizationDto organization;
	private DangerDto danger;
	private FlowElementDto element;
	public Long getValue() {
		return value;
	}
	public void setValue(Long value) {
		this.value = value;
	}
	@NotNull
	public OrganizationDto getOrganization() {
		return organization;
	}
	public void setOrganization(OrganizationDto organization) {
		this.organization = organization;
	}
	@NotNull
	public DangerDto getDanger() {
		return danger;
	}
	public void setDanger(DangerDto danger) {
		this.danger = danger;
	}
	@NotNull
	public FlowElementDto getElement() {
		return element;
	}
	public void setElement(FlowElementDto element) {
		this.element = element;
	}
	
	

}
