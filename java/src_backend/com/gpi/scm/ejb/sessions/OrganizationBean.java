package com.gpi.scm.ejb.sessions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.OrganizationConverter;
import com.gpi.scm.ejb.entities.Context;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.generic.dtos.OrganizationDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.OrganizationLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class OrganizationBean extends GenericBean implements OrganizationLocal {

	private static final Logger logger = Logger.getLogger(OrganizationBean.class);

	@Override
	public List<Long> organizationsIdsTree(long rootOrganizationId) throws BusinessException {
		try {
			List<BigDecimal> ids = (List<BigDecimal>) entityManager
					.createNativeQuery("Select id from organization_tree(:idOrganization)")
					.setParameter("idOrganization", rootOrganizationId).getResultList();

			return ids.stream().map(bd -> bd.longValue()).collect(Collectors.toList());
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

	@Override
	public List<Long> organizationsIdsReverseTree(long leafOrganizationId) throws BusinessException {
		try {
			List<BigDecimal> ids = (List<BigDecimal>) entityManager
					.createNativeQuery("Select id from organization_reverse_tree(:idOrganization)")
					.setParameter("idOrganization", leafOrganizationId).getResultList();

			return ids.stream().map(bd -> bd.longValue()).collect(Collectors.toList());
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}


	@Override
	public List<OrganizationDto> findOrganizations(List<Long> organizations) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);

			List<Organization> entities = (List<Organization>) entityManager
					.createNamedQuery(Organization.NQ_ORGANIZATIONS).setParameter("organizations", organizations)
					.getResultList();
			return OrganizationConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

	@Override
	public OrganizationDto findOrganizationById(long id) throws BusinessException {
		try {

			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			Organization org = (Organization) entityManager.createNamedQuery(Organization.NQ_ORG_BY_ID)
					.setParameter("idOrg", id).getSingleResult();
			return OrganizationConverter.entityToDto(org, true);

		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);

		}
		return null;
	}

	@Override
	public OrganizationDto editOrganization(OrganizationDto toSave) throws BusinessException {
		Organization organization = entityManager.find(Organization.class, toSave.getId());

		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization child not found!");
		}
		Context context = null;
		if (toSave.getContext() != null) {
			context = entityManager.find(Context.class, toSave.getContext().getId());
			if (context == null || context.getDeleted()) {
				throw new NoResultException("Context not found");
			}
		}

		organization.setEmail(toSave.getEmail());
		organization.setName(toSave.getName());
		organization.setDescription(toSave.getDescription());
		organization.setPhone(toSave.getPhone());
		organization.setLegalResidence(toSave.getLegalResidence());
		organization.setStatus(toSave.getStatus());
		organization.setVatNumber(toSave.getVatNumber());
		organization.setContext(context);

		return OrganizationConverter.entityToDto(entityManager.merge(organization), true);
	}

	@Override
	public OrganizationDto saveOrganization(OrganizationDto toSave) throws BusinessException {
		List<Long> organizations = organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		if(organizations.contains(toSave.getId()))
		{
			throw new NoResultException("No access!");
		}
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted() || !organizations.contains(organization.getId()) ) {
			throw new NoResultException("Organization child not found!");
		}
		Organization org = OrganizationConverter.dtoToEntity(toSave);
		org.setOrganization(organization);
		Context context = new Context();
		context.setClassName(this.getClass().getSimpleName());
		entityManager.persist(context);
		org.setContext(context);

		return OrganizationConverter.entityToDto(entityManager.merge(org), true);
	}

	@Override
	public boolean deleteOrganizationById(long id) throws BusinessException {
		try {

			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);

			Organization orgFather = entityManager.find(Organization.class, id);
			Organization orgChild = entityManager.find(Organization.class, orgFather.getOrganization().getId());

			if (orgChild != null && orgChild.getDeleted() == true) 
																	
			{
				throw new NoResultException("Trying to delete non organization with childs ");
			}

			Organization toDelete = (Organization) entityManager.createNamedQuery(Organization.NQ_ORG_BY_ID)
					.setParameter("idOrg", id).getSingleResult();
			toDelete.setDeleted(true);
			entityManager.merge(toDelete);
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
