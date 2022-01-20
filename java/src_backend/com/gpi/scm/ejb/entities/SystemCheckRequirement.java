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
@Table(name="CKS_SYSTEM_CHECK_REQUIREMENTS")
@NamedQueries({
	@NamedQuery(name = SystemCheckRequirement.NQ_SYSTEMCHECKS_REQUIREMENT_BY_SYSCHECK, query = "Select o from SystemCheckRequirement o where o.systemCheck.id = :systemCheckId")

})
public class SystemCheckRequirement extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NQ_SYSTEMCHECKS_REQUIREMENT_BY_SYSCHECK="systemCheckRequirement.findBySystemCheck";
	private String name;
	private String description;
	private SystemCheck systemcheck;
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cks_systemchecks_requirements")
	@SequenceGenerator(name = "seq_cks_systemchecks_requirements", sequenceName = "seq_cks_systemchecks_requirements", allocationSize = 1)
	public Long getId()
	{
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
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REF_SYSTEM_CHECK")
	public SystemCheck getSystemCheck() {
		return systemcheck;
	}
	public void setSystemCheck(SystemCheck systemcheck) {
		this.systemcheck = systemcheck;
	}
	

}
