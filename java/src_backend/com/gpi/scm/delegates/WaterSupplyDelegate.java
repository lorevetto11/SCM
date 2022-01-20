package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.BasePrerequisiteDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.WaterSupplyLocal;

public class WaterSupplyDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(WaterSupplyDelegate.class);


	private static WaterSupplyLocal getBean() throws NamingException {
		return (WaterSupplyLocal) getContext().lookup(WaterSupplyLocal.JNDI_NAME);
	}
	
	public static List<BasePrerequisiteDto> findAll(Long organizations) {
		List<BasePrerequisiteDto> obj = null;
		try {
			obj = getBean().findWaterSupply(organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static BasePrerequisiteDto findById(long id,List<Long> organizations) {
		BasePrerequisiteDto obj = null;
		try {
			obj = getBean().findSupplyById(id,organizations);
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
			return getBean().deleteWaterSupply(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return false;		
	}

	public static BasePrerequisiteDto edit(BasePrerequisiteDto supply) {
		BasePrerequisiteDto obj = null;
		try {
			obj = getBean().editWaterSupply(supply);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static BasePrerequisiteDto save(BasePrerequisiteDto supply) {
		BasePrerequisiteDto obj = null;
		try {
			obj = getBean().newWaterSupply(supply);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

}
