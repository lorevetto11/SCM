package com.gpi.scm.constants;

public class JndiNamingConstants {

	private static final String JNDI_APPLICATION_NAME = "SCM";
	private static final String JNDI_MODULE_NAME = "SCMEjb";
	public static final String JNDI_LOCAL_FIRST_PART = "java:global/" + JNDI_APPLICATION_NAME +"/"+ JNDI_MODULE_NAME + "/";
	public static final String JNDI_REMOTE_FIRST_PART = "ejb:" + JNDI_APPLICATION_NAME +"/"+ JNDI_MODULE_NAME + "/";

}
