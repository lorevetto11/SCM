package com.gpi.scm.generic.dtos;

import java.util.List;

import jdk.nashorn.internal.ir.annotations.Ignore;

import com.gpi.scm.generic.utils.CommonEnums.flowShapeType;

public class FlowShapeDto extends GenericDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private String fillColor;
	private double centerX;
	private double centerY;
	private double width;
	private double height;
	private flowShapeType type;
	private Long order;
	private DiagramDto diagram;
	private List<FlowAnchorPointDto> anchorPoints;
	private FlowElementDto element;
	
	@Ignore
	private boolean selected;


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

	public double getCenterX() {
		
		centerX = Double.parseDouble(String.format("%.3f", centerX).replace(',', '.'));
		return centerX;
	}

	public void setCenterX(double centerX) {
		
		centerX = Double.parseDouble(String.format("%.3f", centerX).replace(',', '.'));
		this.centerX = centerX;
	}

	public double getCenterY() {

		centerY = Double.parseDouble(String.format("%.3f", centerY).replace(',', '.'));
		return centerY;
	}

	public void setCenterY(double centerY) {

		centerY = Double.parseDouble(String.format("%.3f", centerY).replace(',', '.'));
		this.centerY = centerY;
	}

	public double getWidth() {
		
		width = Double.parseDouble(String.format("%.3f", width).replace(',', '.'));
		return width;
	}

	public void setWidth(double width) {
		
		width = Double.parseDouble(String.format("%.3f", width).replace(',', '.'));
		this.width = width;
	}

	public double getHeight() {
		
		height = Double.parseDouble(String.format("%.3f", height).replace(',', '.'));
		return height;
	}

	public void setHeight(double height) {
		
		height = Double.parseDouble(String.format("%.3f", height).replace(',', '.'));
		this.height = height;
	}

	public Long getzOrder() {
		return order;
	}

	public void setzOrder(Long order) {
		this.order = order;
	}

	public DiagramDto getDiagram() {
		return diagram;
	}

	public void setDiagram(DiagramDto diagram) {
		this.diagram = diagram;
	}

	public flowShapeType getType() {
		return type;
	}

	public void setType(flowShapeType type) {
		this.type = type;
	}

	public String getFillColor() {
		return fillColor;
	}

	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}

	public List<FlowAnchorPointDto> getAnchorPoints() {
		return anchorPoints;
	}

	public void setAnchorPoints(List<FlowAnchorPointDto> anchorPoints) {
		this.anchorPoints = anchorPoints;
	}

	public FlowElementDto getElement() {
		return element;
	}

	public void setElement(FlowElementDto element) {
		this.element = element;
	}
	
	

}
