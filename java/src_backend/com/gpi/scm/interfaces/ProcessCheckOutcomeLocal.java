package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.ProcessCheckOutcomeDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface ProcessCheckOutcomeLocal {

	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "ProcessCheckOutcomeBean" + "!" + ProcessCheckOutcomeLocal.class.getName();

	public ProcessCheckOutcomeDto saveOutcome(ProcessCheckOutcomeDto toSave) throws BusinessException;

	public ProcessCheckOutcomeDto editOutcome(ProcessCheckOutcomeDto toSave)throws BusinessException;

	public boolean deleteOutcome(long id)throws BusinessException;

	public List<ProcessCheckOutcomeDto> findOutcome(Long processCheckPlanningId, Long processCheckId)throws BusinessException;

}
