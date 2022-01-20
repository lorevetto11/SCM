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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CKS_SYSTEM_CHECKS")
@NamedQueries({
		@NamedQuery(name = SystemCheck.NQ_SYSTEMCHECKS, query = "Select o from SystemCheck o where private is false and o.organization.deleted is false"),
		@NamedQuery(name = SystemCheck.NQ_SYSTEMCHECK_BY_ORG, query = "Select o from SystemCheck o where o.id = :idOrg"),
	/*   @NamedQuery(name = SystemCheck.NQ_SYSTEMCHECKS_TRUE, query = "Select o from SystemCheck o where private is true "
				+ "and (o.organization.organization.id = :organization or o.organization.id=:organization)")*/
		@NamedQuery(name = SystemCheck.NQ_SYSTEMCHECKS_TRUE, query = "Select s from SystemCheck s join s.organization o "
				+ "where private is true "
				+ "and o.id in :organizations "
		
				 )


})
public class SystemCheck extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NQ_SYSTEMCHECKS = "sysCheck.findAll";
	public static final String NQ_SYSTEMCHECKS_TRUE = "sysCheck.findAllTrue";
	public static final String NQ_SYSTEMCHECK_BY_ORG = "sysCheck.findById";
	private String name;
	private String description;
	private boolean privacy;
	private Organization organization;
	private List<SystemCheckPlanning> systemcheckPlannings = new ArrayList<>();

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cks_systemchecks")
	@SequenceGenerator(name = "seq_cks_systemchecks", sequenceName = "seq_cks_systemchecks", allocationSize = 1)
	public Long getId() {
		return this.id;
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

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "REF_ORGANIZATION")
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@ManyToMany(mappedBy = "systemChecks", targetEntity = SystemCheckPlanning.class, cascade = CascadeType.ALL)
	public List<SystemCheckPlanning> getSystemcheckPlannings() {
		return systemcheckPlannings;
	}

	public void setSystemcheckPlannings(List<SystemCheckPlanning> systemcheckPlannings) {
		this.systemcheckPlannings = systemcheckPlannings;
	}

}
