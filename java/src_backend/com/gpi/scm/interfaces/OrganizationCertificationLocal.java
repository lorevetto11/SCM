package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.OrganizationCertificationDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface OrganizationCertificationLocal {

	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "OrganizationCertificationBean" + "!" + OrganizationCertificationLocal.class.getName();

	public OrganizationCertificationDto saveCertification(OrganizationCertificationDto organization) throws BusinessException;

	public OrganizationCertificationDto editCertification(OrganizationCertificationDto organization)throws BusinessException;

	public boolean deleteCertification(long id)throws BusinessException;

	public List<OrganizationCertificationDto> findCertifications(List<Long> organizations)throws BusinessException;

}
