package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.NonComplianceDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.NonComplianceLocal;

public class NonComplianceDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(NonComplianceDelegate.class);

	private static NonComplianceLocal getBean() throws NamingException {
		return (NonComplianceLocal) getContext().lookup(NonComplianceLocal.JNDI_NAME);
	}

	public static List<NonComplianceDto> findNonCompliances(Long organizationId, Long systemCheckRequirementId,
			Long processCheckId,Long prerequisisteContextId ) {
		List<NonComplianceDto> obj = null;
		try {
			obj = getBean().findCompliances(organizationId,systemCheckRequirementId, processCheckId,
					prerequisisteContextId);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static NonComplianceDto saveNonCompliance(NonComplianceDto processCompliance) {
		NonComplianceDto obj = null;
		try {
			obj = getBean().saveCompliance(processCompliance);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static NonComplianceDto editNonCompliance(NonComplianceDto processCompliance) {
		NonComplianceDto obj = null;
		try {
			obj = getBean().editCompliance(processCompliance);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static boolean deleteById(long id) {
		boolean obj = false;
		try {
			obj = getBean().deleteCompliance(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

}
