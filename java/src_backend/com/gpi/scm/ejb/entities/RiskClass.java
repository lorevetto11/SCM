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
@Table(name="PRP_RISKCLASSES")
@NamedQueries({@NamedQuery(name = RiskClass.NQ_CLASSES, query = "Select u from RiskClass u where u.organization.id=:idOrganization"),
		 @NamedQuery(name = RiskClass.NQ_CLASS_BY_ID, query = "Select u from RiskClass u where u.id= :idClass ")})
public class RiskClass extends GenericEntity {

	
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private String color;
	private Long order;
	private Organization organization;
	public static final String NQ_CLASSES = "riskClass.findAllClasses";
	public static final String NQ_CLASS_BY_ID = "riskClass.findById";


	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_prq_riskclasses")
	@SequenceGenerator(name = "seq_prq_riskclasses", sequenceName = "seq_prq_riskclasses", allocationSize = 1)
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
	@Column(name="COLOR")
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	@Column(name="\"order\"")
	public Long getOrder() {
		return order;
	}
	public void setOrder(Long order) {
		this.order = order;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REF_ORGANIZATION")
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}


}
