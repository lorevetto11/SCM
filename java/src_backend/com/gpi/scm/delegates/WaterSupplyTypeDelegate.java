package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.WaterSupplyTypeDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.WaterSupplyTypeLocal;

public class WaterSupplyTypeDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(WaterSupplyDelegate.class);

	private static WaterSupplyTypeLocal getBean() throws NamingException {
		return (WaterSupplyTypeLocal) getContext().lookup(WaterSupplyTypeLocal.JNDI_NAME);
	}

	public static List<WaterSupplyTypeDto> findWaterSupplyTypes(List<Long> organizations) {
		List<WaterSupplyTypeDto> obj = null;
		try {
			obj = getBean().findTypes(organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static WaterSupplyTypeDto saveWaterSupplyType(WaterSupplyTypeDto watersupplyType) {
		WaterSupplyTypeDto obj = null;
		try {
			obj = getBean().saveType(watersupplyType);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static WaterSupplyTypeDto editWaterSupplyType(WaterSupplyTypeDto watersupplyType) {
		WaterSupplyTypeDto obj = null;
		try {
			obj = getBean().editType(watersupplyType);
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
