package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.OrganizationDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface OrganizationLocal {

	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "OrganizationBean" + "!" + OrganizationLocal.class.getName();

	public List<Long> organizationsIdsTree(long rootOrganizationId) throws BusinessException;

	public List<Long> organizationsIdsReverseTree(long leafOrganizationId) throws BusinessException;

	public List<OrganizationDto> findOrganizations(List<Long> organizations) throws BusinessException;


	public OrganizationDto findOrganizationById(long id) throws BusinessException;


	public OrganizationDto editOrganization(OrganizationDto organization) throws BusinessException;


	public OrganizationDto saveOrganization(OrganizationDto organization) throws BusinessException;
	public boolean deleteOrganizationById(long id) throws BusinessException;
}
