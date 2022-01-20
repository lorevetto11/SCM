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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CKS_SYSTEM_CHECK_OUTCOMES")
@NamedQueries({
		@NamedQuery(name = SystemCheckOutcome.NQ_SYSTEMCHECKS_OUTCOME_BY_SYSCHECK_REQUIREMENT, query = "Select o from SystemCheckOutcome o where o.systemcheckRequirement.id = :systemCheckRequirementId order by insertTime desc"),
		@NamedQuery(name = SystemCheckOutcome.NQ_SYSTEMCHECK_OUTCOMES_ORGANIZATIONS, 
		query = "Select n.organization.id,p.organization.id from SystemCheckOutcome o "
				+ "left outer join o.nonCompliance n "
				+ "left outer join o.systemcheckPlanning p "
				+ "where ((n is not null and n.deleted is false) or (p is not null and p.deleted is false))"
				+ " and o.id =:outcomeId"
				+ " order by o.id"
				)
})
public class SystemCheckOutcome extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NQ_SYSTEMCHECKS_OUTCOME_BY_SYSCHECK_REQUIREMENT = "systemCheckOutcome.findBySystemCheckRequirement";
	public static final String NQ_SYSTEMCHECK_OUTCOMES_ORGANIZATIONS = "systemCheckOutcome.findOrganizations";

	private String evidence;
	private NonCompliance nonCompliance;
	private SystemCheckRequirement systemcheckRequirement;
	private SystemCheckPlanning systemcheckPlanning;
	private Context context;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cks_systemchecks_outcomes")
	@SequenceGenerator(name = "seq_cks_systemchecks_outcomes", sequenceName = "seq_cks_systemchecks_outcomes", allocationSize = 1)
	public Long getId() {
		return id;
	}

	@Column(name = "EVIDENCE")
	public String getEvidence() {
		return evidence;
	}

	public void setEvidence(String evidence) {
		this.evidence = evidence;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REF_NONCOMPLIANCE")
	public NonCompliance getNonCompliance() {
		return nonCompliance;
	}

	public void setNonCompliance(NonCompliance nonCompliance) {
		this.nonCompliance = nonCompliance;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REF_SYSTEM_CHECK_REQUIREMENT")
	public SystemCheckRequirement getSystemcheckRequirement() {
		return systemcheckRequirement;
	}

	public void setSystemcheckRequirement(SystemCheckRequirement systemcheckRequirements) {
		this.systemcheckRequirement = systemcheckRequirements;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REF_SYSTEM_CHECK_PLANNING")
	public SystemCheckPlanning getSystemcheckPlanning() {
		return systemcheckPlanning;
	}

	public void setSystemcheckPlanning(SystemCheckPlanning systemcheckPlanning) {
		this.systemcheckPlanning = systemcheckPlanning;
	}

	@OneToOne
	@JoinColumn(name = "REF_CONTEXT")
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

}
