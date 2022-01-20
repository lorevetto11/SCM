package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.WasteDisposalConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.Context;
import com.gpi.scm.ejb.entities.Floor;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Layout;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.ejb.entities.PrerequisiteType;
import com.gpi.scm.ejb.entities.Shape;
import com.gpi.scm.ejb.entities.WasteDisposal;
import com.gpi.scm.generic.dtos.BasePrerequisiteDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.WasteDisposalLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class WasteDisposalBean extends GenericBean implements WasteDisposalLocal {
	private static final Logger logger = Logger.getLogger(WasteDisposalBean.class);

	@Override
	public List<BasePrerequisiteDto> findWasteDisposal(Long organizations) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			List<WasteDisposal> entities = (List<WasteDisposal>) entityManager
					.createNamedQuery(WasteDisposal.NQ_WASTEDISPOSALS_IN_ORGANIZATIONS)
					.setParameter("organizations", organizations).getResultList();
			return WasteDisposalConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}
	
	@Override
	public BasePrerequisiteDto findByContextId(long id,List<Long> organizations) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			WasteDisposal disposal = (WasteDisposal) entityManager.createNamedQuery(WasteDisposal.NQ_WASTEDISPOSAL_BY_CONTEXT_ID)
					.setParameter("idContext", id).getSingleResult();
			return WasteDisposalConverter.entityToDto(disposal, true);

		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return null;
	}

	@Override
	public BasePrerequisiteDto editWasteDisposal(BasePrerequisiteDto toSave) throws BusinessException {
		
		WasteDisposal disposal = entityManager.find(WasteDisposal.class, toSave.getId());
		if (disposal == null || disposal.getDeleted()) {
			throw new NoResultException("No WasteDisposal Found!");
		}
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
		if (organization == null || organization.getDeleted() || organizations.contains(organization.getId())) {
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

		disposal.setPrerequisiteType(ptype);
		disposal.setLayout(layout);
		disposal.setOrganization(organization);
		disposal.setFloor(floor);
		disposal.setShape(shape);
		disposal.setName(toSave.getName());
		disposal.setDescription(toSave.getDescription());

		WasteDisposal tmp = entityManager.merge(disposal);
		return WasteDisposalConverter.entityToDto(tmp, true);
	}

	@Override
	public BasePrerequisiteDto newWasteDisposal(BasePrerequisiteDto toSave) throws BusinessException {

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

		WasteDisposal disposal = WasteDisposalConverter.dtoToEntity(toSave);

		Context context = new Context();
		context.setClassName(this.getClass().getSimpleName());
		entityManager.persist(context);

		disposal.setContext(context);
		disposal.setLayout(layout);
		disposal.setOrganization(organization);
		disposal.setFloor(floor);
		disposal.setShape(shape);
		disposal.setPrerequisiteType(ptype);

		WasteDisposal tmp = entityManager.merge(disposal);
		return WasteDisposalConverter.entityToDto(tmp, true);

	}

	@Override
	public boolean deleteWasteDisposal(long id) throws BusinessException {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());

			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			WasteDisposal toDelete = (WasteDisposal) entityManager
					.createNamedQuery(WasteDisposal.NQ_WASTEDISPOSAL_BY_ID).setParameter("idWasteDisposal", id)
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
	public BasePrerequisiteDto findDisposalsById(long id) throws BusinessException {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
	
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			WasteDisposal waste = (WasteDisposal) entityManager.createNamedQuery(WasteDisposal.NQ_WASTEDISPOSAL_BY_ID)
					.setParameter("idWasteDisposal", id).getSingleResult();
			return WasteDisposalConverter.entityToDto(waste, true);

		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return null;
	}

}
