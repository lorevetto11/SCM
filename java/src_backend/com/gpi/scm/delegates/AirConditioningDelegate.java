package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.BasePrerequisiteDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.AirConditioningLocal;

public class AirConditioningDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(AirConditioningDelegate.class);


	private static AirConditioningLocal getBean() throws NamingException {
		return (AirConditioningLocal) getContext().lookup(AirConditioningLocal.JNDI_NAME);}
	
	public static List<BasePrerequisiteDto> findAll(Long organizations) {
		List<BasePrerequisiteDto> obj = null;
		try {
			obj = getBean().findAirConditioning(organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static BasePrerequisiteDto findById(long id) {
		BasePrerequisiteDto obj = null;
		try {
			obj = getBean().findAirConditioningById(id);
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
			return getBean().deleteAirConditioning(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return false;		
	}

	public static BasePrerequisiteDto edit(BasePrerequisiteDto conditioning) {
		BasePrerequisiteDto obj = null;
		try {
			obj = getBean().editAirConditioning(conditioning);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static BasePrerequisiteDto save(BasePrerequisiteDto conditioning) {
		BasePrerequisiteDto obj = null;
		try {
			obj = getBean().newAirConditioning(conditioning);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

}
