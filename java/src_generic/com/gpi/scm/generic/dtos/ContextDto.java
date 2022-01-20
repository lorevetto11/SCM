package com.gpi.scm.generic.dtos;

import javax.validation.constraints.NotNull;

public class ContextDto extends GenericDto {

	private static final long serialVersionUID = 1L;
	private String className;
	
	@NotNull
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	

}
