package com.gpi.scm.generic.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

public class TrainingDto extends GenericDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	OrganizationDto organization;
	UserRoleDto role;
	CourseDto course;
	String name;
	Date date;
	boolean archived;
	List<ParticipantDto> participants = new ArrayList<>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}	
	public boolean isArchived() {
		return archived;
	}
	public void setArchived(boolean archived) {
		this.archived = archived;
	}
	@NotNull
	public UserRoleDto getUserRole() {
		return role;
	}
	public void setUserRole(UserRoleDto role) {
		this.role = role;
	}

	public OrganizationDto getOrganization() {
		return organization;
	}
	public void setOrganization(OrganizationDto organization) {
		this.organization = organization;
	}
	@NotNull
	public CourseDto getCourse() {
		return course;
	}
	public void setCourse(CourseDto course) {
		this.course = course;
	}

	public List<ParticipantDto> getParticipants() {
		return participants;
	}
	public void setParticipants(List<ParticipantDto> participants) {
		this.participants = participants;
	}

}
