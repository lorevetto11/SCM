package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.OrganizationPlaceConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.ejb.entities.OrganizationPlace;
import com.gpi.scm.generic.dtos.OrganizationPlaceDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.OrganizationPlaceLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)

public class OrganizationPlaceBean extends GenericBean implements OrganizationPlaceLocal {
	private static final Logger logger = Logger.getLogger(OrganizationPlaceBean.class);

	@Override
	public OrganizationPlaceDto savePlace(OrganizationPlaceDto toSave) throws BusinessException {
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if(organization==null || organization.getDeleted())
		{
			throw new NoResultException("Organization not found!");
		}
		OrganizationPlace place = OrganizationPlaceConverter.dtoToEntity(toSave);
		place.setOrganization(organization);
		OrganizationPlace tmp = entityManager.merge(place);
		
		return OrganizationPlaceConverter.entityToDto(tmp, true);
	}

	
	@Override
	public OrganizationPlaceDto editPlace(OrganizationPlaceDto toSave) throws BusinessException {
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		if(!organizations.contains(toSave.getOrganization().getId()))
		{
			throw new NoResultException("No access!");
		}
		OrganizationPlace place = entityManager.find(OrganizationPlace.class, toSave.getId());
		if(place==null||place.getDeleted())
		{
			throw new NoResultException("Place not found");
		}
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if(organization==null || organization.getDeleted() || !organizations.contains(organization.getId()))
		{
			throw new NoResultException("Organization not found!");
		}
		place.setAddress(toSave.getAddress());
		place.setName(toSave.getName());
		place.setDescription(toSave.getDescription());
		place.setOrganization(organization);
		OrganizationPlace tmp = entityManager.merge(place);
		
		return OrganizationPlaceConverter.entityToDto(tmp, true);
	}

	@Override
	public boolean deletePlace(long id) throws BusinessException {
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		OrganizationPlace toDelete = entityManager.find(OrganizationPlace.class, id);
		if(toDelete==null || toDelete.getDeleted() || !organizations.contains(toDelete.getOrganization().getId()))
		{
			return false;
		}
		toDelete.setDeleted(true);
		return true;
	}

	@Override
	public List<OrganizationPlaceDto> findPlaces(List<Long> organizations) throws BusinessException {
		
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);

			List<OrganizationPlace> entities = (List<OrganizationPlace>) entityManager
					.createNamedQuery(OrganizationPlace.NQ_ORGANIZATIONPLACES).setParameter("organizations", organizations)
					.getResultList();
			return OrganizationPlaceConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

}
