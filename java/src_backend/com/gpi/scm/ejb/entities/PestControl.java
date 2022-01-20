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
@Table(name="PRP_PESTCONTROLS",uniqueConstraints = {@UniqueConstraint(columnNames = {"ref_shape"})})
@NamedQueries({ 
	@NamedQuery(name = PestControl.NQ_PESTCONTROL_BY_ID, query = "Select u from PestControl u where u.id = :idPestControl"),
	@NamedQuery(name = PestControl.NQ_PESTCONTROL_BY_CONTEXT_ID, query = "Select u from PestControl u where u.context.id = :idContext"),
	@NamedQuery(name = PestControl.NQ_PESTCONTROLS_IN_ORGANIZATIONS, query = "Select u from PestControl u where u.organization.id IN :organizations")
	})

public class PestControl extends BasePrerequisite {

	private static final long serialVersionUID = 1L;
	public static final String NQ_PESTCONTROL_BY_ID = "pestControl.getPestControlById";
	public static final String NQ_PESTCONTROL_BY_CONTEXT_ID = "PestControl.getPestControlByContextId";
	public static final String NQ_PESTCONTROLS_IN_ORGANIZATIONS = "pestControl.PestControlInOrganizations";
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_prp_pestcontrol")
	@SequenceGenerator(name = "seq_prp_pestcontrol", sequenceName = "seq_prp_pestcontrol", allocationSize = 1)
	public Long getId()
	{
		return id;
	}

}
