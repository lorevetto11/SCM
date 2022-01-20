package com.gpi.scm.delegates;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.FlowAnchorPointDto;
import com.gpi.scm.generic.dtos.FlowShapeDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.FlowAnchorPointLocal;

public class FlowAnchorPointDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(FlowAnchorPointDelegate.class);


	private static FlowAnchorPointLocal getBean() throws NamingException {
		return (FlowAnchorPointLocal) getContext().lookup(FlowAnchorPointLocal.JNDI_NAME);}
	
	public static List<FlowAnchorPointDto> findAll(Long diagramId) {
		List<FlowAnchorPointDto> obj = null;
		try {
			obj = getBean().findFlowAnchorPoint(diagramId);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}


	public static boolean delete(long id) {
		try {
			return getBean().deleteFlowAnchorPoint(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return false;		
	}

	public static FlowAnchorPointDto edit(FlowAnchorPointDto flowAncorPoint) {
		FlowAnchorPointDto obj = null;
		try {
			obj = getBean().editFlowAnchorPoint(flowAncorPoint);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}
	
	public static List<FlowAnchorPointDto> editAll(List<FlowAnchorPointDto> flowAncorPoint) {
		List<FlowAnchorPointDto> objs = new ArrayList<FlowAnchorPointDto>();
		Iterator<FlowAnchorPointDto> it = flowAncorPoint.iterator();
		try{
			while(it.hasNext()){
				objs.add(getBean().editFlowAnchorPoint(it.next()));
			};
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return objs;
	}

	public static FlowAnchorPointDto save(FlowAnchorPointDto flowAnchorPoint) {
		FlowAnchorPointDto obj = null;
		try {
			obj = getBean().newFlowAnchorPoint(flowAnchorPoint);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}
	
	public static List<FlowAnchorPointDto> saveAll(List<FlowAnchorPointDto> flowAncorPoint) {
		List<FlowAnchorPointDto> objs = new ArrayList<FlowAnchorPointDto>();
		Iterator<FlowAnchorPointDto> it = flowAncorPoint.iterator();
		try{
			while(it.hasNext()){
				objs.add(getBean().newFlowAnchorPoint(it.next()));
			};
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return objs;
	}

}
