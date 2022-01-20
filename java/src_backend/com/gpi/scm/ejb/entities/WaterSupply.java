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
@Table(name="PRP_WATERSUPPLIES",uniqueConstraints = {@UniqueConstraint(columnNames = {"ref_shape"})})
@NamedQueries({ 
	@NamedQuery(name = WaterSupply.NQ_WATERSUPPLY_BY_ID, query = "Select u from WaterSupply u where u.id = :idWaterSupply"),
	@NamedQuery(name = WaterSupply.NQ_WATERSUPPLY_BY_CONTEXT_ID, query = "Select u from WaterSupply u where u.context.id = :idContext"),
	@NamedQuery(name = WaterSupply.NQ_WATERSUPPLYS_IN_ORGANIZATIONS, query = "Select u from WaterSupply u where u.organization.id IN :organizations")
	})

public class WaterSupply extends BasePrerequisite {

	private static final long serialVersionUID = 1L;
	public static final String NQ_WATERSUPPLY_BY_ID = "waterSupply.getWaterSupplyById";
	public static final String NQ_WATERSUPPLY_BY_CONTEXT_ID = "waterSupply.getWaterSupplyByContextId";
	public static final String NQ_WATERSUPPLYS_IN_ORGANIZATIONS = "waterSupply.WaterSupplyInOrganizations";
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_prp_watersupplies")
	@SequenceGenerator(name = "seq_prp_watersupplies", sequenceName = "seq_prp_watersupplies", allocationSize = 1)
	public Long getId()
	{
		return id;
	}

}
