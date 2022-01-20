package com.gpi.scm.generic.dtos;


import java.util.Date;

import javax.validation.constraints.NotNull;



public class EquipmentDto extends GenericDto {
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	//private String supplier;
	private String maintainer;
	private Date startupDate;
	private ContextDto context;
	private FrequencyDto frequency;
	private EquipmentTypeDto equipmentType;
	private OrganizationDto organization;
	private SupplierDto supplier;

	

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
	public EquipmentTypeDto getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(EquipmentTypeDto equipmentType) {
		this.equipmentType = equipmentType;
	}
	public SupplierDto getSupplier() {
		return supplier;
	}
	public void setSupplier(SupplierDto supplier) {
		this.supplier = supplier;
	}
	public String getMaintainer() {
		return maintainer;
	}
	public void setMaintainer(String maintainer) {
		this.maintainer = maintainer;
	}
	public ContextDto getContext() {
		return context;
	}
	public void setContext(ContextDto context) {
		this.context = context;
	}
	@NotNull
	public FrequencyDto getFrequency() {
		return frequency;
	}
	public void setFrequency(FrequencyDto frequency) {
		this.frequency = frequency;
	}
	public Date getStartupDate() {
		return startupDate;
	}
	public void setStartupDate(Date startupDate) {
		this.startupDate = startupDate;
	}
	@NotNull
	public OrganizationDto getOrganization() {
		return organization;
	}
	public void setOrganization(OrganizationDto organization) {
		this.organization = organization;
	}

}
