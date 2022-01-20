package com.gpi.scm.generic.dtos;

import javax.validation.constraints.NotNull;


public class EquipmentPrerequisiteDto extends BasePrerequisiteDto {

	
	private static final long serialVersionUID = 1L;
	private EquipmentDto equipment;
	private EquipmentTypeDto equipmentType;
	
	@NotNull
	public EquipmentDto getEquipment() {
		return equipment;
	}
	public void setEquipment(EquipmentDto equipment) {
		this.equipment = equipment;
	}
	@NotNull
	public EquipmentTypeDto getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(EquipmentTypeDto equipmentType) {
		this.equipmentType = equipmentType;
	}

	}
	

