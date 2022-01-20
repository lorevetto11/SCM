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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

import javax.persistence.JoinColumn;  

@Entity
@Table(name = "ROL_PROFILES")
@NamedQueries({
	@NamedQuery(name = UserProfile.NQ_PROFILE_BY_ID, query = "Select r from UserProfile r where r.id = :idProfile"),
	@NamedQuery(name = UserProfile.NQ_PROFILES, query = "Select r from UserProfile r")

})

public class UserProfile extends GenericEntity {
	
	private static final long serialVersionUID = 1L;
	public static final String NQ_PROFILES = "userProfile.profileInOrganizations";

	public static final String NQ_PROFILE_BY_ID = "userProfile.getProfileById";
	private String name;
	private Organization organization;
	private List<UserRole> roless = new ArrayList<>();
	private List<Grant> grants = new ArrayList<>();


	/*@ManyToMany(cascade = CascadeType.ALL,targetEntity=UserRole.class)
	@JoinTable(name="rol_roles_rol_profiles",
    joinColumns={@JoinColumn(name="ref_rol_profiles", referencedColumnName="id")},
    inverseJoinColumns={@JoinColumn(name="ref_rol_roles", referencedColumnName="id")}) */
	@ManyToMany(mappedBy="profiles",targetEntity=UserRole.class,cascade = CascadeType.ALL)
	@BatchSize(size=25)
	public List<UserRole> getRoless() {
		return roless;
	}
    public void setRoless(List<UserRole> roless) {
		this.roless = roless;
	}
    
	@ManyToMany(cascade = CascadeType.ALL,targetEntity=Grant.class)
	@JoinTable(name="rol_grants_rol_profiles",
    joinColumns={@JoinColumn(name="ref_rol_profiles", referencedColumnName="id")},
    inverseJoinColumns={@JoinColumn(name="ref_rol_grants", referencedColumnName="id")}) 
	@BatchSize(size=25)
	public List<Grant> getGrants() {
		return grants;
	}
	public void setGrants(List<Grant> grants) {
		this.grants = grants;
	}

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_prof_users")
	@SequenceGenerator(name = "seq_prof_users", sequenceName = "seq_prof_users", allocationSize = 1)
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REF_ORGANIZATION")
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	

	
	
	
	

}
