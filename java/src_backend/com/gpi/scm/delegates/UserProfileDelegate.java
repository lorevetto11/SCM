package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.UserProfileDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.UserProfileLocal;

public class UserProfileDelegate extends GenericDelegate {

	private static final Logger logger = Logger.getLogger(UserProfileDelegate.class);{
	}
	private static UserProfileLocal getProfileBean() throws NamingException {
		return (UserProfileLocal)getContext().lookup(UserProfileLocal.JNDI_NAME);
	}
	
	public static UserProfileDto findProfileById(long id,List<Long> organizations) {
		UserProfileDto obj = null;
		try {
			obj = getProfileBean().findProfileById(id, organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static Object saveProfile(UserProfileDto userProfile) {
		UserProfileDto obj = null;
		try {
			obj = getProfileBean().saveProfile(userProfile);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static Object editProfile(UserProfileDto userProfile) {
		UserProfileDto obj = null;
		try {
			obj = getProfileBean().editProfile(userProfile);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static List<UserProfileDto> findProfiles(List<Long> organizations) {
		List<UserProfileDto> obj = null;
		try {
			obj = getProfileBean().findProfiles(organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}


	public static List<UserProfileDto> getAllProfiles() {
		List<UserProfileDto> obj = null;
		try {
			obj = getProfileBean().getAllProfiles();
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	
	public static boolean deleteProfile(long id) {
		try {
			return getProfileBean().deleteUserProfileById(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return false;
	}


}
