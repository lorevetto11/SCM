package com.gpi.scm.ejb.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.gpi.scm.generic.utils.CommonEnums.flowElementsType;

@Entity
@Table(name="FLW_RELATIONS")
@NamedQuery(name = FlowRelation.NQ_RELATION_BY_DIAGRAM_ID, query = "Select u from FlowRelation u "
		+ "join u.startPoint s "
		+ "join u.endPoint e "
		+ "join e.shape q "
		+ "join s.shape w "
		+ "join w.diagram d1 "
		+ "join q.diagram d2 "
		+ "where (d1.id = :idDiagram or d2.id= :idDiagram) "
		+ "and d1.organization.id in :idOrganization "
		+ "and d2.organization.id in :idOrganization")

public class FlowRelation extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NQ_RELATION_BY_DIAGRAM_ID = "flowRelation.findAllByDiagram";
	private String name;
	private String description;
	private Long order;
	private String type;
	private FlowAnchorPoint startPoint;
	private FlowAnchorPoint endPoint;
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_flw_relations")
	@SequenceGenerator(name = "seq_flw_relations", sequenceName = "seq_flw_relations", allocationSize = 1)
	public Long getId() {
		return id;
	}
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
//	@Enumerated(EnumType.STRING)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@ManyToOne
	@JoinColumn(name="START_ANCHOR_POINT")
	public FlowAnchorPoint getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(FlowAnchorPoint startPoint) {
		this.startPoint = startPoint;
	}
	@ManyToOne
	@JoinColumn(name="END_ANCHOR_POINT")
	public FlowAnchorPoint getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(FlowAnchorPoint endPoint) {
		this.endPoint = endPoint;
	}
	@Column(name="\"order\"")
	public Long getOrder() {
		return order;
	}
	public void setOrder(Long order) {
		this.order = order;
	}


}
