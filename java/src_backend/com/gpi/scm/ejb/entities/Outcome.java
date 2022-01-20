package com.gpi.scm.ejb.entities;

import javax.persistence.CascadeType;
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
@Table(name="MNT_OUTCOMES")
@NamedQueries({
	
	@NamedQuery(name = Outcome.NQ_OUTCOME_BY_ID, query = "Select r from Outcome r where r.id in :idOutcome and r.monitoring.organization.id IN :organizations "),
	@NamedQuery(name = Outcome.NQ_OUTCOME_BY_MONITOR_ID, query = "Select r from Outcome r where r.monitoring.deleted is false "
			+ "and r.monitoring.id = :idMonitoring and r.monitoring.organization.id IN :organizations order by r.insertTime desc")


})
public class Outcome extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NQ_OUTCOME_BY_ID ="outcome.OutcomeById";
	public static final String NQ_OUTCOME_BY_MONITOR_ID ="outcome.OutcomeByMonitorId";


	private boolean result;
	private String note;
	private Context context;
	private User user;
	private Monitoring monitoring;
	

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_mnt_outcomes")
	@SequenceGenerator(name = "seq_mnt_outcomes", sequenceName = "seq_mnt_outcomes", allocationSize = 1)
	public Long getId()
	{
		return id;
	}
	@Column(name="RESULT")
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	@Column(name="NOTE")
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	@OneToOne
	@JoinColumn(name="REF_CONTEXT")
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="REF_USER")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="REF_MONITORINGS")
	public Monitoring getMonitoring() {
		return monitoring;
	}
	public void setMonitoring(Monitoring monitoring) {
		this.monitoring = monitoring;
	}

}
