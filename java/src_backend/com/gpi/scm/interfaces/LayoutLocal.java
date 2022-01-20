package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.LayoutDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface LayoutLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "LayoutBean" + "!" + LayoutLocal.class.getName();

	public List<LayoutDto> findLayouts(Long organizations) throws BusinessException;

	public LayoutDto findLayoutsById(long id,List<Long> organizations) throws BusinessException;

	public LayoutDto newLayout(LayoutDto layout) throws BusinessException;

	public LayoutDto editLayout(LayoutDto layout) throws BusinessException;

	public boolean deleteLayout(long id) throws BusinessException;


}
