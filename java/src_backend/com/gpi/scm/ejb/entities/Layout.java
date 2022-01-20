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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;



@Entity
@Table(name="prp_layouts",
uniqueConstraints = {@UniqueConstraint(columnNames = {"ref_shape"})}
		)
@NamedQueries({ @NamedQuery(name = Layout.NQ_LAYOUT_BY_ID, query = "Select u from Layout u where u.id = :idLayout"),
	@NamedQuery(name = Layout.NQ_LAYOUTS_IN_ORGANIZATIONS, query = "Select u from Layout u where u.organization.id IN :organizations")
})public class Layout extends GenericEntity {

	public static final String NQ_LAYOUT_BY_ID = "layout.getLayoutById";
	public static final String NQ_LAYOUTS_IN_ORGANIZATIONS = "layout.LayoutsInOrganizations";
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private Organization organization;
	private Floor floor;
	private RiskClass riskClass;
	private Shape shape;
	private PrerequisiteType prerequisiteType;
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_prp_layouts")
	@SequenceGenerator(name = "seq_prp_layouts", sequenceName = "seq_prp_layouts", allocationSize = 1)
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
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ref_floor")
	public Floor getFloor() {
		return floor;
	}
	public void setFloor(Floor floor) {
		this.floor = floor;
	}
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ref_riskclasses")
	public RiskClass getRiskClass() {
		return riskClass;
	}
	public void setRiskClass(RiskClass riskClass) {
		this.riskClass = riskClass;
	}
    @OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ref_shape")
	public Shape getShape() {
		return shape;
	}
	public void setShape(Shape shape) {
		this.shape = shape;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ref_organization")
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ref_prerequisite_type")
	public PrerequisiteType getPrerequisiteType() {
		return prerequisiteType;
	}
	public void setPrerequisiteType(PrerequisiteType type) {
		this.prerequisiteType = type;
	}


}
