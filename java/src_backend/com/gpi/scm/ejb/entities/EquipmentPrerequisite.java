package com.gpi.scm.ejb.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="PRP_EQUIPMENTS",uniqueConstraints = {@UniqueConstraint(columnNames = {"ref_shape"})})
	@NamedQueries({ @NamedQuery(name = EquipmentPrerequisite.NQ_EQUIPMENTPREREQUISITE_BY_ID, query = "Select u from EquipmentPrerequisite u where u.id = :idEquipmentPrerequisite"),
	@NamedQuery(name = EquipmentPrerequisite.NQ_EQUIPMENTPREREQUISITES_BY_CONTEXT_ID, query = "Select u from EquipmentPrerequisite u where u.context.id = :idContext"),
	@NamedQuery(name = EquipmentPrerequisite.NQ_EQUIPMENTPREREQUISITES_IN_ORGANIZATIONS, query = "Select u from EquipmentPrerequisite u where u.organization.id IN :organizations")
	})

public class EquipmentPrerequisite extends BasePrerequisite {

	private static final long serialVersionUID = 1L;
	public static final String NQ_EQUIPMENTPREREQUISITE_BY_ID = "equipmentPrerequisite.getEquipmentPrerequisiteById";
	public static final String NQ_EQUIPMENTPREREQUISITES_BY_CONTEXT_ID = "equipmentPrerequisite.getEquipmentPrerequisiteByContextId";
	public static final String NQ_EQUIPMENTPREREQUISITES_IN_ORGANIZATIONS = "equipmentPrerequisite.EquipmentPrerequisiteInOrganizations";
	private Equipment equipment;
	private EquipmentType equipmentType;
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_prp_equipmentprerequisite")
	@SequenceGenerator(name = "seq_prp_equipmentprerequisite", sequenceName = "seq_prp_equipmentprerequisite", allocationSize = 1)
	public Long getId()
	{
		return id;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REF_EQUIPMENT")
	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REF_TYPE")
	public EquipmentType getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(EquipmentType equipmentType) {
		this.equipmentType = equipmentType;
	}

}
