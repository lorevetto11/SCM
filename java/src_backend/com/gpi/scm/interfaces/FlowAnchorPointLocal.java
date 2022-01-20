package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.FlowAnchorPointDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface FlowAnchorPointLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "FlowAnchorPointBean" + "!" + FlowAnchorPointLocal.class.getName();

	public List<FlowAnchorPointDto> findFlowAnchorPoint(Long diagramId) throws BusinessException;
	
	public boolean deleteFlowAnchorPoint(long id) throws BusinessException;

	public FlowAnchorPointDto editFlowAnchorPoint(FlowAnchorPointDto FlowAnchorPoint) throws BusinessException;

	public FlowAnchorPointDto newFlowAnchorPoint(FlowAnchorPointDto FlowAnchorPoint) throws BusinessException;


}
