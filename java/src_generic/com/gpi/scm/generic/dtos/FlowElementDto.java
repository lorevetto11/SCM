package com.gpi.scm.generic.dtos;


import com.gpi.scm.generic.utils.CommonEnums.ccpType;
import com.gpi.scm.generic.utils.CommonEnums.flowElementsType;

public class FlowElementDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private String risk;
	private flowElementsType type;
	private ccpType typeCCP;
	private FlowShapeDto shape;
	private MaterialDto material;
	private ContextDto context;
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
	public flowElementsType getType() {
		return type;
	}
	public void setType(flowElementsType type) {
		this.type = type;
	}
	public FlowShapeDto getShape() {
		return shape;
	}
	public void setShape(FlowShapeDto shape) {
		this.shape = shape;
	}
	public MaterialDto getMaterial() {
		return material;
	}
	public void setMaterial(MaterialDto material) {
		this.material = material;
	}
	public ContextDto getContext() {
		return context;
	}
	public void setContext(ContextDto context) {
		this.context = context;
	}
	public ccpType getTypeCCP() {
		return typeCCP;
	}
	public void setTypeCCP(ccpType typeCCP) {
		this.typeCCP = typeCCP;
	}
	public String getRisk() {
		return risk;
	}
	public void setRisk(String risk) {
		this.risk = risk;
	}

}
