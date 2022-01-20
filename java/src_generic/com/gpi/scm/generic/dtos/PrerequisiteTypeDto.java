package com.gpi.scm.generic.dtos;




public class PrerequisiteTypeDto extends GenericDto {
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	
	

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

}
