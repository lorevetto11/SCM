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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="MNT_MONITORINGS")
@NamedQueries({
	@NamedQuery(name = Monitoring.NQ_MONITORING_IN_ORGANIZATIONS, query = "Select r from Monitoring r where r.organization.id IN :organizations "),
	//@NamedQuery(name = Monitoring.NQ_MONITORING_BY_ROLEID, query = "Select r from Monitoring r join r.organization.roles u where u.id = :idRole and u.deleted is false and r.organization.id=:IdOrg "),
	@NamedQuery(name = Monitoring.NQ_MONITORING_BY_ID, query = "Select r from Monitoring r  where r.id = :idMonitoring  "),
	@NamedQuery(name = Monitoring.NQ_MONITORING_BY_ROLEID, query = "Select r from Monitoring r join r.procedure u where r.organization.id IN :organizations and u.userRole.id = :idRole and u.deleted is false  ")

	
			

})
public class Monitoring extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NQ_MONITORING_IN_ORGANIZATIONS = "monitoring.InOrganizations";
	public static final String NQ_MONITORING_BY_ID = "monitoring.ById";
	public static final String NQ_MONITORING_BY_ROLEID = "monitoring.ByRoleId";

	
	Organization organization;
	Context context;
	Frequency frequency;
	Procedure procedure;
	List<Outcome> outcomes= new ArrayList<>();
	

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_mnt_monitorings")
	@SequenceGenerator(name = "seq_mnt_monitorings", sequenceName = "seq_mnt_monitorings", allocationSize = 1)
	public Long getId()
	{
		return id;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REF_ORGANIZATION")
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	@OneToOne
	@JoinColumn(name="REF_CONTEXT")
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REF_FREQUENCY")
	public Frequency getFrequency() {
		return frequency;
	}

	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REF_PROCEDURE")
	public Procedure getProcedure() {
		return procedure;
	}

	public void setProcedure(Procedure procedure) {
		this.procedure = procedure;
	}
	
	@OneToMany(mappedBy = "monitoring", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@OrderBy("insert_time desc")
	public List<Outcome> getOutcomes() {
			return outcomes;
	}

	public void setOutcomes(List<Outcome> outcomes) {
		this.outcomes = outcomes;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
	}

}
