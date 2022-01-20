package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.AnalysisValueDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface AnalysisValueLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "AnalysisValueBean" + "!" + AnalysisValueLocal.class.getName();

	public boolean deleteAnalisisValue(long id) throws BusinessException;

	public AnalysisValueDto editAnalisisValue(AnalysisValueDto value)throws BusinessException;

	public AnalysisValueDto newAnalisisValue(AnalysisValueDto value)throws BusinessException;

	public List<AnalysisValueDto> findAnalisisValue(Long analysisParameterId)throws BusinessException;

}
