package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.MaterialCategoryDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.MaterialCategoryLocal;

public class MaterialCategoryDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(MaterialCategoryDelegate.class);

	private static MaterialCategoryLocal getBean() throws NamingException {
		return (MaterialCategoryLocal) getContext().lookup(MaterialCategoryLocal.JNDI_NAME);
	}

	public static List<MaterialCategoryDto> findAll() {
		List<MaterialCategoryDto> obj = null;
		try {
			obj = getBean().findMaterialCategory();
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static boolean delete(long id) {
		try {
			return getBean().deleteMaterialCategory(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return false;
	}

	public static MaterialCategoryDto edit(MaterialCategoryDto material) {
		MaterialCategoryDto obj = null;
		try {
			obj = getBean().editMaterialCategory(material);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static MaterialCategoryDto save(MaterialCategoryDto material) {
		MaterialCategoryDto obj = null;
		try {
			obj = getBean().newMaterialCategory(material);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

}
