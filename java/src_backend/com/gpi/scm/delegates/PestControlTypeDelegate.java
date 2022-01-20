package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.PestControlTypeDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.PestControlTypeLocal;

public class PestControlTypeDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(PestControlDelegate.class);

	private static PestControlTypeLocal getBean() throws NamingException {
		return (PestControlTypeLocal) getContext().lookup(PestControlTypeLocal.JNDI_NAME);
	}

	public static List<PestControlTypeDto> findPestControlTypes(List<Long> organizations) {
		List<PestControlTypeDto> obj = null;
		try {
			obj = getBean().findTypes(organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static PestControlTypeDto savePestControlType(PestControlTypeDto pestcontrolType) {
		PestControlTypeDto obj = null;
		try {
			obj = getBean().saveType(pestcontrolType);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static PestControlTypeDto editPestControlType(PestControlTypeDto pestcontrolType) {
		PestControlTypeDto obj = null;
		try {
			obj = getBean().editType(pestcontrolType);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static boolean deleteById(long id) {
		boolean obj = false;
		try {
			obj = getBean().deleteType(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

}
