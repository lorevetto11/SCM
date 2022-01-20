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
@Table(name = "ANL_ANALYSIS_PARAMETERS")
@NamedQueries({
		@NamedQuery(name = AnalysisParameter.NQ_PARAMETER_BY_ORG_ID, query = "Select u from AnalysisParameter u where u.organization.id = :idOrg ")

})

public class AnalysisParameter extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NQ_PARAMETER_BY_ORG_ID = "analysisParameter.findByOrganization";
	private String name;
	private String description;
	private Float thresholdValue;
	private UserRole userRole;
	private Organization organization;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_anl_analysis_parameter")
	@SequenceGenerator(name = "seq_anl_analysis_parameter", sequenceName = "seq_anl_analysis_parameter", allocationSize = 1)
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

	@Column(name = "THRESHOLD_VALUE")
	public Float getThresholdValue() {
		return thresholdValue;
	}

	public void setThresholdValue(Float thresholdValue) {
		this.thresholdValue = thresholdValue;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REF_ROLE_IN_CHARGE")
	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REF_ORGANIZATION")
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

}
