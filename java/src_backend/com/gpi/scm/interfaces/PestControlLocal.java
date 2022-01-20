package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.BasePrerequisiteDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface PestControlLocal extends PrerequisiteLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "PestControlBean" + "!" + PestControlLocal.class.getName();
	
	public List<BasePrerequisiteDto> findPestControl(Long organizations) throws BusinessException;

	public BasePrerequisiteDto editPestControl(BasePrerequisiteDto pestCtrl)throws BusinessException;

	public BasePrerequisiteDto newPestControl(BasePrerequisiteDto pestCtrl)throws BusinessException;

	public boolean deletePestControl(long id)throws BusinessException;

	public BasePrerequisiteDto findPestControlById(long id,List<Long> organizations)throws BusinessException;


}
