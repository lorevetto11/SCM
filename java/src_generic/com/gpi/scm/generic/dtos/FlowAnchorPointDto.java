package com.gpi.scm.generic.dtos;

import com.gpi.scm.generic.utils.CommonEnums.flowElementsType;

public class FlowAnchorPointDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private double translationX;
	private double translationY;
	private double width;
	private double height;
	private Long order;
	private FlowShapeDto shape;
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
	public double getTranslationX() {
		return translationX;
	}
	public void setTranslationX(double translationX) {
		this.translationX = translationX;
	}
	public double getTranslationY() {
		return translationY;
	}
	public void setTranslationY(double translationY) {
		this.translationY = translationY;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public Long getOrder() {
		return order;
	}
	public void setOrder(Long order) {
		this.order = order;
	}
	public FlowShapeDto getShape() {
		return shape;
	}
	public void setShape(FlowShapeDto shape) {
		this.shape = shape;
	}
}
