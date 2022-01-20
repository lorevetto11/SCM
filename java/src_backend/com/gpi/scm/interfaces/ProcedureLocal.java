package com.gpi.scm.interfaces;


import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.ProcedureDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface ProcedureLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "ProcedureBean" + "!" + ProcedureLocal.class.getName();

	public ProcedureDto editProcedure(ProcedureDto procedure) throws BusinessException;

	public ProcedureDto newProcedure(ProcedureDto procedure)throws BusinessException;

	public boolean deleteProcedure(long id)throws BusinessException;

	public ProcedureDto findProcedureById(long id, List<Long> organizations)throws BusinessException;

	public List<ProcedureDto> findProcedure(List<Long> organizations)throws BusinessException;
	



}
