package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.BasePrerequisiteDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface WaterSupplyLocal extends PrerequisiteLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "WaterSupplyBean" + "!" + WaterSupplyLocal.class.getName();
	
	public List<BasePrerequisiteDto> findWaterSupply(Long organizations) throws BusinessException;

	public BasePrerequisiteDto editWaterSupply(BasePrerequisiteDto disposal)throws BusinessException;

	public BasePrerequisiteDto newWaterSupply(BasePrerequisiteDto disposal)throws BusinessException;

	public boolean deleteWaterSupply(long id)throws BusinessException;

	public BasePrerequisiteDto findSupplyById(long id,List<Long> organizations)throws BusinessException;
}
