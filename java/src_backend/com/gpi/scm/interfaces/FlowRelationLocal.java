package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.FlowRelationDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface FlowRelationLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "FlowRelationBean" + "!" + FlowRelationLocal.class.getName();

	public List<FlowRelationDto> findFlowRelation(Long diagramId) throws BusinessException;
	
	public boolean deleteFlowRelation(long id) throws BusinessException;

	public FlowRelationDto editFlowRelation(FlowRelationDto FlowRelation) throws BusinessException;

	public FlowRelationDto newFlowRelation(FlowRelationDto FlowRelation) throws BusinessException;


}
