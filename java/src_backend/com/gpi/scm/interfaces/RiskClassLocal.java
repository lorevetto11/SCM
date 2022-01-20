package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.RiskClassDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local

public interface RiskClassLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "RiskClassBean" + "!" + RiskClassLocal.class.getName();

	public List<RiskClassDto> findClasses(Long organizationId) throws BusinessException;

	public RiskClassDto saveClass(RiskClassDto toSave)throws BusinessException;

	public Object editClass(RiskClassDto toSave)throws BusinessException;

	public boolean deleteClass(long id) throws BusinessException;



}
