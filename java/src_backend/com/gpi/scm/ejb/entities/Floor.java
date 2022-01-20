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
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "PRP_FLOORS",
	   uniqueConstraints = {@UniqueConstraint(columnNames = {"ref_context"})})

@NamedQueries({ @NamedQuery(name = Floor.NQ_FLOOR_BY_ID, query = "Select u from Floor u where u.id = :idFloor"),
	@NamedQuery(name = Floor.NQ_FLOORS_IN_ORGANIZATIONS, query = "Select u from Floor u where u.organization.id IN :organizations")
	})
public class Floor extends GenericEntity {

	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private int order;
	private float width;
	private float height;
	private Context context;
	private Organization organization;
	
	public static final String NQ_FLOOR_BY_ID = "floor.getFlootById";
	public static final String NQ_FLOORS_IN_ORGANIZATIONS = "floor.floorsInOrganizations";
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_drw_floors")
	@SequenceGenerator(name = "seq_drw_floors", sequenceName = "seq_drw_floors", allocationSize = 1)
	public Long getId() {
		return this.id;
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
	@Column(name="`order`")
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	@Column(name="WIDTH")
	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}
	@Column(name="HEIGHT")
	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="REF_CONTEXT")
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="REF_ORGANIZATION")
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	

}
