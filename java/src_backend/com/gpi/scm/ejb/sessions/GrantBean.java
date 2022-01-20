package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.GrantConverter;
import com.gpi.scm.converters.UserProfileConverter;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Grant;
import com.gpi.scm.ejb.entities.UserProfile;
import com.gpi.scm.generic.dtos.GrantDto;
import com.gpi.scm.generic.dtos.UserProfileDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.GrantLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class GrantBean extends GenericBean implements GrantLocal {

	private static final Logger logger = Logger.getLogger(GrantBean.class);

	@Override
	public List<GrantDto> findGrants() throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			List<Grant> entities = (List<Grant>) entityManager.createNamedQuery(Grant.NQ_GRANTS).getResultList();
			return GrantConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

	@Override
	public UserProfileDto setGrants(List<Long> organizations, long id, List<Long> grants) throws BusinessException {

		Session ses = (Session) entityManager.getDelegate();
		ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
		UserProfile profile = (UserProfile) entityManager.createNamedQuery(UserProfile.NQ_PROFILE_BY_ID)
				.setParameter("idProfile", id).setParameter("organizations", organizations).getSingleResult();
		List<Grant> grant = new ArrayList<>();
		if (profile == null || profile.getDeleted()) {
			throw new NoResultException("Profile not found");

		}
		if (grants.size() != 0) {
			grant = (List<Grant>) entityManager.createNamedQuery(Grant.NQ_GRANTS_BY_ID).setParameter("grants", grants)
					.getResultList();

			if (grant.size() != grants.size()) {
				throw new NoResultException("One or more grants not found!");

			}
		}
		profile.getGrants().clear();
		profile.getGrants().addAll(grant);

		UserProfile tmp = entityManager.merge(profile);
		return UserProfileConverter.entityToDto(tmp, true);

	}

}
