package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.RiskClassDto;
import com.gpi.scm.interfaces.RiskClassLocal;

public class RiskClassDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(RiskClassDelegate.class);

	private static RiskClassLocal getBean() throws NamingException {
		return (RiskClassLocal) getContext().lookup(RiskClassLocal.JNDI_NAME);
	}

	public static List<RiskClassDto> findClasses(Long organizationId) {
		
		try {
			return getBean().findClasses(organizationId);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	public static RiskClassDto saveClass(RiskClassDto toSave) {
		try {
			return getBean().saveClass(toSave);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	public static Object editClass(RiskClassDto toSave) {
		try {
			return getBean().editClass(toSave);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	public static boolean delete(long id) {
		try {
			return  getBean().deleteClass(id);
		} catch (Exception e) {
			logger.error(e);
		}
		return false;
	}



}
