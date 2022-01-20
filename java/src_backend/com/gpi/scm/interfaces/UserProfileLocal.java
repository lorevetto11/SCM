package com.gpi.scm.interfaces;


import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.UserProfileDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface UserProfileLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "UserProfileBean" + "!" + UserProfileLocal.class.getName();


	public UserProfileDto findProfileById(long idProfile,List<Long> organizations)throws BusinessException;

	public UserProfileDto saveProfile(UserProfileDto toSave) throws BusinessException ;

	public UserProfileDto editProfile(UserProfileDto toSave) throws BusinessException;

	public List<UserProfileDto> findProfiles(List<Long> organizations) throws BusinessException;

	public List<UserProfileDto> getAllProfiles() throws BusinessException;
	
	public boolean deleteUserProfileById(long id)throws BusinessException;
}
