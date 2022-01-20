package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.FloorConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.Context;
import com.gpi.scm.ejb.entities.Floor;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.generic.dtos.FloorDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.FloorLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class FloorBean extends GenericBean implements FloorLocal {
	private static final Logger logger = Logger.getLogger(FloorBean.class);

	@Override
	public List<FloorDto> findFloors(Long organizations) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);

			List<Floor> entities = (List<Floor>) entityManager.createNamedQuery(Floor.NQ_FLOORS_IN_ORGANIZATIONS)
					.setParameter("organizations", organizations).getResultList();
			return FloorConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

	@Override
	public boolean deleteFloor(long id) throws BusinessException {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());

			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			Floor toDelete = (Floor) entityManager.createNamedQuery(Floor.NQ_FLOOR_BY_ID).setParameter("idFloor", id)
					.getSingleResult();
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

	@Override
	public FloorDto editFloor(FloorDto toSave) throws BusinessException {
		
		Floor floor = entityManager.find(Floor.class, toSave.getId());
		if (floor == null || floor.getDeleted()) {
			throw new NoResultException("Floor not found!");
		}
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		if (!organizations.contains(toSave.getOrganization().getId()) || !organizations.contains(floor.getOrganization().getId())) {
			throw new NoResultException("No access!");

		}

		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}

		floor.setOrder(toSave.getOrder());
		floor.setDescription(toSave.getDescription());
		floor.setWidth(toSave.getWidth());
		floor.setHeight(toSave.getHeight());
		floor.setName(toSave.getName());
		floor.setOrganization(organization);

		return FloorConverter.entityToDto(entityManager.merge(floor), true);
	}

	@Override
	public FloorDto newFloor(FloorDto toSave) throws BusinessException {

		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		if (!organizations.contains(toSave.getOrganization().getId())) {
			throw new NoResultException("No access!");

		}
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}

		Floor floor = FloorConverter.dtoToEntity(toSave);

		Context context = new Context();
		context.setClassName(this.getClass().getSimpleName());
		entityManager.persist(context);

		floor.setContext(context);
		floor.setOrganization(organization);
		floor.setOrder(toSave.getOrder());

		Floor tmp = entityManager.merge(floor);
		return FloorConverter.entityToDto(tmp, true);
	}

	@Override
	public FloorDto findFloorsById(long id) throws BusinessException {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());

			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			Floor floor = (Floor) entityManager.createNamedQuery(Floor.NQ_FLOOR_BY_ID).setParameter("idFloor", id)
					.getSingleResult();
			return FloorConverter.entityToDto(floor, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return null;
	}

}
