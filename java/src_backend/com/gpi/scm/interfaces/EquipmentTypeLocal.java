package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.EquipmentTypeDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface EquipmentTypeLocal {

	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "EquipmentTypeBean" + "!" + EquipmentTypeLocal.class.getName();

	public EquipmentTypeDto saveType(EquipmentTypeDto toSave) throws BusinessException;

	public EquipmentTypeDto editType(EquipmentTypeDto toSave)throws BusinessException;

	public boolean deleteType(long id)throws BusinessException;

	public List<EquipmentTypeDto> findTypes(List<Long> organizations)throws BusinessException;

}
