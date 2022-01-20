package com.gpi.scm.ejb.entities;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.gpi.scm.generic.utils.CommonEnums.flowElementsType;

@Entity
@Table(name = "FLW_ANCHOR_POINTS")
@NamedQueries({
		@NamedQuery(name = FlowAnchorPoint.NQ_ANCOR_POINTS_BY_DIAGRAM_ID, query = "Select u from FlowAnchorPoint u "
				+ "join u.shape o "
				+ "join o.diagram d where d.id=:idDiagram and d.organization.id in :organizations")

})
public class FlowAnchorPoint extends GenericEntity {

	/**
	 * 
	 */
	public static final String NQ_ANCOR_POINTS_BY_DIAGRAM_ID = "flowAnchorPoint.getPoinByDiagramId";
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private double translationX;
	private double translationY;
	private double width;
	private double height;
	private Long order;
	private FlowShape shape;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_flw_anchor_points")
	@SequenceGenerator(name = "seq_flw_anchor_points", sequenceName = "seq_flw_anchor_points", allocationSize = 1)
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
	@JoinColumn(name = "REF_SHAPE")
	public FlowShape getShape() {
		return shape;
	}

	public void setShape(FlowShape shape) {
		this.shape = shape;
	}

	@Column(name = "TRANSLATION_X")
	public double getTranslationX() {
		return translationX;
	}

	public void setTranslationX(double translationX) {
		this.translationX = translationX;
	}

	@Column(name = "TRANSLATION_Y")
	public double getTranslationY() {
		return translationY;
	}

	public void setTranslationY(double translationY) {
		this.translationY = translationY;
	}

}
