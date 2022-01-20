package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.LayoutDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.LayoutLocal;

public class LayoutDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(LayoutDelegate.class);

	private static LayoutLocal getBean() throws NamingException {
		return (LayoutLocal) getContext().lookup(LayoutLocal.JNDI_NAME);
	}

	public static List<LayoutDto> findLayout(Long organizations) {
		List<LayoutDto> obj = null;
		try {
			obj = getBean().findLayouts(organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static LayoutDto findLayoutById(long id,List<Long> organizations) {
		LayoutDto obj = null;
		try {
			obj = getBean().findLayoutsById(id,organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static LayoutDto saveLayout(LayoutDto layout) {
		LayoutDto obj = null;
		try {
			obj = getBean().newLayout(layout);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static LayoutDto editLayout(LayoutDto layout) {
		LayoutDto obj = null;
		try {
			obj = getBean().editLayout(layout);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static boolean deleteLayout(long id) {
		try {
			
			return getBean().deleteLayout(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return false;
	}

}
