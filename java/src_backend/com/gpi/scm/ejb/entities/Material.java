package com.gpi.scm.ejb.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.BatchSize;

import com.gpi.scm.generic.utils.CommonEnums.materialType;



@Entity
@Table(name="MTR_MATERIALS")
@NamedQuery(name = Material.NQ_MATERIAL_IN_ORGANIZATIONS, query = "Select r from Material r where r.organization.id IN :organizations ")

public class Material extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NQ_MATERIAL_IN_ORGANIZATIONS = "material.findByOrganization";
	private String name;
	private String description;
	private materialType type;

	private Organization organization;
	private Context context ;
	private Supplier supplier;
	private PrerequisiteType prerequisiteType;
	private MaterialCategory materialCategory;
	private List<Danger> dangers = new ArrayList<>();
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_mtr_materials")
	@SequenceGenerator(name = "seq_mtr_materials", sequenceName = "seq_mtr_materials", allocationSize = 1)
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
	
    @Enumerated(EnumType.STRING)
	@Column(name="TYPE")
	public materialType getType() {
		return type;
	}
	public void setType(materialType type) {
		this.type = type;
	}
	@ManyToOne
	@JoinColumn(name="REF_ORGANIZATION")
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	@OneToOne
	@JoinColumn(name="REF_CONTEXT")
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	@ManyToOne
	@JoinColumn(name="REF_SUPPLIER")
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	@ManyToOne
	@JoinColumn(name="REF_PREREQUISITE_TYPE")
	public PrerequisiteType getPrerequisiteType() {
		return prerequisiteType;
	}
	public void setPrerequisiteType(PrerequisiteType prerequisiteType) {
		this.prerequisiteType = prerequisiteType;
	}
	@ManyToOne
	@JoinColumn(name="REF_MATERIAL_CATEGORY")
	public MaterialCategory getMaterialCategory() {
		return materialCategory;
	}
	public void setMaterialCategory(MaterialCategory materialCategory) {
		this.materialCategory = materialCategory;
	}
	
	@ManyToMany(mappedBy="materials",targetEntity=Danger.class,cascade = CascadeType.ALL)
	@BatchSize(size=25)
	public List<Danger> getDangers() {
		return dangers;
	}
	public void setDangers(List<Danger> dangers) {
		this.dangers = dangers;
	}
	
	

}
