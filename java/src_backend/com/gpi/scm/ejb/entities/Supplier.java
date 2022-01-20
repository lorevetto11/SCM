package com.gpi.scm.ejb.entities;

import javax.persistence.*;

@Entity
@Table(name="MTR_SUPPLIERS")
@NamedQuery(name = Supplier.NQ_SUPPLIER_IN_ORGANIZATIONS, query = "Select r from Supplier r where r.organization.id IN :organizations ")

public class Supplier extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NQ_SUPPLIER_IN_ORGANIZATIONS = "supplier.findByOrg";
	private String name;
	private String description;
	private String vatNumber;
	private String contact;
	private String address;
	private String email;
	private String phone;
	private Organization organization;
	private Context context;
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_mtr_suppliers")
	@SequenceGenerator(name = "seq_mtr_suppliers", sequenceName = "seq_mtr_suppliers", allocationSize = 1)
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
	@Column(name="VAT_NUMBER")
	public String getVatNumber() {
		return vatNumber;
	}
	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}
	@Column(name="CONTACT")
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	@Column(name="ADDRESS")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name="EMAIL")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name="PHONE")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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

}
