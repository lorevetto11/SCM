package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.PrerequisiteTypeDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.PrerequisiteTypeLocal;

public class PrerequisiteTypeDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(PrerequisiteTypeDelegate.class);


	private static PrerequisiteTypeLocal getBean() throws NamingException {
		return (PrerequisiteTypeLocal) getContext().lookup(PrerequisiteTypeLocal.JNDI_NAME);}
	
	public static List<PrerequisiteTypeDto> findAll() {
		List<PrerequisiteTypeDto> obj = null;
		try {
			obj = getBean().findPrerequisiteType();
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}
}
