package com.gpi.scm.generic.dtos;

import javax.validation.constraints.NotNull;

public class BasePrerequisiteDto extends GenericDto {

	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private LayoutDto layout;
	private ShapeDto shape;
	private OrganizationDto organization;
	private FloorDto floor;
	private PrerequisiteTypeDto prerequisiteType;
	private ContextDto context;

	
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
	public ShapeDto getShape() {
		return shape;
	}
	public void setShape(ShapeDto shape) {
		this.shape = shape;
	}
	@NotNull
	public OrganizationDto getOrganization() {
		return organization;
	}
	public void setOrganization(OrganizationDto organization) {
		this.organization = organization;
	}
	@NotNull
	public FloorDto getFloor() {
		return floor;
	}
	public void setFloor(FloorDto floor) {
		this.floor = floor;
	}
	@NotNull
	public LayoutDto getLayout() {
		return layout;
	}
	public void setLayout(LayoutDto layout) {
		this.layout = layout;
	}
	@NotNull
	public PrerequisiteTypeDto getPrerequisiteType() {
		return prerequisiteType;
	}
	public void setPrerequisiteType(PrerequisiteTypeDto type) {
		this.prerequisiteType = type;
	}
	public ContextDto getContext() {
		return context;
	}
	public void setContext(ContextDto context) {
		this.context = context;
	}
	
}
