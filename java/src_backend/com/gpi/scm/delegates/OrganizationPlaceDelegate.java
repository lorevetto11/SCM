package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.OrganizationPlaceDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.OrganizationPlaceLocal;

public class OrganizationPlaceDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(UserDelegate.class);

	private static OrganizationPlaceLocal getBean() throws NamingException {
		return (OrganizationPlaceLocal) getContext().lookup(OrganizationPlaceLocal.JNDI_NAME);
	}

	public static List<OrganizationPlaceDto> findOrganizationPlaces(List<Long> organizations) {
		List<OrganizationPlaceDto> obj = null;
		try {
			obj = getBean().findPlaces(organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static OrganizationPlaceDto saveOrganizationPlace(OrganizationPlaceDto organization) {
		OrganizationPlaceDto obj = null;
		try {
			obj = getBean().savePlace(organization);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static OrganizationPlaceDto editOrganizationPlace(OrganizationPlaceDto organization) {
		OrganizationPlaceDto obj = null;
		try {
			obj = getBean().editPlace(organization);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static boolean deleteById(long id) {
		boolean obj = false ;
		try {
			obj = getBean().deletePlace(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

}
