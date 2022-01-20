package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.MonitoringConverter;
import com.gpi.scm.delegates.FrequencyDelegate;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.Monitoring;
import com.gpi.scm.ejb.entities.Context;
import com.gpi.scm.ejb.entities.Frequency;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.ejb.entities.Procedure;
import com.gpi.scm.generic.dtos.FrequencyDto;
import com.gpi.scm.generic.dtos.MonitoringDto;
import com.gpi.scm.generic.dtos.validators.ValidationUtil;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.exceptions.ValidationException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.MonitoringLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class MonitoringBean extends GenericBean implements MonitoringLocal {
	private static final Logger logger = Logger.getLogger(MonitoringBean.class);

	@Override
	public List<MonitoringDto> findMonitoring(Long organizationId) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			List<Monitoring> entities = (List<Monitoring>) entityManager
					.createNamedQuery(Monitoring.NQ_MONITORING_IN_ORGANIZATIONS)
					.setParameter("organizations", organizationId).getResultList();
			return MonitoringConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}
	
	@Override
	public List<MonitoringDto> findMonitoringByUserRole(Long organizationId, Long userRoleId) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);

			List<Monitoring> entities = (List<Monitoring>) entityManager
					.createNamedQuery(Monitoring.NQ_MONITORING_BY_ROLEID)
					.setParameter("organizations", organizationId)
					.setParameter("idRole", userRoleId)
					.getResultList();

			return MonitoringConverter.entityToDto(entities, true);

		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

	@Override
	public boolean deleteMonitoring(long id) throws BusinessException {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());

			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			Monitoring toDelete = (Monitoring) entityManager.createNamedQuery(Monitoring.NQ_MONITORING_BY_ID)
					.setParameter("idMonitoring", id).getSingleResult();
			toDelete.setDeleted(true);
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
	public MonitoringDto editMonitoring(MonitoringDto toSave) throws BusinessException {
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		if (!organizations.contains(toSave.getOrganization().getId())) {
			throw new NoResultException("No access !");
		}
		Frequency frequency = null;
		FrequencyDto freqDto = toSave.getFrequency();

		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted() || !organizations.contains(organization.getId())) {
			throw new NoResultException("Organization not found!");
		}
		Monitoring monitoring = entityManager.find(Monitoring.class, toSave.getId());
		if (monitoring == null || monitoring.getDeleted()) {
			throw new NoResultException("Monitoring not found");
		}
		if (freqDto.getId() != 0) {
			frequency = entityManager.find(Frequency.class, freqDto.getId());
			if (frequency == null || frequency.getDeleted()) {
				if (freqDto.getType().toString().equals("DEFAULT")) {
					throw new NoResultException("Frequency not found !");
				} else {
					ValidationUtil valid = new ValidationUtil();
					String validated = valid.isValid(freqDto);
					if (!validated.isEmpty()) {
						throw new ValidationException(validated);
					}
					freqDto = FrequencyDelegate.save(toSave.getFrequency());
				}
			}
		} else {
			if (!freqDto.getType().toString().equals("DEFAULT")) {
				ValidationUtil valid = new ValidationUtil();
				String validated = valid.isValid(freqDto);
				if (!validated.isEmpty()) {
					throw new ValidationException(validated);
				}
				freqDto = FrequencyDelegate.save(toSave.getFrequency());
			} else {
				throw new NoResultException("Frequency not found!");
			}
		}

		Context context = entityManager.find(Context.class, toSave.getContext().getId());
		if (context == null || context.getDeleted()) {
			throw new NoResultException("Context not found!");
		}
		Procedure procedure = entityManager.find(Procedure.class, toSave.getProcedure().getId());
		if (procedure == null || procedure.getDeleted()
				|| procedure.getOrganization().getId() != organization.getId()) {
			throw new NoResultException("Procedure not found!");
		}

		monitoring.setOrganization(organization);
		monitoring.setContext(context);
		monitoring.setFrequency(entityManager.find(Frequency.class, freqDto.getId()));
		monitoring.setProcedure(procedure);

		return MonitoringConverter.entityToDto(entityManager.merge(monitoring), true);

	}

	@Override
	public MonitoringDto newMonitoring(MonitoringDto toSave) throws BusinessException {
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		if (!organizations.contains(toSave.getOrganization().getId())) {
			throw new NoResultException("No access !");
		}
		Frequency frequency = null;
		FrequencyDto freqDto = toSave.getFrequency();

		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted() || !organizations.contains(organization.getId())) {
			throw new NoResultException("Organization not found!");
		}
		if (freqDto.getId() != 0) {
			frequency = entityManager.find(Frequency.class, toSave.getFrequency().getId());
			if (frequency == null || frequency.getDeleted()) {
				throw new NoResultException("Frequency not found!");
			}
		} else {
			if (!freqDto.getType().equals("DEFAULT")) {
				ValidationUtil valid = new ValidationUtil();
				String validated = valid.isValid(freqDto);
				if (!validated.isEmpty()) {
					throw new ValidationException(validated);
				}
				freqDto = FrequencyDelegate.save(toSave.getFrequency());
			} else {
				throw new NoResultException("No Frequency found!");
			}
		}

		Procedure procedure = entityManager.find(Procedure.class, toSave.getProcedure().getId());
		if (procedure == null || procedure.getDeleted()) {
			throw new NoResultException("Procedure not found!");
		}
		Context context = entityManager.find(Context.class, toSave.getContext().getId());
		if (context == null || context.getDeleted()) {
			throw new NoResultException("Context not found!");
		}
		Monitoring monitoring = MonitoringConverter.dtoToEntity(toSave);

		monitoring.setOrganization(organization);
		monitoring.setContext(context);
		monitoring.setFrequency(entityManager.find(Frequency.class, freqDto.getId()));
		monitoring.setProcedure(procedure);

		Monitoring tmp = entityManager.merge(monitoring);
		return MonitoringConverter.entityToDto(tmp, true);

	}
}

