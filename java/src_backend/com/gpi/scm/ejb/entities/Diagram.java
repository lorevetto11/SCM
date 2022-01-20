package com.gpi.scm.ejb.entities;

import javax.persistence.*;


@Entity
@Table(name="FLW_DIAGRAMS")
@NamedQuery(name = Diagram.NQ_DIAGRAM_IN_ORGANIZATIONS, query = "Select r from Diagram r where r.organization.id = :organizations ")

public class Diagram extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NQ_DIAGRAM_IN_ORGANIZATIONS = "diagram.findByOrg";
	private String name;
	private String description;
	private Organization organization;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_flw_diagrams")
	@SequenceGenerator(name = "seq_flw_diagrams", sequenceName = "seq_flw_diagrams", allocationSize = 1)
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
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REF_ORGANIZATION")
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	




}
