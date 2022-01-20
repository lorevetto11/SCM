package com.gpi.scm.generic.dtos;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

public class DangerDto extends GenericDto {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private String type;
	private String risk;	
	private boolean isCcp;
	private String controlMeasure;
	private String criticalLimit;
	private String acceptanceLimit;
	private String procedures;
	private PrerequisiteTypeDto prerequisiteType;
	private ContextDto context ;
	private OrganizationDto organization ;
	private List<MaterialDto> materials = new ArrayList<>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isCcp() {
		return isCcp;
	}
	public void setCcp(boolean isCcp) {
		this.isCcp = isCcp;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRisk() {
		return risk;
	}
	public void setRisk(String risk) {
		this.risk = risk;
	}
	public String getControlMeasure() {
		return controlMeasure;
	}
	public void setControlMeasure(String controlMeasure) {
		this.controlMeasure = controlMeasure;
	}
	public String getCriticalLimit() {
		return criticalLimit;
	}
	public void setCriticalLimit(String criticalLimit) {
		this.criticalLimit = criticalLimit;
	}
	public String getProcedures() {
		return procedures;
	}
	public void setProcedures(String procedures) {
		this.procedures = procedures;
	}
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
	@NotNull
	public OrganizationDto getOrganization() {
		return organization;
	}
	public void setOrganization(OrganizationDto organization) {
		this.organization = organization;
	}

	public List<MaterialDto> getMaterials() {
		return materials;
	}
	public void setMaterials(List<MaterialDto> materials) {
		this.materials = materials;
	}
	public String getAcceptanceLimit() {
		return acceptanceLimit;
	}
	public void setAcceptanceLimit(String acceptanceLimit) {
		this.acceptanceLimit = acceptanceLimit;
	}
}
