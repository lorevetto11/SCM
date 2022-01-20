package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.PestControlConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.Context;
import com.gpi.scm.ejb.entities.Floor;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Layout;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.ejb.entities.PestControl;
import com.gpi.scm.ejb.entities.PrerequisiteType;
import com.gpi.scm.ejb.entities.Shape;
import com.gpi.scm.generic.dtos.BasePrerequisiteDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.PestControlLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class PestControlBean extends GenericBean implements PestControlLocal {
	private static final Logger logger = Logger.getLogger(PestControlBean.class);

	@Override
	public List<BasePrerequisiteDto> findPestControl(Long organizations) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			List<PestControl> entities = (List<PestControl>) entityManager
					.createNamedQuery(PestControl.NQ_PESTCONTROLS_IN_ORGANIZATIONS)
					.setParameter("organizations", organizations).getResultList();
			return PestControlConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

	@Override
	public BasePrerequisiteDto editPestControl(BasePrerequisiteDto toSave) throws BusinessException {
		
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		if(!organizations.contains(toSave.getOrganization().getId()))
		{
			throw new NoResultException("No access!");
		}
		PestControl pestCtrl = entityManager.find(PestControl.class, toSave.getId());
		if (pestCtrl == null || pestCtrl.getDeleted()) {
			throw new NoResultException("No PestControl Found!");
		}

		PrerequisiteType ptype = entityManager.find(PrerequisiteType.class, toSave.getPrerequisiteType().getId());
		if (ptype == null || ptype.getDeleted()) {
			throw new NoResultException("PrerequisiteType not found!");
		}

		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted() || !organizations.contains(organization.getId())) {
			throw new NoResultException("Organization not found!");
		}

		Layout layout = entityManager.find(Layout.class, toSave.getLayout().getId());
		if (layout == null || layout.getDeleted()) {
			throw new NoResultException("Context not found!");
		}

		Floor floor = entityManager.find(Floor.class, toSave.getFloor().getId());
		if (floor == null || floor.getDeleted()) {
			throw new NoResultException("Floor not found!");
		}

		Shape shape = entityManager.find(Shape.class, toSave.getShape().getId());
		if (shape == null || shape.getDeleted()) {
			throw new NoResultException("Shape not found!");
		}

		pestCtrl.setPrerequisiteType(ptype);
		pestCtrl.setLayout(layout);
		pestCtrl.setOrganization(organization);
		pestCtrl.setFloor(floor);
		pestCtrl.setShape(shape);
		pestCtrl.setName(toSave.getName());
		pestCtrl.setDescription(toSave.getDescription());

		PestControl tmp = entityManager.merge(pestCtrl);
		return PestControlConverter.entityToDto(tmp, true);
	}

	@Override
	public BasePrerequisiteDto newPestControl(BasePrerequisiteDto toSave) throws BusinessException {
		
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		if(!organizations.contains(toSave.getOrganization().getId()))
		{
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
			throw new NoResultException("Context not found!");
		}

		Floor floor = entityManager.find(Floor.class, toSave.getFloor().getId());
		if (floor == null || floor.getDeleted()) {
			throw new NoResultException("Floor not found!");
		}

		Shape shape = entityManager.find(Shape.class, toSave.getShape().getId());
		if (shape == null || shape.getDeleted()) {
			throw new NoResultException("Shape not found!");
		}

		PestControl pestCtrl = PestControlConverter.dtoToEntity(toSave);

		Context context = new Context();
		context.setClassName(this.getClass().getSimpleName());
		entityManager.persist(context);

		pestCtrl.setContext(context);
		pestCtrl.setLayout(layout);
		pestCtrl.setOrganization(organization);
		pestCtrl.setFloor(floor);
		pestCtrl.setShape(shape);
		pestCtrl.setPrerequisiteType(ptype);

		PestControl tmp = entityManager.merge(pestCtrl);
		return PestControlConverter.entityToDto(tmp, true);
	}

	@Override
	public boolean deletePestControl(long id) throws BusinessException {
		try {
			
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
			
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			PestControl toDelete = (PestControl) entityManager.createNamedQuery(PestControl.NQ_PESTCONTROL_BY_ID)
					.setParameter("idPestControl", id).getSingleResult();
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
	public BasePrerequisiteDto findPestControlById(long id, List<Long> organizations) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			PestControl pestCtrl = (PestControl) entityManager.createNamedQuery(PestControl.NQ_PESTCONTROL_BY_ID)
					.setParameter("idPestControl", id).getSingleResult();
			return PestControlConverter.entityToDto(pestCtrl, true);

		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
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

			PestControl pestControl = (PestControl) entityManager.createNamedQuery(PestControl.NQ_PESTCONTROL_BY_CONTEXT_ID)
					.setParameter("idContext", id).getSingleResult();
			return PestControlConverter.entityToDto(pestControl, true);

		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return null;
	}
	
}
