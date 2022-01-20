package com.gpi.scm.ejb.entities;

import javax.persistence.*;

import com.gpi.scm.generic.utils.CommonEnums.materialType;

@Entity
@Table(name = "MTR_MATERIAL_CATEGORIES")
@NamedQuery(name = MaterialCategory.NQ_MATERIALCATEGORIES, query = "Select r from MaterialCategory r ")

public class MaterialCategory extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NQ_MATERIALCATEGORIES = "materialCategory.findAll";
	private String name;
	private String description;
	private materialType type;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_mtr_material_categories")
	@SequenceGenerator(name = "seq_mtr_material_categories", sequenceName = "seq_mtr_material_categories", allocationSize = 1)
	public Long getId() {
		return id;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE")
	public materialType getType() {
		return type;
	}

	public void setType(materialType type) {
		this.type = type;
	}

}
