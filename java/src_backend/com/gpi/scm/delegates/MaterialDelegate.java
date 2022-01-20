package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.MaterialDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.MaterialLocal;

public class MaterialDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(MaterialDelegate.class);


	private static MaterialLocal getBean() throws NamingException {
		return (MaterialLocal) getContext().lookup(MaterialLocal.JNDI_NAME);}
	
	public static List<MaterialDto> findAll(Long organizations) {
		List<MaterialDto> obj = null;
		try {
			obj = getBean().findMaterial(organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}


	public static boolean delete(long id) {
		try {
			return getBean().deleteMaterial(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return false;		
	}

	public static MaterialDto edit(MaterialDto material) {
		MaterialDto obj = null;
		try {
			obj = getBean().editMaterial(material);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static MaterialDto save(MaterialDto material) {
		MaterialDto obj = null;
		try {
			obj = getBean().newMaterial(material);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

}
