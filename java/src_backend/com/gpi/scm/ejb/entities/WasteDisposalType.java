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
@Table(name="PRP_WASTEDISPOSALTYPES")
@NamedQueries({@NamedQuery(name = WasteDisposalType.NQ_WASTEDISPOSAL_TYPES, query = "Select o from WasteDisposalType o where o.organization.id IN :organizations"),
	@NamedQuery(name = WasteDisposalType.NQ_WASTEDISPOSAL_TYPE_BY_ID, query = "Select o from WasteDisposalType o where o.id = :idOrg")

})
public class WasteDisposalType extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NQ_WASTEDISPOSAL_TYPES="wastedisposalType.findAll";
	public static final String NQ_WASTEDISPOSAL_TYPE_BY_ID="wastedisposalType.findById";
	private String name;
	private String description;
	private Organization organization;
	private Shape shape;
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_prp_wastedisposalType")
	@SequenceGenerator(name = "seq_prp_wastedisposalType", sequenceName = "seq_prp_wastedisposalType", allocationSize = 1)
	public Long getId()
	{
		return id;
	}
	@Column(name="NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	@JoinColumn(name="REF_SHAPE")
	public Shape getShape() {
		return shape;
	}
	public void setShape(Shape shape) {
		this.shape = shape;
	}
	
	

}
