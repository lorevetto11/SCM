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
@Table(name="PRP_AIRCONDITIONING",uniqueConstraints = {@UniqueConstraint(columnNames = {"ref_shape"})})
@NamedQueries({ 
	@NamedQuery(name = AirConditioning.NQ_AIRCONDITIONING_BY_ID, query = "Select u from AirConditioning u where u.id = :idAirConditioning"),
	@NamedQuery(name = AirConditioning.NQ_AIRCONDITIONING_BY_CONTEXT_ID, query = "Select u from AirConditioning u where u.context.id = :idContext"),
	@NamedQuery(name = AirConditioning.NQ_AIRCONDITIONINGS_IN_ORGANIZATIONS, query = "Select u from AirConditioning u where u.organization.id IN :organizations")
	})

public class AirConditioning extends BasePrerequisite {

	private static final long serialVersionUID = 1L;
	public static final String NQ_AIRCONDITIONING_BY_ID = "airConditioning.getAirConditioningById";
	public static final String NQ_AIRCONDITIONING_BY_CONTEXT_ID = "airConditioning.getAirConditioningByContextId";
	public static final String NQ_AIRCONDITIONINGS_IN_ORGANIZATIONS = "airConditioning.AirConditioningInOrganizations";
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_prp_airconditioning")
	@SequenceGenerator(name = "seq_prp_airconditioning", sequenceName = "seq_prp_airconditioning", allocationSize = 1)
	public Long getId()
	{
		return id;
	}

}
