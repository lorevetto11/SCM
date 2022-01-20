package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.SystemCheckPlanningDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.SystemCheckPlanningLocal;

public class SystemCheckPlanningDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(SystemCheckPlanningDelegate.class);

	private static SystemCheckPlanningLocal getBean() throws NamingException {
		return (SystemCheckPlanningLocal) getContext().lookup(SystemCheckPlanningLocal.JNDI_NAME);
	}

	public static List<SystemCheckPlanningDto> findSystemCheckPlannings(Long organizationId) {
		List<SystemCheckPlanningDto> obj = null;
		try {
			obj = getBean().findPlanning(organizationId);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static SystemCheckPlanningDto saveSystemCheckPlanning(SystemCheckPlanningDto sysCheckPlanning) {
		SystemCheckPlanningDto obj = null;
		try {
			obj = getBean().savePlanning(sysCheckPlanning);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static SystemCheckPlanningDto editSystemCheckPlanning(SystemCheckPlanningDto sysCheckPlanning) {
		SystemCheckPlanningDto obj = null;
		try {
			obj = getBean().editPlanning(sysCheckPlanning);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static boolean deleteById(long id) {
		boolean obj = false;
		try {
			obj = getBean().deletePlanning(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

}
