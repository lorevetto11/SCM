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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "PARAMETERS")
@NamedQueries({
	/*@NamedQuery(name = Parameter.NQ_GET_ORG_PARAMETER_BY_KEY ,
			query="select p from Parameter p where p.key = :key and p.refOrganization.id = :idOrganization "),
	@NamedQuery(name = Parameter.NQ_GET_ORG_PARAMETER_BY_KEY_AND_ORG_CODE ,
	query="select p from Parameter p where p.key = :key and p.refOrganization.code = :orgCode ")*/
})
public class Parameter extends GenericEntity {

	private static final long serialVersionUID = -5005954401956462978L;
	
	public static final String NQ_GET_ORG_PARAMETER_BY_KEY="Parameter.GetOrgParameterByKey";
	public static final String NQ_GET_ORG_PARAMETER_BY_KEY_AND_ORG_CODE="Parameter.GetOrgParameterByKeyAndOrgCode";
	
	public static final String NQ_PARAM_KEY="key";
	public static final String NQ_PARAM_ID_ORGANIZATION="idOrganization";
	public static final String NQ_PARAM_ORGANIZATION_CODE="orgCode";
	

	private String key;
	private String value;
	private Organization refOrganization;
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SEQ_PARAMETERS")
	@SequenceGenerator(name = "GEN_SEQ_PARAMETERS", sequenceName = "SEQ_PARAMETERS", allocationSize = 1)
	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	
	
	@Column(name = "KEY")
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	@Column(name = "VALUE")
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ORGANIZATION")
	public Organization getRefOrganization() {
		return refOrganization;
	}
	public void setRefOrganization(Organization refOrganization) {
		this.refOrganization = refOrganization;
	}
	
	
	
	
}
