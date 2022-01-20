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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CKS_PROCESS_CHECKS")
@NamedQueries({
		@NamedQuery(name = ProcessCheck.NQ_PROCESSCHECKS, query = "Select o from ProcessCheck o where private is false and o.organization.deleted is false "),
		@NamedQuery(name = ProcessCheck.NQ_PROCESSCHECKS_TRUE, query = "Select s from ProcessCheck s join s.organization o "
				+ "where private is true " 
				+ "and o.id in :organizations")

})
public class ProcessCheck extends GenericEntity {

	private static final long serialVersionUID = 1L;
	public static final String NQ_PROCESSCHECKS = "processCheck.findAll";
	public static final String NQ_PROCESSCHECKS_TRUE = "processCheck.findAllTrue";

	private String name;
	private String description;
	private boolean privacy;
	private String type;
	private Organization organization;
	private Frequency frequency;
	private PrerequisiteType prerequisiteType;
	private List<ProcessCheckPlanning> processcheckPlannings = new ArrayList<>();
	private List<ProcessCheckOutcome> processCheckOutcomes= new ArrayList<>(); 

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cks_processcheck")
	@SequenceGenerator(name = "seq_cks_processcheck", sequenceName = "seq_cks_processcheck", allocationSize = 1)
	public Long getId() {
		return id;
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

	@Column(name = "PRIVATE")
	public boolean isPrivacy() {
		return privacy;
	}

	public void setPrivacy(boolean privacy) {
		this.privacy = privacy;
	}

	@Column(name = "TYPE")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
	@JoinColumn(name = "REF_FREQUENCY")
	public Frequency getFrequency() {
		return frequency;
	}

	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "REF_PREREQUISITE_TYPE")
	public PrerequisiteType getPrerequisiteType() {
		return prerequisiteType;
	}

	public void setPrerequisiteType(PrerequisiteType prerequisiteType) {
		this.prerequisiteType = prerequisiteType;
	}

	@ManyToMany(mappedBy = "processChecks", targetEntity = ProcessCheckPlanning.class, cascade = CascadeType.ALL)
	public List<ProcessCheckPlanning> getProcesscheckPlannings() {
		return processcheckPlannings;
	}

	public void setProcesscheckPlannings(List<ProcessCheckPlanning> processcheckPlannings) {
		this.processcheckPlannings = processcheckPlannings;
	}
	@OneToMany(mappedBy="processCheck")
	public List<ProcessCheckOutcome> getProcessCheckOutcomes() {
		return processCheckOutcomes;
	}

	public void setProcessCheckOutcomes(List<ProcessCheckOutcome> processCheckOutcomes) {
		this.processCheckOutcomes = processCheckOutcomes;
	}

}
