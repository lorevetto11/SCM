package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.FrequencyDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface FrequencyLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "FrequencyBean" + "!" + FrequencyLocal.class.getName();


	public List<FrequencyDto> findFrequency(List<Long> organizations)throws BusinessException;

	public boolean deleteFrequency(long id)throws BusinessException;

	public FrequencyDto editFrequency(FrequencyDto frequency)throws BusinessException;

	public FrequencyDto newFrequency(FrequencyDto frequency)throws BusinessException;


}
