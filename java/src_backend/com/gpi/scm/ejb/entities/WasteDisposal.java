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
@Table(name="PRP_WASTEDISPOSALS",uniqueConstraints = {@UniqueConstraint(columnNames = {"ref_shape"})})
@NamedQueries({ 
	@NamedQuery(name = WasteDisposal.NQ_WASTEDISPOSAL_BY_ID, query = "Select u from WasteDisposal u where u.id = :idWasteDisposal"),
	@NamedQuery(name = WasteDisposal.NQ_WASTEDISPOSAL_BY_CONTEXT_ID, query = "Select u from WasteDisposal u where u.context.id = :idContext"),
	@NamedQuery(name = WasteDisposal.NQ_WASTEDISPOSALS_IN_ORGANIZATIONS, query = "Select u from WasteDisposal u where u.organization.id IN :organizations")
	})
public class WasteDisposal extends BasePrerequisite {

	private static final long serialVersionUID = 1L;
	public static final String NQ_WASTEDISPOSAL_BY_ID = "wasteDisposal.getWasteDisposalById";
	public static final String NQ_WASTEDISPOSAL_BY_CONTEXT_ID = "waterSupply.getWasteDisposalByContextId";
	public static final String NQ_WASTEDISPOSALS_IN_ORGANIZATIONS = "wasteDisposal.wasteDisposalsInOrganizations";
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_prp_wastedisposals")
	@SequenceGenerator(name = "seq_prp_wastedisposals", sequenceName = "seq_prp_wastedisposals", allocationSize = 1)
	public Long getId()
	{
		return id;
	}

}
