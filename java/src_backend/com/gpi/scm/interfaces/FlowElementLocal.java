package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.FlowElementDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface FlowElementLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "FlowElementBean" + "!" + FlowElementLocal.class.getName();

	public List<FlowElementDto> findFlowElement(Long diagramId, Long shapeId) throws BusinessException;
	
	public boolean deleteFlowElement(long id) throws BusinessException;

	public FlowElementDto editFlowElement(FlowElementDto FlowElement) throws BusinessException;

	public FlowElementDto newFlowElement(FlowElementDto FlowElement) throws BusinessException;

	public FlowElementDto findByContextId(long id)throws BusinessException;
	
}
