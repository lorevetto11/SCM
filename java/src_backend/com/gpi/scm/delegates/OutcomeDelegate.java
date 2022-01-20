package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.OutcomeDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.OutcomeLocal;

public class OutcomeDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(OutcomeDelegate.class);


	private static OutcomeLocal getBean() throws NamingException {
		return (OutcomeLocal) getContext().lookup(OutcomeLocal.JNDI_NAME);}
	

	public static OutcomeDto findById(long id,List<Long> organizations) {
		OutcomeDto obj = null;
		try {
			obj = getBean().findOutcomeByMonitorId(id,organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static boolean delete(long id ) {
		try {
			return getBean().deleteOutcome(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return false;		
	}

	public static OutcomeDto edit(OutcomeDto outcome) {
		OutcomeDto obj = null;
		try {
			obj = getBean().editOutcome(outcome);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static OutcomeDto save(OutcomeDto outcome) {
		OutcomeDto obj = null;
		try {
			obj = getBean().newOutcome(outcome);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

}
