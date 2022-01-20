package com.gpi.scm.generic.dtos;

import javax.validation.constraints.NotNull;


public class FloorDto extends GenericDto {

	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private int order;
	private float width;
	private float height;
	private ContextDto context;
	private OrganizationDto organization;
	
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
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	/**To Do:
	 * Validator for unique costrain
	 * 
	 * @return
	 */
	public ContextDto getContext() {
		return context;
	}
	public void setContext(ContextDto context) {
		this.context = context;
	}
	@NotNull
	public OrganizationDto getOrganization() {
		return organization;
	}
	public void setOrganization(OrganizationDto organization) {
		this.organization = organization;
	}


	
	


}
