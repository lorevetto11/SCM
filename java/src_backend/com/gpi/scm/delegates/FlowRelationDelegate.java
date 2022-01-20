package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.FlowRelationDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.FlowRelationLocal;

public class FlowRelationDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(FlowRelationDelegate.class);


	private static FlowRelationLocal getBean() throws NamingException {
		return (FlowRelationLocal) getContext().lookup(FlowRelationLocal.JNDI_NAME);}
	
	public static List<FlowRelationDto> findAll(Long diagramId) {
		List<FlowRelationDto> obj = null;
		try {
			obj = getBean().findFlowRelation(diagramId);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}


	public static boolean delete(long id) {
		try {
			return getBean().deleteFlowRelation(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return false;		
	}

	public static FlowRelationDto edit(FlowRelationDto flowRelation) {
		FlowRelationDto obj = null;
		try {
			obj = getBean().editFlowRelation(flowRelation);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static FlowRelationDto save(FlowRelationDto flowRelation) {
		FlowRelationDto obj = null;
		try {
			obj = getBean().newFlowRelation(flowRelation);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

}
