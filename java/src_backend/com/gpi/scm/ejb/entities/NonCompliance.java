package com.gpi.scm.ejb.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CKS_NONCOMPLIANCES")

public class NonCompliance extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date closeDate;
	private Date startDate;
	private String description;
	private User closeUser;
	private String treatment;
	private String retrieval;
	private String causes;
	private String corrections;
	private String checks;
	private Organization organization;
	private ProcessCheck processcheck;
	private Context context;
	private SystemCheckRequirement systemCheckRequirement;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cks_noncompliances")
	@SequenceGenerator(name = "seq_cks_noncompliances", sequenceName = "seq_cks_noncompliances", allocationSize = 1)
	public Long getId() {
		return this.id;
	}

	@Column(name = "CLOSE_DATE")
	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "TREATMENT")
	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	@Column(name = "RETRIEVAL")
	public String getRetrieval() {
		return retrieval;
	}

	public void setRetrieval(String retrieval) {
		this.retrieval = retrieval;
	}

	@Column(name = "CAUSES")
	public String getCauses() {
		return causes;
	}

	public void setCauses(String causes) {
		this.causes = causes;
	}

	@Column(name = "CORRECTIONS")
	public String getCorrections() {
		return corrections;
	}

	public void setCorrections(String corrections) {
		this.corrections = corrections;
	}

	@Column(name = "CHECKS")
	public String getChecks() {
		return checks;
	}

	public void setChecks(String checks) {
		this.checks = checks;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "REF_ORGANIZATION")
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "REF_PROCESSCHECK")
	public ProcessCheck getProcessCheck() {
		return processcheck;
	}

	public void setProcessCheck(ProcessCheck processcheck) {
		this.processcheck = processcheck;
	}

	@OneToOne
	@JoinColumn(name = "REF_CONTEXT")
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "REF_SYSTEM_CHECK_REQUIREMENT")
	public SystemCheckRequirement getSystemCheckRequirement() {
		return systemCheckRequirement;
	}

	public void setSystemCheckRequirement(SystemCheckRequirement systemCheckRequirement) {
		this.systemCheckRequirement = systemCheckRequirement;
	}
	@Column(name="START_DATE")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CLOSE_USER")
	public User getCloseUser() {
		return closeUser;
	}

	public void setCloseUser(User closeUser) {
		this.closeUser = closeUser;
	}

}
