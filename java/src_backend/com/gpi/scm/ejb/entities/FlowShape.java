package com.gpi.scm.ejb.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.gpi.scm.generic.utils.CommonEnums.flowShapeType;

@Entity
@Table(name = "FLW_SHAPES")
@NamedQueries({
		@NamedQuery(name = FlowShape.NQ_SHAPE_BY_DIAGRAM_ID, query = "Select u from FlowShape u join u.diagram o where o.id=:idDiagram and o.organization.id in :idOrganization"),
		@NamedQuery(name = FlowShape.NQ_SHAPES, query = "Select u from FlowShape u")

})
public class FlowShape extends GenericEntity {

	/**
	 * 
	 */
	public static final String NQ_SHAPE_BY_DIAGRAM_ID = "flowShape.getShapeByDiagramId";
	public static final String NQ_SHAPES = "flowShape.getShapes";

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
	private Diagram diagram;
	private List<FlowAnchorPoint> anchorPoints;
	private FlowElement element;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_flw_shapes")
	@SequenceGenerator(name = "seq_flw_shapes", sequenceName = "seq_flw_shapes", allocationSize = 1)
	public Long getId() {
		return id;
	}

	@Column(name = "\"order\"")
	public Long getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order = order;
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

	@Column(name = "CENTER_X")
	public double getCenterX() {
		return centerX;
	}

	public void setCenterX(double centerX) {
		this.centerX = centerX;
	}

	@Column(name = "CENTER_Y")
	public double getCenterY() {
		return centerY;
	}

	public void setCenterY(double centerY) {
		this.centerY = centerY;
	}

	@Column(name = "WIDTH")
	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	@Column(name = "HEIGHT")
	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	@ManyToOne
	@JoinColumn(name = "REF_DIAGRAM")
	public Diagram getDiagram() {
		return diagram;
	}

	public void setDiagram(Diagram diagram) {
		this.diagram = diagram;
	}
	
    @Enumerated(EnumType.STRING)
	@Column(name="TYPE")
	public flowShapeType getType() {
		return type;
	}

	public void setType(flowShapeType type) {
		this.type = type;
	}
	@Column(name="FILL_COLOR")
	public String getFillColor() {
		return fillColor;
	}

	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}

	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="REF_SHAPE")
	public List<FlowAnchorPoint> getAnchorPoints() {
		return anchorPoints;
	}

	public void setAnchorPoints(List<FlowAnchorPoint> anchorPoints) {
		this.anchorPoints = anchorPoints;
	}

	@OneToOne(mappedBy = "shape", cascade = CascadeType.ALL, 
            fetch = FetchType.LAZY, optional = false)
	public FlowElement getElement() {
		return element;
	}

	public void setElement(FlowElement element) {
		this.element = element;
	}
	
	
}
