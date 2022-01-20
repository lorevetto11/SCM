package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.BasePrerequisiteDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.CleaningLocal;

public class CleaningDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(CleaningDelegate.class);


	private static CleaningLocal getBean() throws NamingException {
		return (CleaningLocal) getContext().lookup(CleaningLocal.JNDI_NAME);}
	
	public static List<BasePrerequisiteDto> findAll(Long organizations) {
		List<BasePrerequisiteDto> obj = null;
		try {
			obj = getBean().findCleaning(organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static BasePrerequisiteDto findById(long id) {
		BasePrerequisiteDto obj = null;
		try {
			obj = getBean().findCleaningById(id);
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
			return getBean().deleteCleaning(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return false;		
	}

	public static BasePrerequisiteDto edit(BasePrerequisiteDto cleaning) {
		BasePrerequisiteDto obj = null;
		try {
			obj = getBean().editCleaning(cleaning);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static BasePrerequisiteDto save(BasePrerequisiteDto cleaning) {
		BasePrerequisiteDto obj = null;
		try {
			obj = getBean().newCleaning(cleaning);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

}
