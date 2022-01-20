package com.gpi.scm.generic.dtos;

public class ShapeDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type;
	private String color;
	private double startX;
	private double startY;
	private double sizeX;
	private double sizeY;
	private double radius;
	private Long order;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getStartX() {
		return startX;
	}
	public void setStartX(double startX) {
		this.startX = startX;
	}
	public double getStartY() {
		return startY;
	}
	public void setStartY(double startY) {
		this.startY = startY;
	}
	public double getSizeX() {
		return sizeX;
	}
	public void setSizeX(double sizeX) {
		this.sizeX = sizeX;
	}
	public double getSizeY() {
		return sizeY;
	}
	public void setSizeY(double sizeY) {
		this.sizeY = sizeY;
	}
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	public Long getOrder() {
		return order;
	}
	public void setOrder(Long order) {
		this.order = order;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}

}
