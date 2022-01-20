package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.SystemCheckRequirementDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.SystemCheckRequirementLocal;

public class SystemCheckRequirementDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(SystemCheckRequirementDelegate.class);

	private static SystemCheckRequirementLocal getBean() throws NamingException {
		return (SystemCheckRequirementLocal) getContext().lookup(SystemCheckRequirementLocal.JNDI_NAME);
	}

	public static List<SystemCheckRequirementDto> findSystemCheckRequirements(Long systemCheckId) {
		List<SystemCheckRequirementDto> obj = null;
		try {
			obj = getBean().findRequirement(systemCheckId);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static SystemCheckRequirementDto saveSystemCheckRequirement(SystemCheckRequirementDto systemCheckRequirement) {
		SystemCheckRequirementDto obj = null;
		try {
			obj = getBean().saveRequirement(systemCheckRequirement);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static SystemCheckRequirementDto editSystemCheckRequirement(SystemCheckRequirementDto systemCheckRequirement) {
		SystemCheckRequirementDto obj = null;
		try {
			obj = getBean().editRequirement(systemCheckRequirement);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static boolean deleteById(long id) {
		boolean obj = false;
		try {
			obj = getBean().deleteRequirement(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

}
