package com.gpi.scm.ejb.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CTX_CONTEXTS")
public class Context extends GenericEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String className;
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ctx_contexts")
	@SequenceGenerator(name = "seq_ctx_contexts", sequenceName = "seq_ctx_contexts", allocationSize = 1)
	public Long getId()
	{
		return id;
	}
	@Column(name="CLASS_NAME")
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	

}
