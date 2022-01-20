package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.ShapeDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.ShapeLocal;

public class ShapeDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(ShapeDelegate.class);

	private static ShapeLocal getBean() throws NamingException {
		return (ShapeLocal) getContext().lookup(ShapeLocal.JNDI_NAME);
	}

	public static List<ShapeDto> findShape() {
		List<ShapeDto> obj = null;
		try {
			obj = getBean().findShapes();
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static ShapeDto findShapeById(long id) {
		ShapeDto obj = null;
		try {
			obj = getBean().findShapesById(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static ShapeDto saveShape(ShapeDto layout) {
		ShapeDto obj = null;
		try {
			obj = getBean().newShape(layout);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static ShapeDto editShape(ShapeDto layout) {
		ShapeDto obj = null;
		try {
			obj = getBean().editShape(layout);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static boolean deleteShape(long id) {
		try {
			
			return getBean().deleteShape(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return false;
	}

}