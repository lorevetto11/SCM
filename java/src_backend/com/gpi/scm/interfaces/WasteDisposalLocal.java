package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.BasePrerequisiteDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface WasteDisposalLocal extends PrerequisiteLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "WasteDisposalBean" + "!" + WasteDisposalLocal.class.getName();

	public List<BasePrerequisiteDto> findWasteDisposal(Long organizations) throws BusinessException;

	public BasePrerequisiteDto editWasteDisposal(BasePrerequisiteDto disposal)throws BusinessException;

	public BasePrerequisiteDto newWasteDisposal(BasePrerequisiteDto disposal)throws BusinessException;

	public boolean deleteWasteDisposal(long id)throws BusinessException;

	public BasePrerequisiteDto findDisposalsById(long id)throws BusinessException;

}
