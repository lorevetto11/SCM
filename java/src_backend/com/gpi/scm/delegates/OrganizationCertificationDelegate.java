package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.OrganizationCertificationDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.OrganizationCertificationLocal;

public class OrganizationCertificationDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(UserDelegate.class);

	private static OrganizationCertificationLocal getBean() throws NamingException {
		return (OrganizationCertificationLocal) getContext().lookup(OrganizationCertificationLocal.JNDI_NAME);
	}

	public static List<OrganizationCertificationDto> findOrganizationCertifications(List<Long> organizations) {
		List<OrganizationCertificationDto> obj = null;
		try {
			obj = getBean().findCertifications(organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static OrganizationCertificationDto saveOrganizationCertification(OrganizationCertificationDto organization) {
		OrganizationCertificationDto obj = null;
		try {
			obj = getBean().saveCertification(organization);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static OrganizationCertificationDto editOrganizationCertification(OrganizationCertificationDto organization) {
		OrganizationCertificationDto obj = null;
		try {
			obj = getBean().editCertification(organization);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static boolean deleteById(long id) {
		boolean obj = false ;
		try {
			obj = getBean().deleteCertification(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

}
