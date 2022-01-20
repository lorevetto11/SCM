package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.ProcessCheckPlanningDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.ProcessCheckPlanningLocal;

public class ProcessCheckPlanningDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(ProcessCheckPlanningDelegate.class);

	private static ProcessCheckPlanningLocal getBean() throws NamingException {
		return (ProcessCheckPlanningLocal) getContext().lookup(ProcessCheckPlanningLocal.JNDI_NAME);
	}

	public static List<ProcessCheckPlanningDto> findProcessCheckPlannings(Long organizationId) {
		List<ProcessCheckPlanningDto> obj = null;
		try {
			obj = getBean().findPlanning(organizationId);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static ProcessCheckPlanningDto saveProcessCheckPlanning(ProcessCheckPlanningDto processCheckPlanning) {
		ProcessCheckPlanningDto obj = null;
		try {
			obj = getBean().savePlanning(processCheckPlanning);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static ProcessCheckPlanningDto editProcessCheckPlanning(ProcessCheckPlanningDto processCheckPlanning) {
		ProcessCheckPlanningDto obj = null;
		try {
			obj = getBean().editPlanning(processCheckPlanning);
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
