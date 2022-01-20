package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.BasePrerequisiteDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.WasteDisposalLocal;

public class WasteDisposalDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(WasteDisposalDelegate.class);


	private static WasteDisposalLocal getBean() throws NamingException {
		return (WasteDisposalLocal) getContext().lookup(WasteDisposalLocal.JNDI_NAME);}
	
	public static List<BasePrerequisiteDto> findAll(Long organizations) {
		List<BasePrerequisiteDto> obj = null;
		try {
			obj = getBean().findWasteDisposal(organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static BasePrerequisiteDto findById(long id) {
		BasePrerequisiteDto obj = null;
		try {
			obj = getBean().findDisposalsById(id);
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
			return getBean().deleteWasteDisposal(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return false;		
	}

	public static BasePrerequisiteDto edit(BasePrerequisiteDto disposal) {
		BasePrerequisiteDto obj = null;
		try {
			obj = getBean().editWasteDisposal(disposal);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static BasePrerequisiteDto save(BasePrerequisiteDto disposal) {
		BasePrerequisiteDto obj = null;
		try {
			obj = getBean().newWasteDisposal(disposal);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

}
