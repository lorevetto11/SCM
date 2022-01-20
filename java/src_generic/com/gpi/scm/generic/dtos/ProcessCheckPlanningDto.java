package com.gpi.scm.generic.dtos;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;


public class ProcessCheckPlanningDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String status;
	private Date date;
	private Date startDate;
	private Date closeDate;
	private OrganizationDto organization;
	private OrganizationDto company;
	private List<ProcessCheckDto> processchecks;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@NotNull
	public OrganizationDto getOrganization() {
		return organization;
	}
	public void setOrganization(OrganizationDto organization) {
		this.organization = organization;
	}
	@NotNull
	public OrganizationDto getCompany() {
		return company;
	}
	public void setCompany(OrganizationDto company) {
		this.company = company;
	}
	public List<ProcessCheckDto> getProcesschecks() {
		return processchecks;
	}
	public void setProcesschecks(List<ProcessCheckDto> processchecks) {
		this.processchecks = processchecks;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

}
