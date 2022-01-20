package com.gpi.scm.generic.dtos;

import com.gpi.scm.generic.utils.CommonEnums.flowElementsType;

public class FlowRelationDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private Long order;
	private String type;
	private FlowAnchorPointDto startPoint;
	private FlowAnchorPointDto endPoint;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public FlowAnchorPointDto getStartAnchorPoint() {
		return startPoint;
	}

	public void setStartAnchorPoint(FlowAnchorPointDto startPoint) {
		this.startPoint = startPoint;
	}

	public FlowAnchorPointDto getEndAnchorPoint() {
		return endPoint;
	}

	public void setEndAnchorPoint(FlowAnchorPointDto endPoint) {
		this.endPoint = endPoint;
	}

	public Long getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order = order;
	}

	
}
