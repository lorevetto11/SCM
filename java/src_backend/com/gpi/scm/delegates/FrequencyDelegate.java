package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.FrequencyDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.FrequencyLocal;

public class FrequencyDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(FrequencyDelegate.class);


	private static FrequencyLocal getBean() throws NamingException {
		return (FrequencyLocal) getContext().lookup(FrequencyLocal.JNDI_NAME);}
	
	public static List<FrequencyDto> findAll(List<Long> organizations) {
		List<FrequencyDto> obj = null;
		try {
			obj = getBean().findFrequency(organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	
	public static boolean delete(long id) {
		try {
			return getBean().deleteFrequency(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return false;		
	}

	public static FrequencyDto edit(FrequencyDto frequency) {
		FrequencyDto obj = null;
		try {
			obj = getBean().editFrequency(frequency);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static FrequencyDto save(FrequencyDto frequency) {
		FrequencyDto obj = null;
		try {
			obj = getBean().newFrequency(frequency);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

}
