package com.gpi.scm.ejb.entities;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="EQP_EQUIPMENTS")
@NamedQueries({@NamedQuery(name = Equipment.NQ_EQUIPMENT, query = "Select o from Equipment o where o.organization.id = :organizations"),
	@NamedQuery(name = Equipment.NQ_EQUIPMENT_BY_ID, query = "Select o from Equipment o inner join o.equipmentType p where p.organization.id IN :organizations and o.id=:IdEquipment ")

})
public class Equipment extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NQ_EQUIPMENT="equipment.findAll";
	public static final String NQ_EQUIPMENT_BY_ID="equipment.findById";
	private String name;
	private String description;
	private String maintainer;
	private Date startupDate;
	private Context context;
	private Frequency frequency;
	private EquipmentType equipmentType;
	private Organization organization;
	private Supplier supplier;
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_eqp_equipment")
	@SequenceGenerator(name = "seq_eqp_equipment", sequenceName = "seq_eqp_equipment", allocationSize = 1)
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
    @JoinColumn(name="REF_TYPE")
	public EquipmentType getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(EquipmentType equipmentType) {
		this.equipmentType = equipmentType;
	}

	@Column(name="MAINTAINER")
	public String getMaintainer() {
		return maintainer;
	}
	public void setMaintainer(String maintainer) {
		this.maintainer = maintainer;
	}
	@OneToOne
	@JoinColumn(name="REF_CONTEXT")
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	@OneToOne
	@JoinColumn(name="REF_FREQUENCY")
	public Frequency getFrequency() {
		return frequency;
	}
	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="STARTUP_DATE")
	public Date getStartupDate() {
		return startupDate;
	}
	public void setStartupDate(Date startupDate) {
		this.startupDate = startupDate;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REF_ORGANIZATION")
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REF_SUPPLIER")
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
	

	
	

}
