package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.StaffHygieneDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface StaffHygieneLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "StaffHygieneBean" + "!" + StaffHygieneLocal.class.getName();

	public boolean deleteStaffHygiene(long id) throws BusinessException;

	public StaffHygieneDto editStaffHygiene(StaffHygieneDto danger)throws BusinessException;

	public StaffHygieneDto newStaffHygiene(StaffHygieneDto danger)throws BusinessException;

	public List<StaffHygieneDto> findStaffHygiene(Long organizations)throws BusinessException;

	public StaffHygieneDto findById(long id) throws BusinessException;

	public StaffHygieneDto findByContextId(long id,List<Long> organizations)throws BusinessException;
}
