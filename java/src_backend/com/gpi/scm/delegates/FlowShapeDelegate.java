package com.gpi.scm.delegates;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.ErrorMessage;
import com.gpi.scm.generic.dtos.FlowShapeDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.FlowShapeLocal;

public class FlowShapeDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(FlowShapeDelegate.class);


	private static FlowShapeLocal getBean() throws NamingException {
		return (FlowShapeLocal) getContext().lookup(FlowShapeLocal.JNDI_NAME);}
	
	public static List<FlowShapeDto> findAll(Long diagramId) {
		List<FlowShapeDto> obj = null;
		try {
			obj = getBean().findFlowShape(diagramId);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}


	public static boolean delete(long id) {
		try {
			return getBean().deleteFlowShape(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return false;		
	}

	public static FlowShapeDto edit(FlowShapeDto flowShape) {
		FlowShapeDto obj = null;
		try {
			obj = getBean().editFlowShape(flowShape);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}
	
	public static List<FlowShapeDto> editAll(List<FlowShapeDto> flowShapes) {
		List<FlowShapeDto> objs = new ArrayList<FlowShapeDto>();
		Iterator<FlowShapeDto> it = flowShapes.iterator();
		try{
			while(it.hasNext()){
				objs.add(getBean().editFlowShape(it.next()));
			};
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return objs;
	}

	public static FlowShapeDto save(FlowShapeDto flowShape) {
		FlowShapeDto obj = null;
		try {
			obj = getBean().newFlowShape(flowShape);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}
	
	public static List<FlowShapeDto> saveAll(List<FlowShapeDto> flowShapes) {
		List<FlowShapeDto> objs = new ArrayList<FlowShapeDto>();
		Iterator<FlowShapeDto> it = flowShapes.iterator();
		try{
			while(it.hasNext()){
				objs.add(getBean().newFlowShape(it.next()));
			};
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return objs;
	}

}
