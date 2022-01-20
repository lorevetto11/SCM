package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.ProcessCheckPlanningDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface ProcessCheckPlanningLocal {

	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "ProcessCheckPlanningBean" + "!" + ProcessCheckPlanningLocal.class.getName();

	public ProcessCheckPlanningDto savePlanning(ProcessCheckPlanningDto toSave) throws BusinessException;

	public ProcessCheckPlanningDto editPlanning(ProcessCheckPlanningDto toSave)throws BusinessException;

	public boolean deletePlanning(long id)throws BusinessException;

	public List<ProcessCheckPlanningDto> findPlanning(Long organizationId)throws BusinessException;

}
