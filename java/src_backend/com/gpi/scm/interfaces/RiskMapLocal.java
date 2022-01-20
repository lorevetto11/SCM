package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.RiskMapDto;
import com.gpi.scm.generic.exceptions.BusinessException;
@Local
public interface RiskMapLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "RiskMapBean" + "!" + RiskMapLocal.class.getName();

	public List<RiskMapDto> findMaps(Long elementId,Long dangerId,Long organizationId) throws BusinessException;

	public RiskMapDto saveMap(RiskMapDto toSave) throws BusinessException;

	public Object editMap(RiskMapDto toSave) throws BusinessException;

	public boolean deleteMap(long id) throws BusinessException;


}
