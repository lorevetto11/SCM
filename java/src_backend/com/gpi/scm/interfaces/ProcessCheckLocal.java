package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.ProcessCheckDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface ProcessCheckLocal {

	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "ProcessCheckBean" + "!" + ProcessCheckLocal.class.getName();

	public ProcessCheckDto saveCheck(ProcessCheckDto toSave) throws BusinessException;

	public ProcessCheckDto editCheck(ProcessCheckDto toSave) throws BusinessException;

	public boolean deleteCheck(long id)throws BusinessException;

	public List<ProcessCheckDto> findChecks(Long organizationId)throws BusinessException;

}
