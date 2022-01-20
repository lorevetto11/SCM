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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.gpi.scm.generic.utils.CommonEnums.ccpType;
import com.gpi.scm.generic.utils.CommonEnums.flowElementsType;

@Entity
@Table(name="FLW_ELEMENTS")
@NamedQueries({
@NamedQuery(name = FlowElement.NQ_ELEMENT_BY_SHAPE_ID, query = "Select u from FlowElement u "
		+ "join u.shape o "
		+ "join o.diagram d "
		+ "where o.id = :idShape and d.organization.id in :idOrganization"),

@NamedQuery(name = FlowElement.NQ_ELEMENT_BY_DIAGRAM_ID, query = "Select u from FlowElement u "
		+ "join u.shape o "
		+ "join o.diagram d "
		+ "where d.id = :idDiagram and d.organization.id in :idOrganization"),
		
@NamedQuery(name = FlowElement.NQ_ELEMENT_BY_CONTEXT_ID, query = "Select u from FlowElement u where u.context.id = :idContext"),

})
public class FlowElement extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NQ_ELEMENT_BY_SHAPE_ID = "flowElement.findAllByShape";
	public static final String NQ_ELEMENT_BY_DIAGRAM_ID = "flowElement.findAllByDiagram";
	public static final String NQ_ELEMENT_BY_CONTEXT_ID = "flowElement.getFlowElementByContextId";
	
	private String name;
	private String description;
	private String risk;
	private flowElementsType type;
	private ccpType typeCCP;
	private FlowShape shape;
	private Material material;
	private Context context;
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_flw_elements")
	@SequenceGenerator(name = "seq_flw_elements", sequenceName = "seq_flw_elements", allocationSize = 1)
	public Long getId() {
		return id;
	}
	@ManyToOne
	@JoinColumn(name="REF_MATERIAL")
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
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
	@Enumerated(EnumType.STRING)
	@Column(name="TYPE")
	public flowElementsType getType() {
		return type;
	}
	public void setType(flowElementsType type) {
		this.type = type;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="REF_SHAPE")
	public FlowShape getShape() {
		return shape;
	}
	public void setShape(FlowShape shape) {
		this.shape = shape;
	}
	@OneToOne
	@JoinColumn(name="REF_CONTEXT")
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name="TYPE_CCP")
	public ccpType getTypeCCP() {
		return typeCCP;
	}
	public void setTypeCCP(ccpType typeCCP) {
		this.typeCCP = typeCCP;
	}
	@Column(name="RISK")
	public String getRisk() {
		return risk;
	}
	public void setRisk(String risk) {
		this.risk = risk;
	}

}
