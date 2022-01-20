package com.gpi.scm.ejb.sessions;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.gpi.scm.bl.PersistenceParameters;

public abstract class GenericBean {

	@PersistenceContext(unitName = PersistenceParameters.PERSISTENCE_CONTEXT)
	protected EntityManager entityManager;

}
