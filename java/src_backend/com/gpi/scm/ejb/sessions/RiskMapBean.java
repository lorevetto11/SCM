package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.RiskMapConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.Danger;
import com.gpi.scm.ejb.entities.FlowElement;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.ejb.entities.RiskMap;
import com.gpi.scm.generic.dtos.RiskMapDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.RiskMapLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class RiskMapBean extends GenericBean implements RiskMapLocal {

	private static final Logger logger = Logger.getLogger(RiskMapBean.class);

	@Override
	public List<RiskMapDto> findMaps(Long elementId, Long dangerId, Long organizationId) throws BusinessException {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());

			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			Query q = null;

			if (dangerId != null && elementId != null) {
				q = entityManager.createNamedQuery(RiskMap.NQ_RISK_MAPS_BY_ELEMENT_DANGER)
						.setParameter("elementId", elementId).setParameter("dangerId", dangerId)
						.setParameter("organizations", organizations);
			} else if (dangerId != null) {
				q = entityManager.createNamedQuery(RiskMap.NQ_RISK_MAPS_BY_DANGER).setParameter("dangerId", dangerId)
						.setParameter("organizations", organizations);
			} else if (elementId != null) { // if(shapeId != null) {
				q = entityManager.createNamedQuery(RiskMap.NQ_RISK_MAPS_BY_ELEMENT).setParameter("elementId", elementId)
						.setParameter("organizations", organizations);
			} else {
				q = entityManager.createNamedQuery(RiskMap.NQ_RISK_MAPS_BY_ORGANIZATION)
						.setParameter("organizationId", organizationId).setParameter("organizations", organizations);
			}
			List<RiskMap> entities = q.getResultList();

			return RiskMapConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

	@Override
	public RiskMapDto saveMap(RiskMapDto toSave) throws BusinessException {

		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		if (!organizations.contains(toSave.getOrganization().getId())) {
			throw new NoResultException("No access!");
		}

		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}
		Danger danger = entityManager.find(Danger.class, toSave.getDanger().getId());
		if (danger == null || danger.getDeleted()) {
			throw new NoResultException("Danger not found!");
		}
		FlowElement element = entityManager.find(FlowElement.class, toSave.getElement().getId());
		if (element == null || element.getDeleted()) {
			throw new NoResultException("FlowElement not found!");
		}

		RiskMap riskmap = RiskMapConverter.dtoToEntity(toSave);
		riskmap.setOrganization(organization);
		riskmap.setDanger(danger);
		riskmap.setElement(element);

		return RiskMapConverter.entityToDto(entityManager.merge(riskmap), true);
	}

	@Override
	public Object editMap(RiskMapDto toSave) throws BusinessException {
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		if (!organizations.contains(toSave.getOrganization().getId())) {
			throw new NoResultException("No access!");
		}

		RiskMap riskmap = entityManager.find(RiskMap.class, toSave.getId());
		if (riskmap == null || riskmap.getDeleted()) {
			throw new NoResultException("RiskMap not found!");
		}
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted() || !organizations.contains(organization.getId())) {
			throw new NoResultException("Organization not found!");
		}
		Danger danger = entityManager.find(Danger.class, toSave.getDanger().getId());
		if (danger == null || danger.getDeleted()) {
			throw new NoResultException("Danger not found!");
		}
		FlowElement element = entityManager.find(FlowElement.class, toSave.getElement().getId());
		if (element == null || element.getDeleted()) {
			throw new NoResultException("FlowElement not found!");
		}

		riskmap.setValue(toSave.getValue());
		riskmap.setOrganization(organization);
		riskmap.setDanger(danger);
		riskmap.setElement(element);

		return RiskMapConverter.entityToDto(entityManager.merge(riskmap), true);
	}

	@Override
	public boolean deleteMap(long id) throws BusinessException {
		try {
			RiskMap toDelete = entityManager.find(RiskMap.class, id);
			if (toDelete == null || toDelete.getDeleted()) {
				return false;
			}
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