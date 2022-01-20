package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.SystemCheckDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.SystemCheckLocal;

public class SystemCheckDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(SystemCheckDelegate.class);

	private static SystemCheckLocal getBean() throws NamingException {
		return (SystemCheckLocal) getContext().lookup(SystemCheckLocal.JNDI_NAME);
	}

	public static List<SystemCheckDto> findSystemChecks(Long organizationId) {
		List<SystemCheckDto> obj = null;
		try {
			obj = getBean().findChecks(organizationId);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static SystemCheckDto saveSystemCheck(SystemCheckDto systemCheck) {
		SystemCheckDto obj = null;
		try {
			obj = getBean().saveCheck(systemCheck);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static SystemCheckDto editSystemCheck(SystemCheckDto systemCheck) {
		SystemCheckDto obj = null;
		try {
			obj = getBean().editCheck(systemCheck);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static boolean deleteById(long id) {
		boolean obj = false;
		try {
			obj = getBean().deleteCheck(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

}
