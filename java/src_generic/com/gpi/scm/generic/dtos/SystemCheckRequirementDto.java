package com.gpi.scm.generic.dtos;

import javax.validation.constraints.NotNull;

public class SystemCheckRequirementDto extends GenericDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private SystemCheckDto systemcheck;

	
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
	public SystemCheckDto getSystemCheck() {
		return systemcheck;
	}
	public void setSystemCheck(SystemCheckDto systemcheck) {
		this.systemcheck = systemcheck;
	}


}
