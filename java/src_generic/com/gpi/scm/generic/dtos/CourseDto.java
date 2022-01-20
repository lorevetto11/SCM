package com.gpi.scm.generic.dtos;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

public class CourseDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	String description;
	String trainer;
	OrganizationDto organization;
	ContextDto context;
	List<TrainingDto> trainings= new ArrayList<>();
	
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
	public String getTrainer() {
		return trainer;
	}
	public void setTrainer(String trainer) {
		this.trainer = trainer;
	}
	@NotNull
	public OrganizationDto getOrganization() {
		return organization;
	}
	public void setOrganization(OrganizationDto organization) {
		this.organization = organization;
	}
	public ContextDto getContext() {
		return context;
	}
	public void setContext(ContextDto context) {
		this.context = context;
	}

}
