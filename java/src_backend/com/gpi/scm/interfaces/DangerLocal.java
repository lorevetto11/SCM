package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.DangerDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface DangerLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "DangerBean" + "!" + DangerLocal.class.getName();

	public boolean deleteDanger(long id) throws BusinessException;

	public DangerDto editDanger(DangerDto danger)throws BusinessException;

	public DangerDto newDanger(DangerDto danger)throws BusinessException;

	public List<DangerDto> findDanger(Long organizations)throws BusinessException;

	public DangerDto findByContextId(long id,List<Long> organizations)throws BusinessException;
	
}
