package com.gpi.scm.generic.dtos;

import com.gpi.scm.generic.utils.CommonEnums.materialType;

public class MaterialDto extends GenericDto {
	
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private materialType type;
	private OrganizationDto organization;
	private ContextDto context ;
	private SupplierDto supplier;
	private PrerequisiteTypeDto prerequisiteType;
	private MaterialCategoryDto materialCategory;
	
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
	public OrganizationDto getOrganization() {
		return organization;
	}
	public void setOrganization(OrganizationDto organization) {
		this.organization = organization;
	}
	public ContextDto getContext() {
		return context;
	}
	public void setContext(ContextDto context) {
		this.context = context;
	}
	public SupplierDto getSupplier() {
		return supplier;
	}
	public void setSupplier(SupplierDto supplier) {
		this.supplier = supplier;
	}
	public PrerequisiteTypeDto getPrerequisiteType() {
		return prerequisiteType;
	}
	public void setPrerequisiteType(PrerequisiteTypeDto prerequisiteType) {
		this.prerequisiteType = prerequisiteType;
	}
	public MaterialCategoryDto getMaterialCategory() {
		return materialCategory;
	}
	public void setMaterialCategory(MaterialCategoryDto materialCategory) {
		this.materialCategory = materialCategory;
	}
}
