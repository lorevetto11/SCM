package com.gpi.scm.ejb.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

@Entity
@Table(name="dng_dangers")

@NamedQueries({ @NamedQuery(name = Danger.NQ_DANGER_BY_ORG_ID, query = "Select u from Danger u where u.organization.id = :idOrg "),
@NamedQuery(name = Danger.NQ_DANGER_BY_CONTEXT_ID, query = "Select u from Danger u where u.context.id = :idContext"),
	
	})
public class Danger extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NQ_DANGER_BY_ORG_ID="danger.findDangerByOrg";
	public static final String NQ_DANGER_BY_CONTEXT_ID = "danger.getDangerByContextId";
	private String name;
	private String description;
	private String type;
	private String risk;
	private String controlMeasure;
	private String criticalLimit;
	private String procedures;
	private String acceptanceLimit;
	private boolean isCcp;
	private PrerequisiteType prerequisiteType;
	private Context context ;
	private Organization organization;
	private List<Material> materials=new ArrayList<>();
	
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dng_dangers")
	@SequenceGenerator(name = "seq_dng_dangers", sequenceName = "seq_dng_dangers", allocationSize = 1)
	public Long getId()
	{
		return this.id;
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
	@Column(name="TYPE")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Column(name="RISK")
	public String getRisk() {
		return risk;
	}

	public void setRisk(String risk) {
		this.risk = risk;
	}
	@Column(name="CONTROL_MEASURE")
	public String getControlMeasure() {
		return controlMeasure;
	}

	public void setControlMeasure(String controlMeasure) {
		this.controlMeasure = controlMeasure;
	}
	@Column(name="CRITICAL_LIMIT")
	public String getCriticalLimit() {
		return criticalLimit;
	}

	public void setCriticalLimit(String criticalLimit) {
		this.criticalLimit = criticalLimit;
	}
	@Column(name="PROCEDURES")
	public String getProcedures() {
		return procedures;
	}

	public void setProcedures(String procedures) {
		this.procedures = procedures;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REF_PREREQUISITE_TYPE")
	public PrerequisiteType getPrerequisiteType() {
		return prerequisiteType;
	}

	public void setPrerequisiteType(PrerequisiteType prerequisiteType) {
		this.prerequisiteType = prerequisiteType;
	}
	@OneToOne
	@JoinColumn(name="REF_CONTEXT")
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	@ManyToOne
	@JoinColumn(name="REF_ORGANIZATION")
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	@Column(name="IS_CCP")
	public boolean isCcp() {
		return isCcp;
	}
	public void setCcp(boolean isCcp) {
		this.isCcp = isCcp;
	}
	
	@ManyToMany(cascade = CascadeType.ALL,targetEntity=Material.class)
	@JoinTable(name="dng_dangers_mtr_materials",
	joinColumns={@JoinColumn(name="ref_dng_dangers", referencedColumnName="id")},
	inverseJoinColumns={@JoinColumn(name="ref_mtr_materials", referencedColumnName="id")}) 
	@BatchSize(size=25)
	public List<Material> getMaterials() {
		return materials;
	}
	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}
	@Column(name="ACCEPTANCE_LIMIT")
	public String getAcceptanceLimit() {
		return acceptanceLimit;
	}
	public void setAcceptanceLimit(String acceptanceLimit) {
		this.acceptanceLimit = acceptanceLimit;
	}
	
}
