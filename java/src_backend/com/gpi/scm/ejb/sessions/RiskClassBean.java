package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.RiskClassConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.ejb.entities.RiskClass;
import com.gpi.scm.generic.dtos.RiskClassDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.RiskClassLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class RiskClassBean extends GenericBean implements RiskClassLocal {

	private static final Logger logger = Logger.getLogger(RiskClassBean.class);

	@Override
	public List<RiskClassDto> findClasses(Long organizationId) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			List<RiskClass> entities = (List<RiskClass>) entityManager.createNamedQuery(RiskClass.NQ_CLASSES)
					.setParameter("idOrganization", organizationId).getResultList();
			return RiskClassConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

	@Override
	public RiskClassDto saveClass(RiskClassDto toSave) throws BusinessException {
		
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		if (!organizations.contains(toSave.getOrganization().getId())) {
			throw new NoResultException("No access!");
		}

		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}

		RiskClass riskclass = RiskClassConverter.dtoToEntity(toSave);
		riskclass.setOrganization(organization);

		return RiskClassConverter.entityToDto(entityManager.merge(riskclass), true);
	}

	@Override
	public Object editClass(RiskClassDto toSave) throws BusinessException {
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		if (!organizations.contains(toSave.getOrganization().getId())) {
			throw new NoResultException("No access!");
		}
		
		RiskClass riskclass = entityManager.find(RiskClass.class, toSave.getId());
		if (riskclass == null || riskclass.getDeleted()) {
			throw new NoResultException("RiskClass not found!");
		}
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted() || !organizations.contains(organization.getId())) {
			throw new NoResultException("Organization not found!");
		}

		riskclass.setName(toSave.getName());
		riskclass.setOrder(toSave.getOrder());
		riskclass.setDescription(toSave.getDescription());
		riskclass.setColor(toSave.getColor());
		riskclass.setOrganization(organization);

		return RiskClassConverter.entityToDto(entityManager.merge(riskclass), true);
	}

	@Override
	public boolean deleteClass(long id) throws BusinessException {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
			
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			RiskClass toDelete = (RiskClass) entityManager.createNamedQuery(RiskClass.NQ_CLASS_BY_ID)
					.setParameter("idClass", id).getSingleResult();
			toDelete.setDeleted(true);
			return true;
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return false;

	}

}
