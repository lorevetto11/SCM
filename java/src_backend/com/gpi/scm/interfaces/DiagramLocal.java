package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.DiagramDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface DiagramLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "DiagramBean" + "!" + DiagramLocal.class.getName();

	public List<DiagramDto> findDiagram(Long organizations) throws BusinessException;
	
	public boolean deleteDiagram(long id) throws BusinessException;

	public DiagramDto editDiagram(DiagramDto Diagram) throws BusinessException;

	public DiagramDto newDiagram(DiagramDto Diagram) throws BusinessException;


}
