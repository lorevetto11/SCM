package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.DiagramDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.DiagramLocal;

public class DiagramDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(DiagramDelegate.class);


	private static DiagramLocal getBean() throws NamingException {
		return (DiagramLocal) getContext().lookup(DiagramLocal.JNDI_NAME);}
	
	public static List<DiagramDto> findAll(Long organizations) {
		List<DiagramDto> obj = null;
		try {
			obj = getBean().findDiagram(organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}


	public static boolean delete(long id) {
		try {
			return getBean().deleteDiagram(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return false;		
	}

	public static DiagramDto edit(DiagramDto diagram) {
		DiagramDto obj = null;
		try {
			obj = getBean().editDiagram(diagram);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static DiagramDto save(DiagramDto diagram) {
		DiagramDto obj = null;
		try {
			obj = getBean().newDiagram(diagram);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

}
