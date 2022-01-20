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
@Table(name="MNT_PROCEDURES")
@NamedQueries({ @NamedQuery(name = Procedure.NQ_PROCEDURE_BY_ID, query = "Select u from Procedure u where u.id = :idProcedure"),
	@NamedQuery(name = Procedure.NQ_PROCEDURES_IN_ORGANIZATIONS, query = "Select u from Procedure u where u.organization.id IN :organizations and u.privacy is false")
	})
public class Procedure extends GenericEntity {

	 private static final long serialVersionUID = 1L;
	public static final String NQ_PROCEDURE_BY_ID="procedure.procedureById";
	public static final String NQ_PROCEDURES_IN_ORGANIZATIONS="procedure.procedureInOrganizations";
	
	
	private String title;       
	private String description;            
	private String purpose;          
	private  String equipments;
	private String activities;       
	private String process_check ;         
	private String results_check; 
	private Long revision;              
	private boolean privacy;                
	private RiskClass riskclass;          
	private Organization organization;
	private UserRole userRole;
	private PrerequisiteType prerequisiteType;  
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_mnt_procedures")
	@SequenceGenerator(name = "seq_mnt_procedures", sequenceName = "seq_mnt_procedures", allocationSize = 1)
	public Long getId()
	{
		return id;
	}
	@Column(name="TITLE")
    public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name="PURPOSE")
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	@Column(name="EQUIPMENTS")
	public String getEquipments() {
		return equipments;
	}
	public void setEquipments(String equipments) {
		this.equipments = equipments;
	}
	@Column(name="ACTIVITIES")
	public String getActivities() {
		return activities;
	}
	public void setActivities(String activities) {
		this.activities = activities;
	}
	@Column(name="PROCESS_CHECK")
	public String getProcess_check() {
		return process_check;
	}
	public void setProcess_check(String process_check) {
		this.process_check = process_check;
	}
	@Column(name="RESULTS_CHECK")
	public String getResults_check() {
		return results_check;
	}
	public void setResults_check(String results_check) {
		this.results_check = results_check;
	}
	@Column(name="REVISION")
	public Long getRevision() {
		return revision;
	}
	public void setRevision(Long revision) {
		this.revision = revision;
	}
	@Column(name="PRIVATE")
	public boolean getPrivacy() {
		return privacy;
	}
	public void setPrivacy(boolean privacy) {
		this.privacy = privacy;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REF_RISKCLASS")
	public RiskClass getRiskClass() {
		return riskclass;
	}
	public void setRiskClass(RiskClass rClass) {
		this.riskclass = rClass;
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
	@JoinColumn(name="REF_USER_ROLE")
	public UserRole getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRole uRole) {
		this.userRole = uRole;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REF_PREREQUISITE_TYPE")
	public PrerequisiteType getPrerequisiteType() {
		return prerequisiteType;
	}
	public void setPrerequisiteType(PrerequisiteType pType) {
		this.prerequisiteType = pType;
	}
  
 

}
