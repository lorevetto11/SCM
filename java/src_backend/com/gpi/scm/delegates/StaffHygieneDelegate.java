package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.BasePrerequisiteDto;
import com.gpi.scm.generic.dtos.StaffHygieneDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.StaffHygieneLocal;

public class StaffHygieneDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(StaffHygieneDelegate.class);


	private static StaffHygieneLocal getBean() throws NamingException {
		return (StaffHygieneLocal) getContext().lookup(StaffHygieneLocal.JNDI_NAME);}
	
	public static List<StaffHygieneDto> findAll(Long organizations) {
		List<StaffHygieneDto> obj = null;
		try {
			obj = getBean().findStaffHygiene(organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static StaffHygieneDto findByContextId(long id,List<Long> organizations) {
		StaffHygieneDto obj = null;
		try {
			obj = getBean().findByContextId(id, organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static boolean delete(long id) {
		try {
			return getBean().deleteStaffHygiene(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return false;		
	}

	public static StaffHygieneDto edit(StaffHygieneDto hygiene) {
		StaffHygieneDto obj = null;
		try {
			obj = getBean().editStaffHygiene(hygiene);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static StaffHygieneDto save(StaffHygieneDto hygiene) {
		StaffHygieneDto obj = null;
		try {
			obj = getBean().newStaffHygiene(hygiene);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static StaffHygieneDto findById(long id) {
		try {
			return getBean().findById(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return null;
	}

}
