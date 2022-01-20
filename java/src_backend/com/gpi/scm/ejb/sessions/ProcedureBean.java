package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.ProcedureConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.ejb.entities.PrerequisiteType;
import com.gpi.scm.ejb.entities.Procedure;
import com.gpi.scm.ejb.entities.RiskClass;
import com.gpi.scm.ejb.entities.UserRole;
import com.gpi.scm.generic.dtos.ProcedureDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.ProcedureLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class ProcedureBean extends GenericBean implements ProcedureLocal {
	private static final Logger logger = Logger.getLogger(ProcedureBean.class);

	@Override
	public List<ProcedureDto> findProcedure(List<Long> organizations) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			List<Procedure> entities = (List<Procedure>) entityManager
					.createNamedQuery(Procedure.NQ_PROCEDURES_IN_ORGANIZATIONS)
					.setParameter("organizations", organizations).getResultList();
			return ProcedureConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

	@Override
	public ProcedureDto editProcedure(ProcedureDto toSave) throws BusinessException {
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		if (!organizations.contains(toSave.getOrganization().getId())) {
			throw new NoResultException("No access!");
		}
		Procedure procedure = entityManager.find(Procedure.class, toSave.getId());
		if (procedure == null || procedure.getDeleted()) {
			throw new NoResultException("No Procedure Found!");
		}
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());

		if (organization == null || organization.getDeleted() || !organizations.contains(organization.getId())) {
			throw new NoResultException("Organization not found!");
		}
		procedure.setOrganization(organization);
		if (toSave.getRiskClass() != null) {
			RiskClass rclass = entityManager.find(RiskClass.class, toSave.getRiskClass().getId());
			if (rclass == null || rclass.getDeleted()) {
				throw new NoResultException("RiskClass not found!");
			}
			procedure.setRiskClass(rclass);
		}
		UserRole urole = entityManager.find(UserRole.class, toSave.getUserRole().getId());
		if (urole == null || urole.getDeleted()) {
			throw new NoResultException("UserRole not found!");
		}
		procedure.setUserRole(urole);
		PrerequisiteType ptype = entityManager.find(PrerequisiteType.class, toSave.getPrerequisiteType().getId());
		if (ptype == null || ptype.getDeleted()) {
			throw new NoResultException("PrerequisiteType not found!");
		}

		procedure.setPrerequisiteType(ptype);
		procedure.setId(toSave.getId());
		procedure.setTitle(toSave.getTitle());
		procedure.setPrivacy(toSave.getPrivacy());
		procedure.setPurpose(toSave.getPurpose());
		procedure.setDescription(toSave.getDescription());
		procedure.setActivities(toSave.getActivities());
		procedure.setResults_check(toSave.getResults_check());
		procedure.setProcess_check(toSave.getProcess_check());
		procedure.setRevision(toSave.getRevision());
		procedure.setEquipments(toSave.getEquipments());
		Procedure tmp = entityManager.merge(procedure);
		return ProcedureConverter.entityToDto(tmp, true);
	}

	@Override
	public ProcedureDto newProcedure(ProcedureDto toSave) throws BusinessException {
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		if (!organizations.contains(toSave.getOrganization().getId())) {
			throw new NoResultException("No access!");
		}

		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}
		Procedure procedure = ProcedureConverter.dtoToEntity(toSave);

		if (toSave.getRiskClass() != null) {
			RiskClass rclass = entityManager.find(RiskClass.class, toSave.getRiskClass().getId());
			if (rclass == null || rclass.getDeleted()) {
				throw new NoResultException("RiskClass not found!");
			}
			procedure.setRiskClass(rclass);
		}
		UserRole urole = entityManager.find(UserRole.class, toSave.getUserRole().getId());
		if (urole == null || urole.getDeleted()) {
			throw new NoResultException("UserRole not found!");
		}
		PrerequisiteType ptype = entityManager.find(PrerequisiteType.class, toSave.getPrerequisiteType().getId());
		if (ptype == null || ptype.getDeleted()) {
			throw new NoResultException("PrerequisiteType not found!");
		}

		procedure.setOrganization(organization);
		procedure.setUserRole(urole);
		procedure.setPrerequisiteType(ptype);

		Procedure tmp = entityManager.merge(procedure);
		return ProcedureConverter.entityToDto(tmp, true);
	}

	@Override
	public boolean deleteProcedure(long id) throws BusinessException {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());

			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			Procedure toDelete = (Procedure) entityManager.createNamedQuery(Procedure.NQ_PROCEDURE_BY_ID)
					.setParameter("idProcedure", id).getSingleResult();
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
	public ProcedureDto findProcedureById(long id, List<Long> organizations) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			Procedure procedure = (Procedure) entityManager.createNamedQuery(Procedure.NQ_PROCEDURE_BY_ID)
					.setParameter("idProcedure", id).getSingleResult();
			return ProcedureConverter.entityToDto(procedure, true);

		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return null;
	}

}
