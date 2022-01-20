package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.MonitoringDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface MonitoringLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "MonitoringBean" + "!" + MonitoringLocal.class.getName();

	public List<MonitoringDto> findMonitoring(Long organizations) throws BusinessException;
	
	public List<MonitoringDto> findMonitoringByUserRole(Long organizations, Long userRoleId) throws BusinessException;

	public boolean deleteMonitoring(long id) throws BusinessException;

	public MonitoringDto editMonitoring(MonitoringDto monitoring) throws BusinessException;

	public MonitoringDto newMonitoring(MonitoringDto monitoring) throws BusinessException;


}
