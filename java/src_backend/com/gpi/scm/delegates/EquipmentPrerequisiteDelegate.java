package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.BasePrerequisiteDto;
import com.gpi.scm.generic.dtos.EquipmentPrerequisiteDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.EquipmentPrerequisiteLocal;

public class EquipmentPrerequisiteDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(EquipmentPrerequisiteDelegate.class);


	private static EquipmentPrerequisiteLocal getBean() throws NamingException {
		return (EquipmentPrerequisiteLocal) getContext().lookup(EquipmentPrerequisiteLocal.JNDI_NAME);}
	
	public static List<EquipmentPrerequisiteDto> findAll(Long organizations) {
		List<EquipmentPrerequisiteDto> obj = null;
		try {
			obj = getBean().findEquipmentPrerequisite(organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static EquipmentPrerequisiteDto findById(long id) {
		EquipmentPrerequisiteDto obj = null;
		try {
			obj = getBean().findEquipmentPrerequisiteById(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}
	
	public static BasePrerequisiteDto findByContextId(long id,List<Long> organizations) {
		BasePrerequisiteDto obj = null;
		try {
			obj = getBean().findByContextId(id, organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static boolean delete(long id) {
		try {
			return getBean().deleteEquipmentPrerequisite(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return false;		
	}

	public static EquipmentPrerequisiteDto edit(EquipmentPrerequisiteDto equipment) {
		EquipmentPrerequisiteDto obj = null;
		try {
			obj = getBean().editEquipmentPrerequisite(equipment);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static EquipmentPrerequisiteDto save(EquipmentPrerequisiteDto equipment) {
		EquipmentPrerequisiteDto obj = null;
		try {
			obj = getBean().newEquipmentPrerequisite(equipment);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

}
