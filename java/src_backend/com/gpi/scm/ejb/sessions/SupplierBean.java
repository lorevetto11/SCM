package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.SupplierConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.Supplier;
import com.gpi.scm.ejb.entities.Context;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.generic.dtos.SupplierDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.SupplierLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class SupplierBean extends GenericBean implements SupplierLocal {
	private static final Logger logger = Logger.getLogger(SupplierBean.class);

	@Override
	public List<SupplierDto> findSupplier(Long organization) throws BusinessException {
		try {

			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(organization);

			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			List<Supplier> entities = (List<Supplier>) entityManager
					.createNamedQuery(Supplier.NQ_SUPPLIER_IN_ORGANIZATIONS)
					.setParameter("organizations", organizations).getResultList();
			return SupplierConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

	@Override
	public boolean deleteSupplier(long id) throws BusinessException {

		/*
		 * List<Long> organizations = OrganizationDelegate
		 * .organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		 */

		Supplier toDelete = entityManager.find(Supplier.class, id);
		if (toDelete == null || toDelete.getDeleted()) {
			return false;
		}
		toDelete.setDeleted(true);
		return true;
	}

	@Override
	public SupplierDto editSupplier(SupplierDto toSave) throws BusinessException {
		/*
		 * List<Long> organizations = OrganizationDelegate
		 * .organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		 * if (!organizations.contains(toSave.getOrganization().getId())) { throw new
		 * NoResultException("No access !"); }
		 */


		Supplier supplier = entityManager.find(Supplier.class, toSave.getId());
		if (supplier == null || supplier.getDeleted()) {
			throw new NoResultException("Supplier not found");
		}
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}
		if (toSave.getContext() != null) {
			Context context = entityManager.find(Context.class, toSave.getContext().getId());
			if (context == null || context.getDeleted()) {
				throw new NoResultException("Context not found!");
			}
			supplier.setContext(context);

		}
		supplier.setName(toSave.getName());
		supplier.setDescription(toSave.getDescription());
		supplier.setVatNumber(toSave.getVatNumber());
		supplier.setContact(toSave.getContact());
		supplier.setAddress(toSave.getAddress());
		supplier.setEmail(toSave.getEmail());
		supplier.setPhone(toSave.getPhone());
		supplier.setOrganization(organization);

		return SupplierConverter.entityToDto(entityManager.merge(supplier), true);

	}

	@Override
	public SupplierDto newSupplier(SupplierDto toSave) throws BusinessException {

		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		Supplier supplier = SupplierConverter.dtoToEntity(toSave);

		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}

		Context context = new Context();
		context.setClassName(this.getClass().getSimpleName());
		entityManager.persist(context);
		supplier.setContext(context);
		supplier.setOrganization(organization);

		Supplier tmp = entityManager.merge(supplier);
		return SupplierConverter.entityToDto(tmp, true);

	}

}
