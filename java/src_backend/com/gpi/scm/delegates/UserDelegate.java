package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.UserDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.UserLocal;

public class UserDelegate extends GenericDelegate {

	private static final Logger logger = Logger.getLogger(UserDelegate.class);

	private static UserLocal getUserBean() throws NamingException {
		return (UserLocal) getContext().lookup(UserLocal.JNDI_NAME);
	}

	public static UserDto saveUser(UserDto toSave) {
		UserDto obj = null;
		try {
			return getUserBean().saveUser(toSave);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static UserDto editUser(UserDto toSave) {
		UserDto obj = null;
		try {
			return getUserBean().editUser(toSave);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static UserDto findUserById(long idUser) {
		UserDto obj = null;
		try {
			obj = getUserBean().findUserById(idUser);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static UserDto findUserByLogin(UserDto user) {
		try {
			return getUserBean().findUserByLogin(user);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	public static List<UserDto> findUsers(List<Long> organizations) {
		try {
			return getUserBean().findUsers(organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
			return null;
		}
	}

	public static boolean deleteUser(long id,List<Long> orgs) {
		try {
			return getUserBean().deleteUserById(id,orgs);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
			
		}
		return false;
	}

	public static UserDto findUserById(long id, List<Long> organizations) {
		UserDto obj = null;
		try {
			obj = getUserBean().findUserById(id,organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}
}
