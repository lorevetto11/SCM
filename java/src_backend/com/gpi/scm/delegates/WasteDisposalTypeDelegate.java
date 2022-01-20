package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.WasteDisposalTypeDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.WasteDisposalTypeLocal;

public class WasteDisposalTypeDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(WasteDisposalDelegate.class);

	private static WasteDisposalTypeLocal getBean() throws NamingException {
		return (WasteDisposalTypeLocal) getContext().lookup(WasteDisposalTypeLocal.JNDI_NAME);
	}

	public static List<WasteDisposalTypeDto> findWasteDisposalTypes(List<Long> organizations) {
		List<WasteDisposalTypeDto> obj = null;
		try {
			obj = getBean().findTypes(organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static WasteDisposalTypeDto saveWasteDisposalType(WasteDisposalTypeDto wastedisposalType) {
		WasteDisposalTypeDto obj = null;
		try {
			obj = getBean().saveType(wastedisposalType);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static WasteDisposalTypeDto editWasteDisposalType(WasteDisposalTypeDto wastedisposalType) {
		WasteDisposalTypeDto obj = null;
		try {
			obj = getBean().editType(wastedisposalType);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static boolean deleteById(long id) {
		boolean obj = false;
		try {
			obj = getBean().deleteType(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

}
