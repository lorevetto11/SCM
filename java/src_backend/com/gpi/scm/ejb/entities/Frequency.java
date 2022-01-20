package com.gpi.scm.ejb.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
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

@Entity
@Table(name="MNT_FREQUENCIES")

@NamedQueries({
	@NamedQuery(name = Frequency.NQ_FREQUENCIES_IN_ORGANIZATIONS, query = "Select r from Frequency r where r.organization.id IN :organizations and r.type = 'DEFAULT' "),
	@NamedQuery(name = Frequency.NQ_FREQUENCY_BY_ID, query = "Select r from Frequency r where r.id=:freqId ")
	
			

})
public class Frequency extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NQ_FREQUENCIES_IN_ORGANIZATIONS = "frquency.InOrganizations";
	public static final String NQ_FREQUENCY_BY_ID = "frequency.ById";


	
	private boolean asNeeded;
	private boolean justOnce;
	private String type;
	private  String period;
	private Long value;     
	private RiskClass riskclass;          
	private Organization organization;
	private PrerequisiteType prerequisiteType;  

	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_mnt_frequencies")
	@SequenceGenerator(name = "seq_mnt_frequencies", sequenceName = "seq_mnt_frequencies", allocationSize = 1)
	public Long getId()
	{
		return id;
	}
	
	@Column(name="PERIOD")
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	@Column(name="VALUE")
	public Long getValue() {
		return value;
	}
	public void setValue(Long value) {
		this.value = value;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REF_RISKCLASS")
	public RiskClass getRiskClass() {
		return riskclass;
	}
	public void setRiskClass(RiskClass riskclass) {
		this.riskclass = riskclass;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REF_ORGANIZATION")
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REF_PREREQUISITE_TYPE")
	public PrerequisiteType getPrerequisiteType() {
		return prerequisiteType;
	}
	public void setPrerequisiteType(PrerequisiteType prerequisiteType) {
		this.prerequisiteType = prerequisiteType;
	}

	@Column(name="JUSTONCE")
	public boolean isJustOnce() {
		return justOnce;
	}

	public void setJustOnce(boolean justOnce) {
		this.justOnce = justOnce;
	}

	@Column(name="ASNEEDED")
	public boolean isAsNeeded() {
		return asNeeded;
	}

	public void setAsNeeded(boolean asNeeded) {
		this.asNeeded = asNeeded;
	}
	@Column(name="TYPE")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
