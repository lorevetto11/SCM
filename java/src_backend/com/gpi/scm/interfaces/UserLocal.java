package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.UserDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface UserLocal {

	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "UserBean" + "!" + UserLocal.class.getName();

	public UserDto saveUser(UserDto toSave) throws BusinessException;
	
	public UserDto editUser(UserDto toSave) throws BusinessException;

	public UserDto findUserById(long idUser) throws BusinessException;

	public List<UserDto> findUsers(List<Long> organizations) throws BusinessException;

	public UserDto findUserByLogin(UserDto user) throws BusinessException;

	public boolean deleteUserById(long id,List<Long> orgs)throws BusinessException;
	
	public UserDto findUserById(long idUser,List<Long> organizations) throws BusinessException;

}
