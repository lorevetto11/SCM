package com.gpi.scm.ejb.entities;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="ORG_CERTIFICATIONS")
@NamedQueries({@NamedQuery(name = OrganizationCertification.NQ_ORGANIZATIONCERTIFICATIONS, query = "Select o from OrganizationCertification o where o.organization.id IN :organizations"),
	@NamedQuery(name = OrganizationCertification.NQ_CERTIFICATION_BY_ID, query = "Select o from OrganizationCertification o where o.id = :idOrg")

})
public class OrganizationCertification extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NQ_ORGANIZATIONCERTIFICATIONS = "organizationCertifications.organizationCertifications";
	public static final String NQ_CERTIFICATION_BY_ID = "organizationCertifications.organizationCertificationsIds";
	private String name;
	private String description;
	private Organization organization;
	private Context context;
	private String authority ;
	private Date date;
	private Date expiryDate;
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_org_organizationCertifications")
	@SequenceGenerator(name = "seq_org_organizationCertifications", sequenceName = "seq_org_organizationCertifications", allocationSize = 1)
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
	@ManyToOne(fetch=FetchType.LAZY)
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
	@Column(name="AUTORITY")
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"DATE\"")
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="EXPIRY_DATE")
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

}
