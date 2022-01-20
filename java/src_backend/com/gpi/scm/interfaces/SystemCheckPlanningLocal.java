package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.SystemCheckPlanningDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface SystemCheckPlanningLocal {

	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "SystemCheckPlanningBean" + "!" + SystemCheckPlanningLocal.class.getName();

	public SystemCheckPlanningDto savePlanning(SystemCheckPlanningDto toSave) throws BusinessException;

	public SystemCheckPlanningDto editPlanning(SystemCheckPlanningDto toSave)throws BusinessException;

	public boolean deletePlanning(long id)throws BusinessException;

	public List<SystemCheckPlanningDto> findPlanning(Long organizationId)throws BusinessException;

}
