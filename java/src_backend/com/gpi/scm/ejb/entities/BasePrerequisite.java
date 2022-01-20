package com.gpi.scm.ejb.entities;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;


@MappedSuperclass
public class BasePrerequisite extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private Layout layout;
	private Shape shape;
	private Organization organization;
	private Floor floor;
	private PrerequisiteType prerequisiteType;
	private Context context;
	
	
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
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REF_SHAPE")
	public Shape getShape() {
		return shape;
	}
	public void setShape(Shape shape) {
		this.shape = shape;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REF_ORGANIZATION")
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REF_FLOOR")
	public Floor getFloor() {
		return floor;
	}
	public void setFloor(Floor floor) {
		this.floor = floor;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REF_LAYOUT")
	public Layout getLayout() {
		return layout;
	}
	public void setLayout(Layout layout) {
		this.layout = layout;
	}
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REF_PREREQUISITE_TYPE")
	public PrerequisiteType getPrerequisiteType() {
		return prerequisiteType;
	}
	public void setPrerequisiteType(PrerequisiteType type) {
		this.prerequisiteType = type;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REF_CONTEXT")
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}

	

}
