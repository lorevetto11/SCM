package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.EquipmentDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface EquipmentLocal {

	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "EquipmentBean" + "!" + EquipmentLocal.class.getName();

	public EquipmentDto save(EquipmentDto organization) throws BusinessException;

	public EquipmentDto edit(EquipmentDto organization)throws BusinessException;

	public boolean delete(long id)throws BusinessException;

	public List<EquipmentDto> finds(Long organizationId)throws BusinessException;

}
