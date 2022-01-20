package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.CleaningConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.Floor;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Layout;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.ejb.entities.PrerequisiteType;
import com.gpi.scm.ejb.entities.Shape;
import com.gpi.scm.ejb.entities.Cleaning;
import com.gpi.scm.ejb.entities.Context;
import com.gpi.scm.generic.dtos.BasePrerequisiteDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.CleaningLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class CleaningBean extends GenericBean implements CleaningLocal {
	private static final Logger logger = Logger.getLogger(CleaningBean.class);

	@Override
	public List<BasePrerequisiteDto> findCleaning(Long organizations) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			List<Cleaning> entities = (List<Cleaning>) entityManager
					.createNamedQuery(Cleaning.NQ_CLEANINGS_IN_ORGANIZATIONS)
					.setParameter("organizations", organizations).getResultList();
			return CleaningConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

	@Override
	public BasePrerequisiteDto editCleaning(BasePrerequisiteDto toSave) throws BusinessException {
		Cleaning cleaning = entityManager.find(Cleaning.class, toSave.getId());
		if (cleaning == null) {
			throw new NoResultException("No Cleaning Found!");
		}
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		if (!organizations.contains(toSave.getOrganization().getId()) || !organizations.contains(cleaning.getOrganization().getId())) {
			throw new NoResultException("No access!");

		}
		PrerequisiteType ptype = entityManager.find(PrerequisiteType.class, toSave.getPrerequisiteType().getId());
		if (ptype == null || ptype.getDeleted()) {
			throw new NoResultException("PrerequisiteType not found!");
		}

		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}

		Layout layout = entityManager.find(Layout.class, toSave.getLayout().getId());
		if (layout == null || layout.getDeleted()) {
			throw new NoResultException("Layout not found!");
		}

		Floor floor = entityManager.find(Floor.class, toSave.getFloor().getId());
		if (floor == null || floor.getDeleted()) {
			throw new NoResultException("Floor not found!");
		}

		Shape shape = entityManager.find(Shape.class, toSave.getShape().getId());
		if (shape == null || shape.getDeleted()) {
			throw new NoResultException("Shape not found!");
		}

		cleaning.setPrerequisiteType(ptype);
		cleaning.setLayout(layout);
		cleaning.setOrganization(organization);
		cleaning.setFloor(floor);
		cleaning.setShape(shape);
		cleaning.setName(toSave.getName());
		cleaning.setDescription(toSave.getDescription());

		Cleaning tmp = entityManager.merge(cleaning);
		return CleaningConverter.entityToDto(tmp, true);
	}

	@Override
	public BasePrerequisiteDto newCleaning(BasePrerequisiteDto toSave) throws BusinessException {

		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		if (!organizations.contains(toSave.getOrganization().getId())) {
			throw new NoResultException("No access!");

		}
		
		PrerequisiteType ptype = entityManager.find(PrerequisiteType.class, toSave.getPrerequisiteType().getId());
		if (ptype == null || ptype.getDeleted()) {
			throw new NoResultException("PrerequisiteType not found!");
		}
		

		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}
		

		Layout layout = entityManager.find(Layout.class, toSave.getLayout().getId());
		if (layout == null || layout.getDeleted()) {
			throw new NoResultException("Layout not found!");
		}

		Floor floor = entityManager.find(Floor.class, toSave.getFloor().getId());
		if (floor == null || floor.getDeleted()) {
			throw new NoResultException("Floor not found!");
		}

		Shape shape = entityManager.find(Shape.class, toSave.getShape().getId());
		if (shape == null || shape.getDeleted()) {
			throw new NoResultException("Shape not found!");
		}

		Cleaning cleaning = CleaningConverter.dtoToEntity(toSave);

		Context context = new Context();
		context.setClassName(this.getClass().getSimpleName());
		entityManager.persist(context);

		cleaning.setContext(context);
		cleaning.setLayout(layout);
		cleaning.setOrganization(organization);
		cleaning.setFloor(floor);
		cleaning.setShape(shape);
		cleaning.setPrerequisiteType(ptype);

		Cleaning tmp = entityManager.merge(cleaning);
		return CleaningConverter.entityToDto(tmp, true);
	}

	@Override
	public boolean deleteCleaning(long id) throws BusinessException {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());

			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			Cleaning toDelete = (Cleaning) entityManager.createNamedQuery(Cleaning.NQ_CLEANINGS_BY_ID)
					.setParameter("idCleaning", id).getSingleResult();
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
	public BasePrerequisiteDto findCleaningById(long id) throws BusinessException {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());

			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);
			Cleaning cleaning = (Cleaning) entityManager.createNamedQuery(Cleaning.NQ_CLEANINGS_BY_ID)
					.setParameter("idCleaning", id).getSingleResult();
			return CleaningConverter.entityToDto(cleaning, true);

		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
			// throw new NoResultException();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return null;
	}
	
	@Override
	public BasePrerequisiteDto findByContextId(long id,List<Long> organizations) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			Cleaning cleaning = (Cleaning) entityManager.createNamedQuery(Cleaning.NQ_CLEANINGS_BY_CONTEXT_ID)
					.setParameter("idContext", id).getSingleResult();
			return CleaningConverter.entityToDto(cleaning, true);

		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return null;
	}

}
