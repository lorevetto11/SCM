package com.gpi.scm.interfaces;

import javax.ejb.Remote;

@Remote
public interface TestProxyRemote extends UserLocal {
	
	public static final String JNDI_REMOTE_FIRST_PART = "ejb:SCM/SCMEjb/";

	public static final String JNDI_NAME = JNDI_REMOTE_FIRST_PART + "TestProxyBean!" + TestProxyRemote.class.getName();

}
