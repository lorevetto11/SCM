package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.SupplierDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.SupplierLocal;

public class SupplierDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(SupplierDelegate.class);


	private static SupplierLocal getBean() throws NamingException {
		return (SupplierLocal) getContext().lookup(SupplierLocal.JNDI_NAME);}
	
	public static List<SupplierDto> findAll(Long organizations) {
		List<SupplierDto> obj = null;
		try {
			obj = getBean().findSupplier(organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}


	public static boolean delete(long id) {
		try {
			return getBean().deleteSupplier(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return false;		
	}

	public static SupplierDto edit(SupplierDto supplier) {
		SupplierDto obj = null;
		try {
			obj = getBean().editSupplier(supplier);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static SupplierDto save(SupplierDto supplier) {
		SupplierDto obj = null;
		try {
			obj = getBean().newSupplier(supplier);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

}
