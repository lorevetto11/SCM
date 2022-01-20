package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.SystemCheckDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface SystemCheckLocal {

	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "SystemCheckBean" + "!" + SystemCheckLocal.class.getName();

	public SystemCheckDto saveCheck(SystemCheckDto toSave) throws BusinessException;

	public SystemCheckDto editCheck(SystemCheckDto toSave)throws BusinessException;

	public boolean deleteCheck(long id)throws BusinessException;

	public List<SystemCheckDto> findChecks(Long organizationId)throws BusinessException;

}
