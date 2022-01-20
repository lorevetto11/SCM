package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.FlowShapeDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface FlowShapeLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "FlowShapeBean" + "!" + FlowShapeLocal.class.getName();

	public List<FlowShapeDto> findFlowShape(Long diagramId) throws BusinessException;
	
	public boolean deleteFlowShape(long id) throws BusinessException;

	public FlowShapeDto editFlowShape(FlowShapeDto FlowShape) throws BusinessException;

	public FlowShapeDto newFlowShape(FlowShapeDto FlowShape) throws BusinessException;


}
