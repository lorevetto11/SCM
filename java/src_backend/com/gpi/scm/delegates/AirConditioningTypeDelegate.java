package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.AirConditioningTypeDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.AirConditioningTypeLocal;

public class AirConditioningTypeDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(AirConditioningDelegate.class);

	private static AirConditioningTypeLocal getBean() throws NamingException {
		return (AirConditioningTypeLocal) getContext().lookup(AirConditioningTypeLocal.JNDI_NAME);
	}

	public static List<AirConditioningTypeDto> findAirConditioningTypes(List<Long> organizations) {
		List<AirConditioningTypeDto> obj = null;
		try {
			obj = getBean().findTypes(organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static AirConditioningTypeDto saveAirConditioningType(AirConditioningTypeDto airconditioningType) {
		AirConditioningTypeDto obj = null;
		try {
			obj = getBean().saveType(airconditioningType);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static AirConditioningTypeDto editAirConditioningType(AirConditioningTypeDto airconditioningType) {
		AirConditioningTypeDto obj = null;
		try {
			obj = getBean().editType(airconditioningType);
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
