package com.gpi.scm.ejb.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="DNG_RISKMAP")
@NamedQueries({
	@NamedQuery(name = RiskMap.NQ_RISK_MAPS_BY_DANGER, query = "Select u from RiskMap u where danger.id=:dangerId"
			+ " and organization.id in :organizations"),
	@NamedQuery(name = RiskMap.NQ_RISK_MAPS_BY_ELEMENT, query = "Select u from RiskMap u where element.id=:elementId"
			+ " and organization.id in :organizations"),
	@NamedQuery(name = RiskMap.NQ_RISK_MAPS_BY_ORGANIZATION, query = "Select u from RiskMap u where organization.id=:organizationId"
			+ " and organization.id in :organizations"),
	@NamedQuery(name = RiskMap.NQ_RISK_MAPS_BY_ELEMENT_DANGER, query = "Select u from RiskMap u where element.id=:elementId and danger.id=:dangerId"
			+ " and organization.id in :organizations")


})

public class RiskMap extends GenericEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NQ_RISK_MAPS_BY_DANGER= "riskMap.findAllByDanger";
	public static final String NQ_RISK_MAPS_BY_ELEMENT = "riskMap.findAllByElement";
	public static final String NQ_RISK_MAPS_BY_ORGANIZATION = "riskMap.findAllByOrganization";
	public static final String NQ_RISK_MAPS_BY_ELEMENT_DANGER = "riskMap.findAllByElementDanger";



	private Long value;
	private Organization organization;
	private Danger danger;
	private FlowElement element;
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dng_riskmap")
	@SequenceGenerator(name = "seq_dng_riskmap", sequenceName = "seq_dng_riskmap", allocationSize = 1)
	public Long getId()
	{
		return this.id;
	}
	@Column(name="VALUE")
	public Long getValue() {
		return value;
	}
	public void setValue(Long value) {
		this.value = value;
	}
	@ManyToOne
	@JoinColumn(name="REF_ORGANIZATION")
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	@ManyToOne
	@JoinColumn(name="REF_DNG_DANGERS")
	public Danger getDanger() {
		return danger;
	}
	public void setDanger(Danger danger) {
		this.danger = danger;
	}
	@ManyToOne
	@JoinColumn(name="REF_FLW_ELEMENTS")
	public FlowElement getElement() {
		return element;
	}
	public void setElement(FlowElement element) {
		this.element = element;
	}
	
	

}
