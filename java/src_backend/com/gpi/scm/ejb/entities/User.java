package com.gpi.scm.ejb.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "ROL_USERS")
@NamedQueries({ @NamedQuery(name = User.NQ_USER_BY_ID, query = "Select u from User u where u.id = :idUser "),
		@NamedQuery(name = User.NQ_LOGIN, query = "Select u from User u where u.username = :username and u.password = :password"),
		@NamedQuery(name = User.NQ_USERS_IN_ORGANIZATIONS, query = "Select u from User u where u.organization.id IN :organizations")
		})


public class User extends GenericEntity {

	private static final long serialVersionUID = 1L;

	public static final String NQ_USER_BY_ID = "user.getUserById";
	public static final String NQ_LOGIN = "user.login";
	public static final String NQ_USERS_IN_ORGANIZATIONS = "user.usersInOrganizations";


	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private String email;
	private String phone;
	private String status;
	private String language;
	private Organization organization;
	private UserRole role;

	public User() {
		super();
	}

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_rol_users")
	@SequenceGenerator(name = "seq_rol_users", sequenceName = "seq_rol_users", allocationSize = 1)
	public Long getId() {
		return id;
	}

	@Column(name = "FIRSTNAME")
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Column(name = "LASTNAME")
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Column(name = "USERNAME")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "PASSWORD")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "PHONE")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "LANGUAGE")
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "REF_ORGANIZATION")
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REF_ROLE")
	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}
}
