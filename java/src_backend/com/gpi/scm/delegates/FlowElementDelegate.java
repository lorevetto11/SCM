package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.FlowElementDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.FlowElementLocal;

public class FlowElementDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(FlowElementDelegate.class);


	private static FlowElementLocal getBean() throws NamingException {
		return (FlowElementLocal) getContext().lookup(FlowElementLocal.JNDI_NAME);}
	
	public static List<FlowElementDto> findAll(Long diagramId, Long shapeId) {
		List<FlowElementDto> obj = null;
		try {
			obj = getBean().findFlowElement(diagramId, shapeId);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static FlowElementDto findByContextId(long id) {
		FlowElementDto obj = null;
		try {
			obj = getBean().findByContextId(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static boolean delete(long id) {
		try {
			return getBean().deleteFlowElement(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return false;		
	}

	public static FlowElementDto edit(FlowElementDto flowElement) {
		FlowElementDto obj = null;
		try {
			obj = getBean().editFlowElement(flowElement);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static FlowElementDto save(FlowElementDto flowElement) {
		FlowElementDto obj = null;
		try {
			obj = getBean().newFlowElement(flowElement);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	

}
