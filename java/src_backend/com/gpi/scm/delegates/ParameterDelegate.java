package com.gpi.scm.delegates;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.ParameterDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.ParameterLocal;


public class ParameterDelegate extends GenericDelegate {
	
	private static Logger logger = Logger.getLogger(ParameterDelegate.class);
	
	private static ParameterLocal getParameterBean() throws NamingException{
		return (ParameterLocal) getContext().lookup(ParameterLocal.JNDI_NAME);
	}

	
	public static ParameterDto getParameter(String key, Long idOrg) throws BusinessException{
		try{
			
			return getParameterBean().getParameter(key, idOrg);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new BusinessException(e) ;
		}
		
	}
	
	public static ParameterDto getParameterByOrganizationCode(String key, String orgCode) throws BusinessException{
		try{
			
			return getParameterBean().getParameterByOrganizationCode(key, orgCode);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new BusinessException(e) ;
		}
		
	}

}
