package com.gpi.scm.ejb.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="PRP_PREREQUISITE_TYPES")
@NamedQuery(name = PrerequisiteType.NQ_PRPTYPES, query = "Select u from PrerequisiteType u")
public class PrerequisiteType extends GenericEntity {

	/**
	 * 
	 */
	public static final String NQ_PRPTYPES="prerequisiteType.allPrerequisiteTypes";
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	
	@Id
	@Column(name="ID")
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
	
	
	

}
