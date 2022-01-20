package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.AirConditioningTypeDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface AirConditioningTypeLocal {

	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "AirConditioningTypeBean" + "!" + AirConditioningTypeLocal.class.getName();

	public AirConditioningTypeDto saveType(AirConditioningTypeDto toSave) throws BusinessException;

	public AirConditioningTypeDto editType(AirConditioningTypeDto toSave)throws BusinessException;

	public boolean deleteType(long id)throws BusinessException;

	public List<AirConditioningTypeDto> findTypes(List<Long> organizations)throws BusinessException;

}
