package com.gpi.scm.interfaces;

import java.util.List;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.FloorDto;
import com.gpi.scm.generic.exceptions.BusinessException;

public interface FloorLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "FloorBean" + "!" + FloorLocal.class.getName();

	public List<FloorDto> findFloors(Long organizationId) throws BusinessException;

	public boolean deleteFloor(long id)throws BusinessException;

	public FloorDto editFloor(FloorDto floor)throws BusinessException;

	public FloorDto newFloor(FloorDto floor)throws BusinessException;

	public FloorDto findFloorsById(long id)throws BusinessException;

}
