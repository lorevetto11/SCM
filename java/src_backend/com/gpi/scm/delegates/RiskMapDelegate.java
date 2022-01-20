package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.RiskMapDto;
import com.gpi.scm.interfaces.RiskMapLocal;

public class RiskMapDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(RiskMapDelegate.class);

	private static RiskMapLocal getBean() throws NamingException {
		return (RiskMapLocal) getContext().lookup(RiskMapLocal.JNDI_NAME);
	}

	public static List<RiskMapDto> findMaps(Long elementId,Long dangerId,Long organizationId) {
		
		try {
			return getBean().findMaps(elementId,dangerId,organizationId);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	public static RiskMapDto saveMap(RiskMapDto toSave) {
		try {
			return getBean().saveMap(toSave);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	public static Object editMap(RiskMapDto toSave) {
		try {
			return getBean().editMap(toSave);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	public static boolean delete(long id) {
		try {
			return  getBean().deleteMap(id);
		} catch (Exception e) {
			logger.error(e);
		}
		return false;
	}



}
