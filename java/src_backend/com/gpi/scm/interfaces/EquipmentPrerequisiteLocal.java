package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.EquipmentPrerequisiteDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface EquipmentPrerequisiteLocal extends PrerequisiteLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "EquipmentPrerequisiteBean" + "!" + EquipmentPrerequisiteLocal.class.getName();
	
	public List<EquipmentPrerequisiteDto> findEquipmentPrerequisite(Long organizations) throws BusinessException;

	public EquipmentPrerequisiteDto editEquipmentPrerequisite(EquipmentPrerequisiteDto equipment)throws BusinessException;

	public EquipmentPrerequisiteDto newEquipmentPrerequisite(EquipmentPrerequisiteDto equipment)throws BusinessException;

	public boolean deleteEquipmentPrerequisite(long id)throws BusinessException;

	public EquipmentPrerequisiteDto findEquipmentPrerequisiteById(long id)throws BusinessException;


}
