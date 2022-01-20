package com.gpi.scm.generic.dtos;

import java.util.Date;

import javax.validation.constraints.NotNull;


public class AnalysisValueDto extends GenericDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date date;
	private double value;
	private String note;
	private ContextDto context;
	private AnalysisParameterDto analysisParameter;
	private OrganizationDto organization;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
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
	public AnalysisParameterDto getAnalysisParameter() {
		return analysisParameter;
	}
	public void setAnalysisParameter(AnalysisParameterDto analysisParameter) {
		this.analysisParameter = analysisParameter;
	}
	@NotNull
	public OrganizationDto getOrganization() {
		return organization;
	}
	public void setOrganization(OrganizationDto organization) {
		this.organization = organization;
	}
}
