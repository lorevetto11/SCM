package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.MonitoringDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.MonitoringLocal;

public class MonitoringDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(MonitoringDelegate.class);


	private static MonitoringLocal getBean() throws NamingException {
		return (MonitoringLocal) getContext().lookup(MonitoringLocal.JNDI_NAME);}
	
	public static List<MonitoringDto> findAll(Long organizations) {
		List<MonitoringDto> obj = null;
		try {
			obj = getBean().findMonitoring(organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static List<MonitoringDto> findByRole(Long organizations, Long userRoleId) {
		
		List<MonitoringDto> obj = null;
		try {
			obj = getBean().findMonitoringByUserRole(organizations,userRoleId);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static boolean delete(long id) {
		try {
			return getBean().deleteMonitoring(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return false;		
	}

	public static MonitoringDto edit(MonitoringDto monitoring) {
		MonitoringDto obj = null;
		try {
			obj = getBean().editMonitoring(monitoring);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static MonitoringDto save(MonitoringDto monitoring) {
		MonitoringDto obj = null;
		try {
			obj = getBean().newMonitoring(monitoring);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

}
