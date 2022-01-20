package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.AnalysisParameterDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.AnalysisParameterLocal;

public class AnalysisParameterDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(AnalysisParameterDelegate.class);


	private static AnalysisParameterLocal getBean() throws NamingException {
		return (AnalysisParameterLocal) getContext().lookup(AnalysisParameterLocal.JNDI_NAME);}
	
	public static List<AnalysisParameterDto> findAll(Long organizations) {
		List<AnalysisParameterDto> obj = null;
		try {
			obj = getBean().findAnalisisParameter(organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}


	public static boolean delete(long id) {
		try {
			return getBean().deleteAnalisisParameter(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return false;		
	}

	public static AnalysisParameterDto edit(AnalysisParameterDto parameter) {
		AnalysisParameterDto obj = null;
		try {
			obj = getBean().editAnalisisParameter(parameter);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static AnalysisParameterDto save(AnalysisParameterDto parameter) {
		AnalysisParameterDto obj = null;
		try {
			obj = getBean().newAnalisisParameter(parameter);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

}
