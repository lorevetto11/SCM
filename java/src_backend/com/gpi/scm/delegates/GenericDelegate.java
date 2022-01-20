package com.gpi.scm.delegates;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.gpi.scm.generic.utils.StringUtils;

public class GenericDelegate {

	private static Context context = null;
	private static Context remoteContext = null;
	
	private static boolean lookupLocal = true;
	public static boolean isLookupLocal() {
		return lookupLocal;
	}
	static {
		String tmp = System.getProperty("scm.lookupLocal");
		if (StringUtils.isEmpty(tmp) == false)
			lookupLocal = Boolean.parseBoolean(tmp); 
	}
	
	protected static Context getContext() {
		try {
			if (context == null){
				Properties properties = new Properties();
				properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.as.naming.InitialContextFactory");
				context = new InitialContext(properties);
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return context;
	}

	protected static void setContext(Context context) {
		GenericDelegate.context = context;
	}
	
	protected static Context getRemoteContext() {
		try {
			if (remoteContext == null){
				Properties jndiProperties = new Properties();	
				jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
				jndiProperties.put("jboss.naming.client.ejb.context", true);
				remoteContext = new InitialContext(jndiProperties);
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return remoteContext;
	}
	protected static void setRemoteContext(Context remoteContext) {
		GenericDelegate.remoteContext = remoteContext;
	}
}
