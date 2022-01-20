package com.gpi.scm.interfaces;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.ParameterDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface ParameterLocal {
	
	
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART 
			+ "ParameterBean" + "!" + ParameterLocal.class.getName();
	
	ParameterDto getParameter(String key, Long orgId) throws BusinessException;
	ParameterDto getParameterByOrganizationCode(String key, String orgCode) throws BusinessException;

}
