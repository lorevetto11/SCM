package com.gpi.scm.ejb.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="DRW_SHAPES")
@NamedQueries({ @NamedQuery(name = Shape.NQ_SHAPE_BY_ID, query = "Select u from Shape u where u.id = :idShape"),
	 @NamedQuery(name = Shape.NQ_SHAPES, query = "Select u from Shape u")

	

})
public class Shape extends GenericEntity {

	/**
	 * 
	 */
	public static final String NQ_SHAPE_BY_ID = "shape.getShapeById";
	public static final String NQ_SHAPES = "shape.getShapes";

	private static final long serialVersionUID = 1L;
	private String type;
	private double startX;
	private double startY;
	private double sizeX;
	private double sizeY;
	private double radius;
	private Long order;
	private String color;
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_drw_shapes")
	@SequenceGenerator(name = "seq_drw_shapes", sequenceName = "seq_drw_shapes", allocationSize = 1)
	public Long getId()
	{
		return id;
	}
	@Column(name="TYPE")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(name="START_X")
	public double getStartX() {
		return startX;
	}
	public void setStartX(double start_x) {
		this.startX = start_x;
	}
	@Column(name="START_Y")
	public double getStartY() {
		return startY;
	}

	public void setStartY(double start_y) {
		this.startY = start_y;
	}
	@Column(name="SIZE_X")
	public double getSizeX() {
		return sizeX;
	}
	public void setSizeX(double size_x) {
		this.sizeX = size_x;
	}
	@Column(name="SIZE_Y")
	public double getSizeY() {
		return sizeY;
	}
	public void setSizeY(double size_y) {
		this.sizeY = size_y;
	}
	@Column(name="RADIUS")
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	@Column(name="\"order\"")
	public Long getOrder() {
		return order;
	}
	public void setOrder(Long order) {
		this.order = order;
	}
	@Column(name="COLOR")
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	

}
