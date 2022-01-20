package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.PestControlTypeDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface PestControlTypeLocal {

	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "PestControlTypeBean" + "!" + PestControlTypeLocal.class.getName();

	public PestControlTypeDto saveType(PestControlTypeDto toSave) throws BusinessException;

	public PestControlTypeDto editType(PestControlTypeDto toSave)throws BusinessException;

	public boolean deleteType(long id)throws BusinessException;

	public List<PestControlTypeDto> findTypes(List<Long> organizations)throws BusinessException;

}
