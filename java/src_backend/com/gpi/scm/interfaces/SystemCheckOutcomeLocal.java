package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.SystemCheckOutcomeDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface SystemCheckOutcomeLocal {

	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "SystemCheckOutcomeBean" + "!" + SystemCheckOutcomeLocal.class.getName();

	public SystemCheckOutcomeDto saveOutcome(SystemCheckOutcomeDto toSave) throws BusinessException;

	public SystemCheckOutcomeDto editOutcome(SystemCheckOutcomeDto toSave)throws BusinessException;

	public boolean deleteOutcome(long id)throws BusinessException;

	public List<SystemCheckOutcomeDto> findOutcome(Long systemCheckRequiremntId)throws BusinessException;

}
