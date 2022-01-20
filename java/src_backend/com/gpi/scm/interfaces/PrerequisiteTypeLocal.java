package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.PrerequisiteTypeDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface PrerequisiteTypeLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "PrerequisiteTypeBean" + "!" + PrerequisiteTypeLocal.class.getName();

	public List<PrerequisiteTypeDto> findPrerequisiteType() throws BusinessException;


}
