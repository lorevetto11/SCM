package com.gpi.scm.ejb.entities;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="ROL_GRANTS")
@NamedQueries({
	@NamedQuery(name = Grant.NQ_GRANTS, query = "Select r from Grant r"),
	@NamedQuery(name = Grant.NQ_GRANTS_BY_ID, query = "Select r from Grant r where r.id in :grants")


})
public class Grant extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NQ_GRANTS = "userGrant.getGrants";
	public static final String NQ_GRANTS_BY_ID = "userGrant.getGrantsById";

	

	private String name;
	
	@ManyToMany(mappedBy="grants",targetEntity=UserProfile.class)
	private List<UserProfile> profiles ;

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

}
