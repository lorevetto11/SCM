package com.gpi.scm.generic.dtos;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

public class OrganizationDto extends GenericDto {

	private static final long serialVersionUID = 4440639228367190906L;

	private String name;
	private String description;
	private String vatNumber;
	private String legalResidence;
	private String email;
	private String phone;
	private String status;
	private ContextDto context;
	private OrganizationDto organization;
	private List<UserDto> users = new ArrayList<>();
	private List<UserRoleDto> roles = new ArrayList<>();

	@NotNull
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
	public String getVatNumber() {
		return vatNumber;
	}

	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}
	@NotNull
	public String getLegalResidence() {
		return legalResidence;
	}

	public void setLegalResidence(String legalResidence) {
		this.legalResidence = legalResidence;
	}
	@NotNull
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@NotNull
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public OrganizationDto getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationDto organization) {
		this.organization = organization;
	}

	public List<UserDto> getUsers() {
		return users;
	}

	public void setUsers(List<UserDto> users) {
		this.users = users;
	}

	public List<UserRoleDto> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRoleDto> roles) {
		this.roles = roles;
	}

	public ContextDto getContext() {
		return context;
	}

	public void setContext(ContextDto context) {
		this.context = context;
	}
}
