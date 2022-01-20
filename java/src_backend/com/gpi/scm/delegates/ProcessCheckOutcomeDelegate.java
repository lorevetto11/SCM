package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.ProcessCheckOutcomeDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.ProcessCheckOutcomeLocal;

public class ProcessCheckOutcomeDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(ProcessCheckOutcomeDelegate.class);

	private static ProcessCheckOutcomeLocal getBean() throws NamingException {
		return (ProcessCheckOutcomeLocal) getContext().lookup(ProcessCheckOutcomeLocal.JNDI_NAME);
	}

	public static List<ProcessCheckOutcomeDto> findProcessCheckOutcomes(Long planningId, Long processCheckId) {
		List<ProcessCheckOutcomeDto> obj = null;
		try {
			obj = getBean().findOutcome(planningId,processCheckId);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static ProcessCheckOutcomeDto saveProcessCheckOutcome(ProcessCheckOutcomeDto processCheckOutcome) {
		ProcessCheckOutcomeDto obj = null;
		try {
			obj = getBean().saveOutcome(processCheckOutcome);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static ProcessCheckOutcomeDto editProcessCheckOutcome(ProcessCheckOutcomeDto processCheckOutcome) {
		ProcessCheckOutcomeDto obj = null;
		try {
			obj = getBean().editOutcome(processCheckOutcome);
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
