package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.SystemCheckOutcomeDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.SystemCheckOutcomeLocal;

public class SystemCheckOutcomeDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(SystemCheckOutcomeDelegate.class);

	private static SystemCheckOutcomeLocal getBean() throws NamingException {
		return (SystemCheckOutcomeLocal) getContext().lookup(SystemCheckOutcomeLocal.JNDI_NAME);
	}

	public static List<SystemCheckOutcomeDto> findSystemCheckOutcomes(Long requirementId) {
		List<SystemCheckOutcomeDto> obj = null;
		try {
			obj = getBean().findOutcome(requirementId);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static SystemCheckOutcomeDto saveSystemCheckOutcome(SystemCheckOutcomeDto systemCheckOutcome) {
		SystemCheckOutcomeDto obj = null;
		try {
			obj = getBean().saveOutcome(systemCheckOutcome);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static SystemCheckOutcomeDto editSystemCheckOutcome(SystemCheckOutcomeDto systemCheckOutcome) {
		SystemCheckOutcomeDto obj = null;
		try {
			obj = getBean().editOutcome(systemCheckOutcome);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static boolean deleteById(long id) {
		boolean obj = false;
		try {
			obj = getBean().deleteOutcome(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

}
