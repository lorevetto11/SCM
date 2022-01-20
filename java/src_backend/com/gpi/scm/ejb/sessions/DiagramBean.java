package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.DiagramConverter;
import com.gpi.scm.ejb.entities.Diagram;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.generic.dtos.DiagramDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.DiagramLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class DiagramBean extends GenericBean implements DiagramLocal {
	private static final Logger logger = Logger.getLogger(DiagramBean.class);

	@Override
	public List<DiagramDto> findDiagram(Long organization) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			List<Diagram> entities = (List<Diagram>) entityManager
					.createNamedQuery(Diagram.NQ_DIAGRAM_IN_ORGANIZATIONS)
					.setParameter("organizations", organization).getResultList();
			return DiagramConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

	@Override
	public boolean deleteDiagram(long id) throws BusinessException {

		/*
		 * List<Long> organizations = OrganizationDelegate
		 * .organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		 */

		Diagram toDelete = entityManager.find(Diagram.class, id);
		if (toDelete == null || toDelete.getDeleted()) {
			return false;
		}
		toDelete.setDeleted(true);
		return true;
	}

	@Override
	public DiagramDto editDiagram(DiagramDto toSave) throws BusinessException {
		/*
		 * List<Long> organizations = OrganizationDelegate
		 * .organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		 * if (!organizations.contains(toSave.getOrganization().getId())) { throw new
		 * NoResultException("No access !"); }
		 */

		Diagram diagram = entityManager.find(Diagram.class, toSave.getId());
		if (diagram == null || diagram.getDeleted()) {
			throw new NoResultException("Diagram not found");
		}
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}
		diagram.setName(toSave.getName());
		diagram.setDescription(toSave.getDescription());
		diagram.setOrganization(organization);


		return DiagramConverter.entityToDto(entityManager.merge(diagram), true);

	}

	@Override
	public DiagramDto newDiagram(DiagramDto toSave) throws BusinessException {

		Diagram diagram = DiagramConverter.dtoToEntity(toSave);
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());

		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}
		diagram.setOrganization(organization);
		Diagram tmp = entityManager.merge(diagram);
		return DiagramConverter.entityToDto(tmp, true);

	}

}
