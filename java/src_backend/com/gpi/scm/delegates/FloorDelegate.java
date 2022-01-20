package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.FloorDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.FloorLocal;

public class FloorDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(FloorDelegate.class);

	private static FloorLocal getBean() throws NamingException {
		return (FloorLocal) getContext().lookup(FloorLocal.JNDI_NAME);
	}

	public static List<FloorDto> findFloor(Long organizationId) {
		List<FloorDto> obj = null;
		try {
			obj = getBean().findFloors(organizationId);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static FloorDto findFloorById(long id) {
		FloorDto obj = null;
		try {
			obj = getBean().findFloorsById(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static FloorDto saveFloor(FloorDto floor) {
		FloorDto obj = null;
		try {
			obj = getBean().newFloor(floor);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static FloorDto editFloor(FloorDto floor) {
		FloorDto obj = null;
		try {
			obj = getBean().editFloor(floor);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static boolean deleteFloor(long id) {
		try {
			return getBean().deleteFloor(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return false;
	}
}
