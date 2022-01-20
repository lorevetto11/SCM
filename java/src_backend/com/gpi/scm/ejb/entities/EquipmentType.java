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
@Table(name="EQP_EQUIPMENTTYPES")
@NamedQueries({@NamedQuery(name = EquipmentType.NQ_EQUIPMENT_TYPES, query = "Select o from EquipmentType o where o.organization.id IN :organizations"),
	@NamedQuery(name = EquipmentType.NQ_EQUIPMENT_TYPE_BY_ID, query = "Select o from EquipmentType o where o.id = :idOrg")

})
public class EquipmentType extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NQ_EQUIPMENT_TYPES="equipmentType.findAll";
	public static final String NQ_EQUIPMENT_TYPE_BY_ID="equipmentType.findById";
	private String name;
	private String description;
	private Organization organization;
	private Shape shape;
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_eqp_equipmentType")
	@SequenceGenerator(name = "seq_eqp_equipmentType", sequenceName = "seq_eqp_equipmentType", allocationSize = 1)
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
