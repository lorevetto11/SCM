package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.GrantDto;
import com.gpi.scm.generic.dtos.UserProfileDto;
import com.gpi.scm.interfaces.GrantLocal;

public class GrantDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(GrantDelegate.class);

	private static GrantLocal getBean() throws NamingException {
		return (GrantLocal) getContext().lookup(GrantLocal.JNDI_NAME);
	}

	public static List<GrantDto> findClasses() {
		
		try {
			return getBean().findGrants();
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	public static UserProfileDto setGrants(List<Long> organizations, long id, List<Long> grants) {
		try {
			return getBean().setGrants(organizations,id,grants);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
}
