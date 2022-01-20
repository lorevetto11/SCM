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
@Table(name = "CKS_PROCESS_CHECK_OUTCOMES")
@NamedQueries({
		@NamedQuery(name = ProcessCheckOutcome.NQ_PROCESSCHECKS_OUTCOME_BY_PLANNING, query = "Select o from ProcessCheckOutcome o where o.processcheckPlanning.id = :processCheckPlanningId and processCheck.id=:processCheckId order by insertTime desc"),
		@NamedQuery(name = ProcessCheckOutcome.NQ_PROCESSCHECK_OUTCOMES_ORGANIZATIONS, query = "Select n.organization.id,p.organization.id,c.organization.id from ProcessCheckOutcome o "
				+ "left outer join o.nonCompliance n "
				+ "left outer join o.processcheckPlanning p "
				+ "left outer join o.processCheck c "
				+ "where ((n is not null and n.deleted is false)"
				+ " or (p is not null and p.deleted is false) "
				+ "or (c is not null and c.deleted is false))"
				+ " and o.id =:outcomeId" + " order by o.id")
		})
public class ProcessCheckOutcome extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NQ_PROCESSCHECKS_OUTCOME_BY_PLANNING = "processCheckOutcome.findByProcessCheckPlanning";
	public static final String NQ_PROCESSCHECK_OUTCOMES_ORGANIZATIONS = "processCheckOutcome.findOrganizations";

	private String evidence;
	private NonCompliance nonCompliance;
	private ProcessCheckPlanning processcheckPlanning;
	private ProcessCheck processCheck;
	private Context context;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cks_processchecks_outcomes")
	@SequenceGenerator(name = "seq_cks_processchecks_outcomes", sequenceName = "seq_cks_processchecks_outcomes", allocationSize = 1)
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
	@JoinColumn(name = "REF_PROCESS_CHECK_PLANNING")
	public ProcessCheckPlanning getProcesscheckPlanning() {
		return processcheckPlanning;
	}

	public void setProcesscheckPlanning(ProcessCheckPlanning processcheckPlanning) {
		this.processcheckPlanning = processcheckPlanning;
	}

	@OneToOne
	@JoinColumn(name = "REF_CONTEXT")
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	@ManyToOne
	@JoinColumn(name = "REF_PROCESS_CHECK")
	public ProcessCheck getProcessCheck() {
		return processCheck;
	}

	public void setProcessCheck(ProcessCheck processCheck) {
		this.processCheck = processCheck;
	}

}
