package com.gpi.scm.ejb.entities;

import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

@Entity
@Table(name = "ORG_ORGANIZATIONS")
@NamedQueries({@NamedQuery(name = Organization.NQ_ORGANIZATIONS, query = "Select o from Organization o where o.id IN :organizations"),
				@NamedQuery(name = Organization.NQ_ORG_BY_ID, query = "Select o from Organization o where o.id = :idOrg")

})
public class Organization extends GenericEntity {

	private static final long serialVersionUID = 1L;
	public static final String NQ_ORGANIZATIONS = "organizations.userOrganizations";
	public static final String NQ_ORG_BY_ID = "organizations.organizationId";

	private String name;
	private String description;
	private String vatNumber;
	private String legalResidence;
	private String email;
	private String phone;
	private String status;
	private Organization organization;
	private Context context;
	private List<User> users = new ArrayList<>();
	private List<UserRole> roles =new ArrayList <> ();


	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_org_organizations")
	@SequenceGenerator(name = "seq_org_organizations", sequenceName = "seq_org_organizations", allocationSize = 1)
	public Long getId() {
		return id;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "VAT_NUMBER")
	public String getVatNumber() {
		return vatNumber;
	}

	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}

	@Column(name = "LEGAL_RESIDENCE")
	public String getLegalResidence() {
		return legalResidence;
	}

	public void setLegalResidence(String legalResidence) {
		this.legalResidence = legalResidence;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REF_ORGANIZATION")
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
	@BatchSize(size=25)
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	@OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
	@BatchSize(size=25)
	public List<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}

	@OneToOne
	@JoinColumn(name="REF_CONTEXT")
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
}
