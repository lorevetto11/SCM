package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.OrganizationDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.OrganizationLocal;

public class OrganizationDelegate extends GenericDelegate {

	private static final Logger logger = Logger.getLogger(OrganizationDelegate.class);

	private static OrganizationLocal getBean() throws NamingException {
		return (OrganizationLocal) getContext().lookup(OrganizationLocal.JNDI_NAME);
	}

	public static List<Long> organizationsIdsTree(long rootOrganizationId) {
		try {
			return getBean().organizationsIdsTree(rootOrganizationId);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	public static List<Long> organizationsIdsReverseTree(long leafOrganizationId) {
		try {
			return getBean().organizationsIdsReverseTree(leafOrganizationId);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}


	public static List<OrganizationDto> findOrganizations(List<Long> organizations) {
		try {
			return getBean().findOrganizations(organizations);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	public static OrganizationDto findOrganizationById(long id) {
		OrganizationDto obj = null;
		try {
			obj = getBean().findOrganizationById(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static OrganizationDto editOrganization(OrganizationDto organization) {
		OrganizationDto obj = null;
		try {
			obj = getBean().editOrganization(organization);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static OrganizationDto saveOrganization(OrganizationDto organization) {
		OrganizationDto obj = null;
		try {
			obj = getBean().saveOrganization(organization);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static boolean deleteById(long id) {
		try {
			return getBean().deleteOrganizationById(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return false;
	}

	
}
