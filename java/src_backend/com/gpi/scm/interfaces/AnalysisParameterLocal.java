package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.AnalysisParameterDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface AnalysisParameterLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "AnalysisParameterBean" + "!" + AnalysisParameterLocal.class.getName();

	public boolean deleteAnalisisParameter(long id) throws BusinessException;

	public AnalysisParameterDto editAnalisisParameter(AnalysisParameterDto parameter)throws BusinessException;

	public AnalysisParameterDto newAnalisisParameter(AnalysisParameterDto parameter)throws BusinessException;

	public List<AnalysisParameterDto> findAnalisisParameter(Long organizations)throws BusinessException;

}
