package com.gpi.scm.ejb.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="PRP_CLEANINGS",uniqueConstraints = {@UniqueConstraint(columnNames = {"ref_shape"})})
@NamedQueries({ 
	@NamedQuery(name = Cleaning.NQ_CLEANINGS_BY_ID, query = "Select u from Cleaning u where u.id = :idCleaning"),
	@NamedQuery(name = Cleaning.NQ_CLEANINGS_BY_CONTEXT_ID, query = "Select u from Cleaning u where u.context.id = :idContext"),
	@NamedQuery(name = Cleaning.NQ_CLEANINGS_IN_ORGANIZATIONS, query = "Select u from Cleaning u where u.organization.id IN :organizations")
	})

public class Cleaning extends BasePrerequisite {

	private static final long serialVersionUID = 1L;
	public static final String NQ_CLEANINGS_BY_ID = "cleaning.getCleaningById";
	public static final String NQ_CLEANINGS_BY_CONTEXT_ID = "cleaning.getCleaningByContextId";
	public static final String NQ_CLEANINGS_IN_ORGANIZATIONS = "cleaning.CleaningInOrganizations";
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_prp_cleanings")
	@SequenceGenerator(name = "seq_prp_cleanings", sequenceName = "seq_prp_cleanings", allocationSize = 1)
	public Long getId()
	{
		return id;
	}

}
