package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import javax.persistence.NoResultException;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.ParticipantConverter;
import com.gpi.scm.converters.TrainingConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.delegates.ParticipantDelegate;
import com.gpi.scm.ejb.entities.Course;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.ejb.entities.Participant;
import com.gpi.scm.ejb.entities.Training;
import com.gpi.scm.ejb.entities.UserRole;
import com.gpi.scm.generic.dtos.ParticipantDto;
import com.gpi.scm.generic.dtos.TrainingDto;
import com.gpi.scm.generic.dtos.validators.ValidationUtil;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.exceptions.ValidationException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.TrainingLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class TrainingBean extends GenericBean implements TrainingLocal {

	private static final Logger logger = Logger.getLogger(TrainingBean.class);

	@Override
	public boolean deleteTraining(long id) throws BusinessException {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());

			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			Training toDelete = (Training) entityManager.createNamedQuery(Training.NQ_TRAINING_BY_ID)
					.setParameter("idTraining", id).getSingleResult();
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

	@SuppressWarnings("unused")
	@Override
	public TrainingDto editTraining(TrainingDto toSave) throws BusinessException {
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());

		if (!organizations.contains(toSave.getOrganization().getId())) {
			throw new NoResultException("No access !");
		}

		Training training = entityManager.find(Training.class, toSave.getId());
		if (training == null || training.getDeleted() || !organizations.contains(training.getOrganization().getId())) {
			throw new NoResultException("Training not found!");
		}

		Course course = entityManager.find(Course.class, toSave.getCourse().getId());
		if (course == null || course.getDeleted() || !organizations.contains(course.getOrganization().getId())) {
			throw new NoResultException("Course not found!");
		}

		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}
		UserRole role = entityManager.find(UserRole.class, toSave.getUserRole().getId());
		if (role == null || role.getDeleted()) {
			throw new NoResultException("UserRole not found!");
		}

		List<ParticipantDto> participantsToInsert = toSave.getParticipants();
		List<Participant> effectiveParticipants = new ArrayList<Participant>();

		if (!participantsToInsert.isEmpty()) {

			for (ParticipantDto participant : participantsToInsert) {

				ParticipantDto tmp = null;
				Participant participantE = entityManager.find(Participant.class, participant.getId());
				ParticipantDto participantDto = ParticipantConverter.entityToDto(participantE, true);

				Participant t = ParticipantConverter.dtoToEntity(participant);
				if (participantE != null && !participantE.getDeleted()) {
					if (MyHashCode(participantDto) == MyHashCode(participant)) {
						effectiveParticipants.add(participantE);
					} else if (onlyId(participant)) {
						effectiveParticipants.add(participantE);
					} else {
						tmp = ParticipantDelegate.editParticipant(participant, organizations);
						effectiveParticipants.add(entityManager.find(Participant.class, tmp.getId()));

					}

				} else if (participantE == null) {

					ValidationUtil valid = new ValidationUtil();
					String validated = valid.isValid(participant);
					if (!validated.isEmpty()) {
						throw new ValidationException("Participant validation Failed");
					}
					tmp = ParticipantDelegate.saveParticipant(participant, organizations);
					effectiveParticipants.add(entityManager.find(Participant.class, tmp.getId()));

				}
			}
		}

		training.setParticipants(effectiveParticipants);
		training.setId(toSave.getId());
		training.setName(toSave.getName());
		training.setDate(toSave.getDate());
		training.setCourse(course);
		training.setOrganization(organization);
		training.setUserRole(role);
		training.setArchived(toSave.isArchived());

		Training tmp = entityManager.merge(training);

		return TrainingConverter.entityToDto(tmp, true);
	}

	@Override
	public TrainingDto newTraining(TrainingDto toSave) throws BusinessException {

		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		
		if (!organizations.contains(toSave.getOrganization().getId())) {
			throw new NoResultException("No access !");
		}

		Course course = entityManager.find(Course.class, toSave.getCourse().getId());
		if (course == null || course.getDeleted() || !organizations.contains(course.getOrganization().getId())) {
			throw new NoResultException("Course not found!");
		}

		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}
		UserRole role = entityManager.find(UserRole.class, toSave.getUserRole().getId());
		if (role == null || role.getDeleted() || !organizations.contains(role.getOrganization().getId())) {
			throw new NoResultException("UserRole not found!");
		}

		List<ParticipantDto> participantsToInsert = toSave.getParticipants();
		List<Participant> effectiveParticipants = new ArrayList<Participant>();

		if (!participantsToInsert.isEmpty()) {
			for (ParticipantDto participant : participantsToInsert) {

				ParticipantDto tmp = null;
				Participant participantE = entityManager.find(Participant.class, participant.getId());
				if (participantE != null && !participantE.getDeleted()) {
					effectiveParticipants.add(participantE);

				} else if (participantE == null) {
					tmp = ParticipantDelegate.saveParticipant(participant, organizations);
					effectiveParticipants.add(entityManager.find(Participant.class, tmp.getId()));

				}
			}

		}

		Training training = TrainingConverter.dtoToEntity(toSave);
		training.setParticipants(effectiveParticipants);
		training.setCourse(course);
		training.setOrganization(organization);
		training.setUserRole(role);

		Training tmp = entityManager.merge(training);

		return TrainingConverter.entityToDto(tmp, true);
	}

	@Override
	public List<TrainingDto> findTrainings(Long organizationId) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			List<Training> entities = (List<Training>) entityManager.createNamedQuery(Training.NQ_TRAININGS)
					.setParameter("idOrganization", organizationId).getResultList();
			return TrainingConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

	@Override
	public TrainingDto findTrainingsById(Long id) throws BusinessException {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());

			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			Training training = (Training) entityManager.createNamedQuery(Training.NQ_TRAINING_BY_ID)
					.setParameter("idTraining", id).getSingleResult();
			return TrainingConverter.entityToDto(training, true);

		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return null;
	}

	public boolean onlyId(ParticipantDto participant) {
		if (participant.getId() != 0
				&& !ObjectUtils.anyNotNull(participant.getContext(), participant.getUser(), participant.getNote())) {
			return true;
		}
		return false;
	}

	public int MyHashCode(ParticipantDto participant) {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((participant.getContext() == null) ? 0 : participant.getContext().hashCode());
		result = prime * result + ((participant.getNote() == null) ? 0 : participant.getNote().hashCode());
		result = prime * result + (participant.isPassed() ? 1231 : 1237);
		result = prime * result + ((participant.getUser() == null) ? 0 : participant.getUser().hashCode());
		return result;
	}

}
