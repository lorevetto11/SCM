package com.gpi.scm.generic.dtos;

import javax.validation.constraints.NotNull;

public class OutcomeDto extends GenericDto {

	private static final long serialVersionUID = 1L;
	@NotNull
	private boolean result;
	private String note;
	@NotNull
	private MonitoringDto monitoring;
	
	private ContextDto context;
	@NotNull
	private UserDto user;
	
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
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
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public MonitoringDto getMonitoring() {
		return monitoring;
	}
	public void setMonitoring(MonitoringDto monitoring) {
		this.monitoring = monitoring;
	}

	
}
