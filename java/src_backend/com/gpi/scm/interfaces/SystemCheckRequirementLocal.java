package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.SystemCheckRequirementDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface SystemCheckRequirementLocal {

	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "SystemCheckRequirementBean" + "!" + SystemCheckRequirementLocal.class.getName();

	public SystemCheckRequirementDto saveRequirement(SystemCheckRequirementDto toSave) throws BusinessException;

	public SystemCheckRequirementDto editRequirement(SystemCheckRequirementDto toSave)throws BusinessException;

	public boolean deleteRequirement(long id)throws BusinessException;

	public List<SystemCheckRequirementDto> findRequirement(Long systemCheckId)throws BusinessException;

}
