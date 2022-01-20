package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.EquipmentDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.EquipmentLocal;

public class EquipmentDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(EquipmentDelegate.class);

	private static EquipmentLocal getBean() throws NamingException {
		return (EquipmentLocal) getContext().lookup(EquipmentLocal.JNDI_NAME);
	}

	public static List<EquipmentDto> findEquipments(Long organizationId) {
		List<EquipmentDto> obj = null;
		try {
			obj = getBean().finds(organizationId);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static EquipmentDto saveEquipment(EquipmentDto equipment) {
		EquipmentDto obj = null;
		try {
			obj = getBean().save(equipment);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static EquipmentDto editEquipment(EquipmentDto equipment) {
		EquipmentDto obj = null;
		try {
			obj = getBean().edit(equipment);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static boolean deleteById(long id) {
		boolean obj = false ;
		try {
			obj = getBean().delete(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

}
