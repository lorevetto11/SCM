package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.NonComplianceDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface NonComplianceLocal {

	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "NonComplianceBean" + "!"
			+ NonComplianceLocal.class.getName();

	public NonComplianceDto saveCompliance(NonComplianceDto toSave) throws BusinessException;

	public NonComplianceDto editCompliance(NonComplianceDto toSave) throws BusinessException;

	public boolean deleteCompliance(long id) throws BusinessException;

	public List<NonComplianceDto> findCompliances(Long organizationId,Long systemCheckRequirementId,
			Long processCheckId, Long prerequisisteContextId ) throws BusinessException;

}
