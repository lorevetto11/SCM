package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.WaterSupplyTypeDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface WaterSupplyTypeLocal {

	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "WaterSupplyTypeBean" + "!" + WaterSupplyTypeLocal.class.getName();

	public WaterSupplyTypeDto saveType(WaterSupplyTypeDto toSave) throws BusinessException;

	public WaterSupplyTypeDto editType(WaterSupplyTypeDto toSave)throws BusinessException;

	public boolean deleteType(long id)throws BusinessException;

	public List<WaterSupplyTypeDto> findTypes(List<Long> organizations)throws BusinessException;

}
