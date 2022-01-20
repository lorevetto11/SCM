package com.gpi.scm.ejb.sessions;

import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.gpi.scm.converters.NonComplianceConverter;
import com.gpi.scm.converters.UserConverter;
import com.gpi.scm.ejb.entities.Context;
import com.gpi.scm.ejb.entities.NonCompliance;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.ejb.entities.ProcessCheck;
import com.gpi.scm.ejb.entities.SystemCheckRequirement;
import com.gpi.scm.ejb.entities.User;
import com.gpi.scm.generic.dtos.NonComplianceDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.NonComplianceLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)

public class NonComplianceBean extends GenericBean implements NonComplianceLocal {
	private static final Logger logger = Logger.getLogger(NonComplianceBean.class);

	@Override
	public NonComplianceDto saveCompliance(NonComplianceDto toSave) throws BusinessException {
		NonCompliance noncompliance = NonComplianceConverter.dtoToEntity(toSave);
		int atLeastOne=0;
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found");
		}

		if (toSave.getProcessCheck() != null) {
			ProcessCheck processcheck = entityManager.find(ProcessCheck.class, toSave.getProcessCheck().getId());
			if (processcheck == null || processcheck.getDeleted()) {
				throw new NoResultException("ProcessCheck not found");
			}
			noncompliance.setProcessCheck(processcheck);
			atLeastOne++;
		}
		if (toSave.getSystemCheckRequirement() != null) {
			SystemCheckRequirement systemCheckRequirement = entityManager.find(SystemCheckRequirement.class,
					toSave.getSystemCheckRequirement().getId());
			if (systemCheckRequirement == null || systemCheckRequirement.getDeleted()) {
				throw new NoResultException("SystemCheck Requirement not found");
			}
			noncompliance.setSystemCheckRequirement(systemCheckRequirement);
			atLeastOne++;
		}
		
		if (toSave.getContext() != null) {
			Context context = entityManager.find(Context.class, toSave.getContext().getId());
			if (context == null || context.getDeleted()) {
				throw new NoResultException("Context not found");

			}
			noncompliance.setContext(context);
			atLeastOne++;
		}
		noncompliance.setOrganization(organization);
		if(atLeastOne==0)
		{
			throw new NoResultException("Noncompliace is not associated with any kind of context");
		}
		if(toSave.getCloseDate()!=null)
			noncompliance.setCloseUser(entityManager.find(User.class, UserContextHolder.getUser().getId()));

		return NonComplianceConverter.entityToDto(entityManager.merge(noncompliance), true);

	}

	@Override
	public NonComplianceDto editCompliance(NonComplianceDto toSave) throws BusinessException {


		NonCompliance noncompliance = entityManager.find(NonCompliance.class, toSave.getId());
		if (noncompliance == null || noncompliance.getDeleted()) {
			throw new NoResultException("NonCompliance not found");
		}

		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found");
		}
	

		noncompliance.setDescription(toSave.getDescription());
		noncompliance.setCauses(toSave.getCauses());
		noncompliance.setChecks(toSave.getChecks());
		noncompliance.setCorrections(toSave.getCorrections());
		noncompliance.setCloseDate(toSave.getCloseDate());
		if(toSave.getCloseDate()!=null)
			noncompliance.setCloseUser(entityManager.find(User.class, UserContextHolder.getUser().getId()));

		noncompliance.setStartDate(toSave.getStartDate());
		noncompliance.setRetrieval(toSave.getRetrieval());
		noncompliance.setTreatment(toSave.getTreatment());
		noncompliance.setOrganization(organization);

		return NonComplianceConverter.entityToDto(entityManager.merge(noncompliance), true);
	}

	@Override
	public boolean deleteCompliance(long id) throws BusinessException {


		NonCompliance toDelete = entityManager.find(NonCompliance.class, id);
		if (toDelete == null || toDelete.getDeleted()) {
			return false;
		}
		toDelete.setDeleted(true);
		return true;
	}

	@Override
	public List<NonComplianceDto> findCompliances(Long organizationId, Long systemCheckRequirementId, Long processCheckId,
			Long prerequisisteContextId) throws BusinessException {

		Session ses = (Session) entityManager.getDelegate();
		Criteria criteria = ses.createCriteria(NonCompliance.class, "noncompliance");
		if (organizationId != null) {

			criteria.add(Restrictions.eq("organization.id", organizationId));

		}
		if (systemCheckRequirementId != null) {
			
			criteria.add(Restrictions.eq("systemCheckRequirement.id", systemCheckRequirementId));

		}
		if (processCheckId != null) {

			criteria.add(Restrictions.eq("processCheck.id", processCheckId));

		}
		if (prerequisisteContextId != null) {
			criteria.add(Restrictions.eqOrIsNull("context.id", prerequisisteContextId));
		}

		List<NonCompliance> results = (List<NonCompliance>) criteria.list();
		return NonComplianceConverter.entityToDto(results, true);
	}

}
