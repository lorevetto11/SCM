package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.BasePrerequisiteDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.PestControlLocal;

public class PestControlDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(PestControlDelegate.class);


	private static PestControlLocal getBean() throws NamingException {
		return (PestControlLocal) getContext().lookup(PestControlLocal.JNDI_NAME);}
	
	public static List<BasePrerequisiteDto> findAll(Long organizations) {
		List<BasePrerequisiteDto> obj = null;
		try {
			obj = getBean().findPestControl(organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static BasePrerequisiteDto findById(long id,List<Long> org) {
		BasePrerequisiteDto obj = null;
		try {
			obj = getBean().findPestControlById(id,org);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static BasePrerequisiteDto findByContextId(long id,List<Long> organizations) {
		BasePrerequisiteDto obj = null;
		try {
			obj = getBean().findByContextId(id, organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}
	
	public static boolean delete(long id) {
		try {
			return getBean().deletePestControl(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return false;		
	}

	public static BasePrerequisiteDto edit(BasePrerequisiteDto pestCtrl) {
		BasePrerequisiteDto obj = null;
		try {
			obj = getBean().editPestControl(pestCtrl);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static BasePrerequisiteDto save(BasePrerequisiteDto pestCtrl) {
		BasePrerequisiteDto obj = null;
		try {
			obj = getBean().newPestControl(pestCtrl);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

}
