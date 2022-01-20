package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.ProcedureDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.ProcedureLocal;

public class ProcedureDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(ProcedureDelegate.class);


	private static ProcedureLocal getBean() throws NamingException {
		return (ProcedureLocal) getContext().lookup(ProcedureLocal.JNDI_NAME);}
	
	public static List<ProcedureDto> findAll(List<Long> organizations) {
		List<ProcedureDto> obj = null;
		try {
			obj = getBean().findProcedure(organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static ProcedureDto findById(long id,List<Long> organizations) {
		ProcedureDto obj = null;
		try {
			obj = getBean().findProcedureById(id,organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static boolean delete(long id) {
		try {
			return getBean().deleteProcedure(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return false;		
	}

	public static ProcedureDto edit(ProcedureDto procedure) {
		ProcedureDto obj = null;
		try {
			obj = getBean().editProcedure(procedure);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static ProcedureDto save(ProcedureDto procedure) {
		ProcedureDto obj = null;
		try {
			obj = getBean().newProcedure(procedure);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

}
