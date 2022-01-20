package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.ParticipantConverter;
import com.gpi.scm.ejb.entities.Context;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Participant;
import com.gpi.scm.ejb.entities.User;
import com.gpi.scm.generic.dtos.ParticipantDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.ParticipantLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class ParticipantBean extends GenericBean implements ParticipantLocal {

	private static final Logger logger = Logger.getLogger(ParticipantBean.class);

	@Override
	public boolean deleteParticipant(long id, List<Long> organizations) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);

			Participant toDelete = (Participant) entityManager.createQuery(
					"Select p from Participant p inner join p.user u where u.organization.id in :organizations and p.id=:idParticipant")
					.setParameter("idParticipant", id).setParameter("organizations", organizations).getSingleResult();

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
	public ParticipantDto findParticipantsById(long id) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);

			Participant participant = (Participant) entityManager.createNamedQuery(Participant.NQ_PARTICIPANT_BY_ID)
					.setParameter("IdParticipant", id).getSingleResult();
			return ParticipantConverter.entityToDto(participant, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return null;
	}

	@Override
	public List<ParticipantDto> findParticipants(Long roleId) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);

			List<Participant> entities = (List<Participant>) entityManager
					.createNamedQuery(Participant.NQ_PARTICIPANT_BY_ROLEID).setParameter("IdRole", roleId)
					.getResultList();
			return ParticipantConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

	@Override
	public List<ParticipantDto> findParticipants() throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);

			List<Participant> entities = (List<Participant>) entityManager.createNamedQuery(Participant.NQ_PARTICIPANTS)
					.getResultList();
			return ParticipantConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

	@Override
	public ParticipantDto newParticipant(ParticipantDto toSave, List<Long> organizations) throws BusinessException {

		Participant participant = ParticipantConverter.dtoToEntity(toSave);

		User user = (User) entityManager
				.createQuery("Select u from User u where u.id=:IdUser and u.organization.id in :organizations")
				.setParameter("IdUser", toSave.getUser().getId()).setParameter("organizations", organizations)
				.getSingleResult();

		if (user == null || user.getDeleted()) {
			throw new NoResultException("User not found");
		}

		Context context = new Context();
		context.setClassName(this.getClass().getSimpleName());
		entityManager.persist(context);

		participant.setContext(context);
		participant.setUser(user);
		Participant tmp = entityManager.merge(participant);

		return ParticipantConverter.entityToDto(tmp, true);
	}

	@Override
	public ParticipantDto editParticipant(ParticipantDto toSave, List<Long> organizations) throws BusinessException {

		Participant toEdit = entityManager.find(Participant.class, toSave.getId());
		if (toEdit == null || toEdit.getDeleted()) {
			throw new NoResultException("Participant not found");
		}
	
		User user = (User) entityManager
				.createQuery("Select u from User u where u.id=:IdUser and u.organization.id in :organizations")
				.setParameter("IdUser", toSave.getUser().getId()).setParameter("organizations", organizations)
				.getSingleResult();
		if (user != null) {
			if (user == null || user.getDeleted()) {
				throw new NoResultException("User not found");
			}
			toEdit.setUser(user);
			
		}
		if (toSave.getContext() != null) {
			Context context = entityManager.find(Context.class, toSave.getContext().getId());
			if (context == null || context.getDeleted()) {
				throw new NoResultException("Context not found");
			}
			toEdit.setContext(context);

		}
	

		toEdit.setPassed(toSave.isPassed());
		toEdit.setNote(toSave.getNote());

		Participant tmp = entityManager.merge(toEdit);

		return ParticipantConverter.entityToDto(tmp, true);

	}

}
