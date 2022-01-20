package com.gpi.scm.generic.dtos;

import javax.validation.constraints.NotNull;

import com.gpi.scm.generic.dtos.validators.Conditional;


@Conditional(selected = "type", values = {"DEFAULT"}, required = {"riskClass"})
public class FrequencyDto extends GenericDto {
	
	private static final long serialVersionUID = 1L;
	private String type;
	private boolean asNeeded;       
	private boolean justOnce;     
	private  String period;
	private Long valueee;     
	private RiskClassDto riskClass;          
	private OrganizationDto organization;
	private PrerequisiteTypeDto prerequisiteType;  
	
	public boolean isAsNeeded() {
		return asNeeded;
	}
	public void setAsNeeded(boolean asNeeded) {
		this.asNeeded = asNeeded;
	}
	public boolean isJustOnce() {
		return justOnce;
	}
	public void setJustOnce(boolean justOnce) {
		this.justOnce = justOnce;
	}

	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public Long getValue() {
		return valueee;
	}
	public void setValue(Long value) {
		this.valueee = value;
	}
	public RiskClassDto getRiskClass() {
		return riskClass;
	}
	public void setRiskClass(RiskClassDto riskclass) {
		this.riskClass = riskclass;
	}
	@NotNull
	public OrganizationDto getOrganization() {
		return organization;
	}
	public void setOrganization(OrganizationDto organization) {
		this.organization = organization;
	}
	@NotNull
	public PrerequisiteTypeDto getPrerequisiteType() {
		return prerequisiteType;
	}
	public void setPrerequisiteType(PrerequisiteTypeDto prerequisiteType) {
		this.prerequisiteType = prerequisiteType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
