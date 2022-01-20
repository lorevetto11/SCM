package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.UserRoleDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.UserRoleLocal;

public class UserRoleDelegate extends GenericDelegate {

	private static final Logger logger = Logger.getLogger(UserRoleDelegate.class);{
	}
	private static UserRoleLocal getRoleBean() throws NamingException {
		return (UserRoleLocal) getContext().lookup(UserRoleLocal.JNDI_NAME);
	}
	
	public static List<UserRoleDto> findRole(List<Long> organizations) {
		try {
			return getRoleBean().findRoles(organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
			return null;
		}
	}
	public static UserRoleDto findRoleById(long idRole) {
		UserRoleDto obj = null;
		try {
			obj = getRoleBean().findRoleById(idRole);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}
	public static UserRoleDto saveRole(UserRoleDto toSave) {
		UserRoleDto obj = null;
		try {
			return getRoleBean().saveRole(toSave);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}
	public static UserRoleDto editRole(UserRoleDto toSave) {
		UserRoleDto obj = null;
		try {
			return getRoleBean().editRole(toSave);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static boolean deleteRole(long id) {
		try {
			return getRoleBean().deleteUserRoleById(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return false;
		
	}

}
