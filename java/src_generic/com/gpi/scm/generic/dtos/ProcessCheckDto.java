package com.gpi.scm.generic.dtos;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

public class ProcessCheckDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private String type;
	private boolean privacy;
	private OrganizationDto organization;
	private FrequencyDto frequency;
	private PrerequisiteTypeDto prerequisiteType;
	private List <ProcessCheckOutcomeDto> processCheckOutcomes= new ArrayList<>();
	
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
	public boolean isPrivacy() {
		return privacy;
	}
	public void setPrivacy(boolean privacy) {
		this.privacy = privacy;
	}
	@NotNull
	public OrganizationDto getOrganization() {
		return organization;
	}
	public void setOrganization(OrganizationDto organization) {
		this.organization = organization;
	}
	@NotNull
	public FrequencyDto getFrequency() {
		return frequency;
	}
	public void setFrequency(FrequencyDto frequency) {
		this.frequency = frequency;
	}
	@NotNull
	public PrerequisiteTypeDto getPrerequisiteType() {
		return prerequisiteType;
	}
	public void setPrerequisiteType(PrerequisiteTypeDto prerequisiteType) {
		this.prerequisiteType = prerequisiteType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List <ProcessCheckOutcomeDto> getProcessCheckOutcomes() {
		return processCheckOutcomes;
	}
	public void setProcessCheckOutcomes(List <ProcessCheckOutcomeDto> processCheckOutcomes) {
		this.processCheckOutcomes = processCheckOutcomes;
	}

}
