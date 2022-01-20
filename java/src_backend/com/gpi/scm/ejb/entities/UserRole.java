package com.gpi.scm.ejb.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

@Entity
@Table(name = "ROL_ROLES")
@NamedQueries({@NamedQuery(name = UserRole.NQ_ROLES_IN_ORGANIZATIONS, query = "Select r from UserRole r where r.organization.id IN :organizations"),
	@NamedQuery(name = UserRole.NQ_ROLE_BY_ID, query = "Select r from UserRole r where r.id = :idRole"),
			@NamedQuery(name = UserRole.NQ_ROLES_BY_IDS, query = "Select r from UserRole r where r in :Role")

})
public class UserRole extends GenericEntity {

	private static final long serialVersionUID = 1L;
	public static final String NQ_ROLES_IN_ORGANIZATIONS = "userRole.rolesInOrganizations";
	public static final String NQ_ROLE_BY_ID = "userRole.getRoleById";
	public static final String NQ_ROLES_BY_IDS= "userRole.getRolesByIds";

	private String name;
	private String description;
	private Organization organization;
	private List<UserProfile> profiles = new ArrayList<>();



	
	@ManyToMany(cascade = CascadeType.ALL,targetEntity=UserProfile.class)
	@JoinTable(name="rol_roles_rol_profiles",
	joinColumns={@JoinColumn(name="ref_rol_roles", referencedColumnName="id")},
	inverseJoinColumns={@JoinColumn(name="ref_rol_profiles", referencedColumnName="id")}) 
	@BatchSize(size=25)
	public List<UserProfile> getProfiles()  
	{  
		return profiles;  
	}  
	public void setProfiles(List<UserProfile> profile)  
	{  
		this.profiles = profile;  
	} 
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_rol_roles")
	@SequenceGenerator(name = "seq_rol_roles", sequenceName = "seq_rol_roles", allocationSize = 1)
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

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REF_ORGANIZATION")
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	

 
    
}
