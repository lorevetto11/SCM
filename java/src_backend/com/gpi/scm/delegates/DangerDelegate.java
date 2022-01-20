package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.BasePrerequisiteDto;
import com.gpi.scm.generic.dtos.DangerDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.DangerLocal;

public class DangerDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(DangerDelegate.class);


	private static DangerLocal getBean() throws NamingException {
		return (DangerLocal) getContext().lookup(DangerLocal.JNDI_NAME);}
	
	public static List<DangerDto> findAll(Long organizations) {
		List<DangerDto> obj = null;
		try {
			obj = getBean().findDanger(organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static DangerDto findByContextId(long id,List<Long> organizations) {
		DangerDto obj = null;
		try {
			obj = getBean().findByContextId(id, organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static boolean delete(long id) {
		try {
			return getBean().deleteDanger(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return false;		
	}

	public static DangerDto edit(DangerDto danger) {
		DangerDto obj = null;
		try {
			obj = getBean().editDanger(danger);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static DangerDto save(DangerDto danger) {
		DangerDto obj = null;
		try {
			obj = getBean().newDanger(danger);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

}
