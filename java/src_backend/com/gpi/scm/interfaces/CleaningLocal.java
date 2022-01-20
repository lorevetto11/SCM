package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.BasePrerequisiteDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface CleaningLocal extends PrerequisiteLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "CleaningBean" + "!" + CleaningLocal.class.getName();
	
	public List<BasePrerequisiteDto> findCleaning(Long organizations) throws BusinessException;

	public BasePrerequisiteDto editCleaning(BasePrerequisiteDto cleaning)throws BusinessException;

	public BasePrerequisiteDto newCleaning(BasePrerequisiteDto cleaning)throws BusinessException;

	public boolean deleteCleaning(long id)throws BusinessException;

	public BasePrerequisiteDto findCleaningById(long id)throws BusinessException;


}
