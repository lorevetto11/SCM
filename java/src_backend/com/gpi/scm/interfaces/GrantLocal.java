package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.GrantDto;
import com.gpi.scm.generic.dtos.UserProfileDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface GrantLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "GrantBean" + "!" + GrantLocal.class.getName();

	public List<GrantDto> findGrants() throws BusinessException;

	public UserProfileDto setGrants(List<Long> organizations, long id, List<Long> grants) throws BusinessException;

}
