package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.ProcessCheckDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.ProcessCheckLocal;

public class ProcessCheckDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(ProcessCheckDelegate.class);

	private static ProcessCheckLocal getBean() throws NamingException {
		return (ProcessCheckLocal) getContext().lookup(ProcessCheckLocal.JNDI_NAME);
	}

	public static List<ProcessCheckDto> findProcessChecks(Long organizationId) {
		List<ProcessCheckDto> obj = null;
		try {
			obj = getBean().findChecks(organizationId);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static ProcessCheckDto saveProcessCheck(ProcessCheckDto processCheck) {
		ProcessCheckDto obj = null;
		try {
			obj = getBean().saveCheck(processCheck);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static ProcessCheckDto editProcessCheck(ProcessCheckDto processCheck) {
		ProcessCheckDto obj = null;
		try {
			obj = getBean().editCheck(processCheck);
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
