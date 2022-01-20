package com.gpi.scm.ejb.entities;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CKS_SYSTEM_CHECK_PLANNINGS")
@NamedQueries({
		@NamedQuery(name = SystemCheckPlanning.NQ_SYSTEMCHECKS_PLANNING, query = "Select o from SystemCheckPlanning o where o.organization.id = :organizations")

})
public class SystemCheckPlanning extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NQ_SYSTEMCHECKS_PLANNING = "sysCheckPlanning.findAll";
	private String status;
	private Date date;
	private Date startDate;
	private Date closeDate;
	private Organization organization;
	private Organization company;
	private List<SystemCheck> systemChecks= new ArrayList<>();

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cks_systemchecks_plannings")
	@SequenceGenerator(name = "seq_cks_systemchecks_plannings", sequenceName = "seq_cks_systemchecks_plannings", allocationSize = 1)
	public Long getId() {
		return this.id;
	}

	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "\"DATE\"")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
	@JoinColumn(name = "REF_COMPANY")
	public Organization getCompany() {
		return company;
	}

	public void setCompany(Organization company) {
		this.company = company;
	}
	
	@ManyToMany(cascade = CascadeType.ALL,targetEntity=SystemCheck.class)
	@JoinTable(name="cks_system_checks_cks_system_check_plannings",
	joinColumns={@JoinColumn(name="ref_cks_system_check_plannings", referencedColumnName="id")},
	inverseJoinColumns={@JoinColumn(name="ref_cks_system_checks", referencedColumnName="id")}) 
	public List<SystemCheck> getSystemChecks() {
		return systemChecks;
	}

	public void setSystemChecks(List<SystemCheck> systemChecks) {
		this.systemChecks = systemChecks;
	}
	@Column(name = "START_DATE")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@Column(name = "CLOSE_DATE")
	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

}
