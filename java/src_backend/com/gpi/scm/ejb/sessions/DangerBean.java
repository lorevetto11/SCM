package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.gpi.scm.converters.DangerConverter;
import com.gpi.scm.ejb.entities.Context;
import com.gpi.scm.ejb.entities.Danger;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Material;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.ejb.entities.PrerequisiteType;
import com.gpi.scm.generic.dtos.BasePrerequisiteDto;
import com.gpi.scm.generic.dtos.DangerDto;
import com.gpi.scm.generic.dtos.MaterialDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.DangerLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class DangerBean extends GenericBean implements DangerLocal {
	private static final Logger logger = Logger.getLogger(DangerBean.class);

	@Override
	public boolean deleteDanger(long id) throws BusinessException {
		Danger toDelete = entityManager.find(Danger.class, id);
		if (toDelete == null || toDelete.getDeleted()) {
			return false;
		}
		toDelete.setDeleted(true);
		return true;
	}

	@Override
	public DangerDto editDanger(DangerDto toSave) throws BusinessException {
		Danger danger = entityManager.find(Danger.class, toSave.getId());
		if (danger == null || danger.getDeleted()) {
			throw new NoResultException("Danger not found!");
		}
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}
		if (toSave.getPrerequisiteType() != null) {
			PrerequisiteType type = entityManager.find(PrerequisiteType.class, toSave.getPrerequisiteType().getId());
			if (type == null || type.getDeleted()) {
				throw new NoResultException("PrerequisiteType not found!");
			}
			danger.setPrerequisiteType(type);

		}
		if (toSave.getContext() != null) {
			Context context = entityManager.find(Context.class, toSave.getContext().getId());
			if (context == null || context.getDeleted()) {
				throw new NoResultException("Context not found");
			}
			danger.setContext(context);

		}
		if (toSave.getMaterials() != null && !toSave.getMaterials().isEmpty()) {
			Set<Long> effectiveMaterials = new HashSet<>();
			for (MaterialDto material : toSave.getMaterials()) {
				effectiveMaterials.add(material.getId());
			}
			Session ses = (Session) entityManager.getDelegate();
			Criteria criteria = ses.createCriteria(Material.class, "danger");
			criteria.add(Restrictions.in("id", effectiveMaterials));
			List<Material> results = (List<Material>) criteria.list();
			if (results.size() != effectiveMaterials.size()) {
				throw new NoResultException("Can't find all the Materials");
			}
			danger.setMaterials(results);

		}
		danger.setType(toSave.getType());
		danger.setRisk(toSave.getRisk());
		danger.setName(toSave.getName());
		danger.setDescription(toSave.getDescription());
		danger.setCcp(toSave.isCcp());
		danger.setCriticalLimit(toSave.getCriticalLimit());
		danger.setAcceptanceLimit(toSave.getAcceptanceLimit());
		danger.setControlMeasure(toSave.getControlMeasure());
		danger.setOrganization(organization);

		return DangerConverter.entityToDto(entityManager.merge(danger), true);
	}

	@Override
	public DangerDto newDanger(DangerDto toSave) throws BusinessException {

		Danger danger = DangerConverter.dtoToEntity(toSave);
		if (toSave.getPrerequisiteType() != null) {
			PrerequisiteType type = entityManager.find(PrerequisiteType.class, toSave.getPrerequisiteType().getId());
			if (type == null || type.getDeleted()) {
				throw new NoResultException("PrerequisiteType not found!");
			}
			danger.setPrerequisiteType(type);

		}
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}
		if (toSave.getMaterials() != null && !toSave.getMaterials().isEmpty()) {
			Set<Long> effectiveMaterials = new HashSet<>();
			for (MaterialDto material : toSave.getMaterials()) {
				effectiveMaterials.add(material.getId());
			}
			Session ses = (Session) entityManager.getDelegate();
			Criteria criteria = ses.createCriteria(Material.class, "danger");
			criteria.add(Restrictions.in("id", effectiveMaterials));
			List<Material> results = (List<Material>) criteria.list();
			if (results.size() != effectiveMaterials.size()) {
				throw new NoResultException("Can't find all the Materials");
			}
			danger.setMaterials(results);

		}
		danger.setOrganization(organization);
		Context context = new Context();
		context.setClassName(this.getClass().getSimpleName());
		entityManager.persist(context);
		danger.setContext(context);

		return DangerConverter.entityToDto(entityManager.merge(danger), true);

	}

	@Override
	public List<DangerDto> findDanger(Long organizationId) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();

			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			List<Danger> entities = (List<Danger>) entityManager.createNamedQuery(Danger.NQ_DANGER_BY_ORG_ID)
					.setParameter("idOrg", organizationId).getResultList();
			return DangerConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

	@Override
	public DangerDto findByContextId(long id,List<Long> organizations) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			Danger danger = (Danger) entityManager.createNamedQuery(Danger.NQ_DANGER_BY_CONTEXT_ID)
					.setParameter("idContext", id).getSingleResult();
			return DangerConverter.entityToDto(danger, true);

		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return null;
	}
	
}
