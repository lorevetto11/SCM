package com.gpi.scm.generic.dtos;

import javax.validation.constraints.NotNull;

public class ParticipantDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean passed;
	String note;
	ContextDto context;
	UserDto user;
	
	public boolean isPassed() {
		return passed;
	}
	public void setPassed(boolean passed) {
		this.passed = passed;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public ContextDto getContext() {
		return context;
	}
	public void setContext(ContextDto context) {
		this.context = context;
	}
	@NotNull
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	

}
