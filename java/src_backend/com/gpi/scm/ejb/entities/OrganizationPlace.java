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
@Table(name="ORG_PLACES")
@NamedQueries({@NamedQuery(name = OrganizationPlace.NQ_ORGANIZATIONPLACES, query = "Select o from OrganizationPlace o where o.organization.id IN :organizations"),
	@NamedQuery(name = OrganizationPlace.NQ_PLACE_BY_ID, query = "Select o from OrganizationPlace o where o.id = :idOrg")

})
public class OrganizationPlace extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NQ_ORGANIZATIONPLACES = "organizationPlaces.organizationPlaces";
	public static final String NQ_PLACE_BY_ID = "organizationPlaces.organizationPlacesIds";
	private String name;
	private String address;
	private String description;
	private Organization organization;
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_org_organizationPlaces")
	@SequenceGenerator(name = "seq_org_organizationPlaces", sequenceName = "seq_org_organizationPlaces", allocationSize = 1)
	public Long getId()
	{
		return id;
	}
	@Column(name="NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="ADDRESS")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REF_ORGANIZATION")
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

}
