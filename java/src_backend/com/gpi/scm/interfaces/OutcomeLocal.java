package com.gpi.scm.interfaces;


import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.OutcomeDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface OutcomeLocal  {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "OutcomeBean" + "!" + OutcomeLocal.class.getName();

	public boolean deleteOutcome(long id) throws BusinessException;

	public OutcomeDto editOutcome(OutcomeDto outcome) throws BusinessException;

	public OutcomeDto newOutcome(OutcomeDto outcome) throws BusinessException;

	public OutcomeDto findOutcomeByMonitorId(long id, List<Long> organizations) throws BusinessException;



}
