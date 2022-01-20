package com.gpi.scm.generic.dtos;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;


public class SystemCheckPlanningDto extends GenericDto {

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
	private List<SystemCheckDto> systemchecks;
	
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
	public List<SystemCheckDto> getSystemchecks() {
		return systemchecks;
	}
	public void setSystemchecks(List<SystemCheckDto> systemchecks) {
		this.systemchecks = systemchecks;
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
