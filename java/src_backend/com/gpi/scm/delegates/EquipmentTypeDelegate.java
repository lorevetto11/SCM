package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.EquipmentTypeDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.EquipmentTypeLocal;

public class EquipmentTypeDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(EquipmentDelegate.class);

	private static EquipmentTypeLocal getBean() throws NamingException {
		return (EquipmentTypeLocal) getContext().lookup(EquipmentTypeLocal.JNDI_NAME);
	}

	public static List<EquipmentTypeDto> findEquipmentTypes(List<Long> organizations) {
		List<EquipmentTypeDto> obj = null;
		try {
			obj = getBean().findTypes(organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static EquipmentTypeDto saveEquipmentType(EquipmentTypeDto equipmentType) {
		EquipmentTypeDto obj = null;
		try {
			obj = getBean().saveType(equipmentType);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static EquipmentTypeDto editEquipmentType(EquipmentTypeDto equipmentType) {
		EquipmentTypeDto obj = null;
		try {
			obj = getBean().editType(equipmentType);
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
