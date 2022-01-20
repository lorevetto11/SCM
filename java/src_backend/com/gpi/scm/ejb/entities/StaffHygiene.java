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
@Table(name = "prp_staffhygiene")
@NamedQueries({
		@NamedQuery(name = StaffHygiene.NQ_STAFFHYGIENE_BY_ORG, query = "Select u from StaffHygiene u where u.organization.id = :idOrganization"),
		@NamedQuery(name = StaffHygiene.NQ_STAFFHYGIENE_BY_CONTEXT_ID, query = "Select u from StaffHygiene u where u.context.id = :idContext"),
})
public class StaffHygiene extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NQ_STAFFHYGIENE_BY_ORG = "staffHygiene.findByStaffHygieneByOrg";
	public static final String NQ_STAFFHYGIENE_BY_CONTEXT_ID = "staffHygiene.getStaffHygieneByContextId";
	private PrerequisiteType prerequisiteType;
	private Context context;
	private Organization organization;
	private UserRole role;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_prp_staffhygiene")
	@SequenceGenerator(name = "seq_prp_staffhygiene", sequenceName = "seq_prp_staffhygiene", allocationSize = 1)
	public Long getId() {
		return this.id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REF_PREREQUISITE_TYPE")
	public PrerequisiteType getPrerequisiteType() {
		return prerequisiteType;
	}

	public void setPrerequisiteType(PrerequisiteType prerequisiteType) {
		this.prerequisiteType = prerequisiteType;
	}

	@OneToOne
	@JoinColumn(name = "REF_CONTEXT")
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REF_ORGANIZATION")
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REF_ROLES")
	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

}
