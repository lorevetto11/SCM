package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.AnalysisValueDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.AnalysisValueLocal;

public class AnalysisValueDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(AnalysisValueDelegate.class);


	private static AnalysisValueLocal getBean() throws NamingException {
		return (AnalysisValueLocal) getContext().lookup(AnalysisValueLocal.JNDI_NAME);}
	
	public static List<AnalysisValueDto> findAll(Long analysisParameterId) {
		List<AnalysisValueDto> obj = null;
		try {
			obj = getBean().findAnalisisValue(analysisParameterId);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}


	public static boolean delete(long id) {
		try {
			return getBean().deleteAnalisisValue(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return false;		
	}

	public static AnalysisValueDto edit(AnalysisValueDto value) {
		AnalysisValueDto obj = null;
		try {
			obj = getBean().editAnalisisValue(value);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static AnalysisValueDto save(AnalysisValueDto value) {
		AnalysisValueDto obj = null;
		try {
			obj = getBean().newAnalisisValue(value);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

}
