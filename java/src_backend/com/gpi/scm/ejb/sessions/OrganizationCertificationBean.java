package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.OrganizationCertificationConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.Context;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.ejb.entities.OrganizationCertification;
import com.gpi.scm.generic.dtos.OrganizationCertificationDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.OrganizationCertificationLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)

public class OrganizationCertificationBean extends GenericBean implements OrganizationCertificationLocal {
	private static final Logger logger = Logger.getLogger(OrganizationCertificationBean.class);

	@Override
	public OrganizationCertificationDto saveCertification(OrganizationCertificationDto toSave) throws BusinessException {
		
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if(organization==null || organization.getDeleted())
		{
			throw new NoResultException("Organization not found!");
		}
		OrganizationCertification certification = OrganizationCertificationConverter.dtoToEntity(toSave);
		certification.setOrganization(organization);
		Context context = new Context();
		context.setClassName(this.getClass().getSimpleName());
		entityManager.persist(context);
		certification.setContext(context);
		OrganizationCertification tmp = entityManager.merge(certification);
		
		return OrganizationCertificationConverter.entityToDto(tmp, true);
	}

	
	@Override
	public OrganizationCertificationDto editCertification(OrganizationCertificationDto toSave) throws BusinessException {
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		if(!organizations.contains(toSave.getOrganization().getId()))
		{
			throw new NoResultException("No access!");
		}
		OrganizationCertification certification = entityManager.find(OrganizationCertification.class, toSave.getId());
		if(certification==null||certification.getDeleted() )
		{
			throw new NoResultException("Certification not found");
		}
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if(organization==null || organization.getDeleted()||!organizations.contains(organization.getId()))
		{
			throw new NoResultException("Organization not found!");
		}
		Context context = null;
		if (toSave.getContext() != null) {
			context = entityManager.find(Context.class, toSave.getContext().getId());
			if (context == null || context.getDeleted()) {
				throw new NoResultException("Context not found");
			}
		}
		certification.setContext(context);
		certification.setName(toSave.getName());
		certification.setDescription(toSave.getDescription());
		certification.setOrganization(organization);
		certification.setAuthority(toSave.getAuthority());
		certification.setDate(toSave.getDate());
		certification.setExpiryDate(toSave.getExpiryDate());
		
		OrganizationCertification tmp = entityManager.merge(certification);
		
		return OrganizationCertificationConverter.entityToDto(tmp, true);
	}

	@Override
	public boolean deleteCertification(long id) throws BusinessException {
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		OrganizationCertification toDelete = entityManager.find(OrganizationCertification.class, id);
		if(toDelete==null || toDelete.getDeleted() || !organizations.contains(toDelete.getOrganization().getId()))
		{
			return false;
		}
		toDelete.setDeleted(true);
		return true;
	}

	@Override
	public List<OrganizationCertificationDto> findCertifications(List<Long> organizations) throws BusinessException {
		
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);

			List<OrganizationCertification> entities = (List<OrganizationCertification>) entityManager
					.createNamedQuery(OrganizationCertification.NQ_ORGANIZATIONCERTIFICATIONS).setParameter("organizations", organizations)
					.getResultList();
			return OrganizationCertificationConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

}
