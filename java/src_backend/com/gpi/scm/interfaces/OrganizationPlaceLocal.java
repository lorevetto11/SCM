package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.OrganizationPlaceDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface OrganizationPlaceLocal {

	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "OrganizationPlaceBean" + "!" + OrganizationPlaceLocal.class.getName();

	public OrganizationPlaceDto savePlace(OrganizationPlaceDto organization) throws BusinessException;

	public OrganizationPlaceDto editPlace(OrganizationPlaceDto organization)throws BusinessException;

	public boolean deletePlace(long id)throws BusinessException;

	public List<OrganizationPlaceDto> findPlaces(List<Long> organizations)throws BusinessException;

}
