package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.BasePrerequisiteDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface AirConditioningLocal extends PrerequisiteLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "AirConditioningBean" + "!" + AirConditioningLocal.class.getName();
	
	public List<BasePrerequisiteDto> findAirConditioning(Long organizations) throws BusinessException;

	public BasePrerequisiteDto editAirConditioning(BasePrerequisiteDto conditioning)throws BusinessException;

	public BasePrerequisiteDto newAirConditioning(BasePrerequisiteDto conditioning)throws BusinessException;

	public boolean deleteAirConditioning(long id)throws BusinessException;

	public BasePrerequisiteDto findAirConditioningById(long id)throws BusinessException;


}
