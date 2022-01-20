package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.WasteDisposalTypeDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface WasteDisposalTypeLocal {

	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "WasteDisposalTypeBean" + "!" + WasteDisposalTypeLocal.class.getName();

	public WasteDisposalTypeDto saveType(WasteDisposalTypeDto toSave) throws BusinessException;

	public WasteDisposalTypeDto editType(WasteDisposalTypeDto toSave)throws BusinessException;

	public boolean deleteType(long id)throws BusinessException;

	public List<WasteDisposalTypeDto> findTypes(List<Long> organizations)throws BusinessException;

}
