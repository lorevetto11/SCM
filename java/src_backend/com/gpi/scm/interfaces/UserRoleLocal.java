package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.UserRoleDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface UserRoleLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "UserRoleBean" + "!" + UserRoleLocal.class.getName();

	
public List<UserRoleDto> findRoles (List<Long> organizations)throws BusinessException;

public UserRoleDto findRoleById(long idRole)throws BusinessException;

public UserRoleDto saveRole(UserRoleDto toSave) throws BusinessException ;

public UserRoleDto editRole(UserRoleDto toSave) throws BusinessException;

public boolean deleteUserRoleById(long id)throws BusinessException;


}
