package com.gpi.scm.generic.dtos;

import com.gpi.scm.generic.utils.CommonEnums.materialType;

public class MaterialCategoryDto extends GenericDto {

	private String name;
	private String description;
	private materialType type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public materialType getType() {
		return type;
	}

	public void setType(materialType type) {
		this.type = type;
	}
}
