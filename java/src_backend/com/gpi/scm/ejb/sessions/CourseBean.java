package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gpi.scm.converters.CourseConverter;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.ejb.entities.Context;
import com.gpi.scm.ejb.entities.Course;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.ejb.entities.Organization;
import com.gpi.scm.generic.dtos.CourseDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.CourseLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class CourseBean extends GenericBean implements CourseLocal {
	private static final Logger logger = Logger.getLogger(CourseBean.class);

	@Override
	public List<CourseDto> findCourses(List<Long> organizations) throws BusinessException {
		try {
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);

			List<Course> entities = (List<Course>) entityManager.createNamedQuery(Course.NQ_COURSES_IN_ORGANIZATIONS)
					.setParameter("organizations", organizations).getResultList();
			return CourseConverter.entityToDto(entities, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return new ArrayList<>();
	}

	@Override
	public CourseDto newCourse(CourseDto toSave) throws BusinessException {

		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		if (!organizations.contains(toSave.getOrganization().getId())) {
			throw new NoResultException("No access!");

		}
		
		Organization organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		if (organization == null || organization.getDeleted()) {
			throw new NoResultException("Organization not found!");
		}
		

		Context context = new Context();
		context.setClassName(this.getClass().getSimpleName());
		entityManager.persist(context);

		Course course = CourseConverter.dtoToEntity(toSave);
		course.setOrganization(organization);
		course.setContext(context);
		Course tmp = entityManager.merge(course);

		return CourseConverter.entityToDto(tmp, true);
	}

	@Override
	public CourseDto editCourse(CourseDto toSave) throws BusinessException {
		
		Course course = entityManager.find(Course.class, toSave.getId());
		if (course == null || course.getDeleted()) {
			throw new NoResultException("Course not found");
		}
		List<Long> organizations = OrganizationDelegate
				.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
		if (!organizations.contains(toSave.getOrganization().getId()) || !organizations.contains(course.getOrganization().getId())) {
			throw new NoResultException("No access!");

		}

		Organization organization = null;
		if (toSave.getOrganization() != null && (organizations.contains(toSave.getOrganization().getId()))) {
			organization = entityManager.find(Organization.class, toSave.getOrganization().getId());
		}

		if (toSave.getContext() != null) {
			Context context = entityManager.find(Context.class, toSave.getContext().getId());
			if (context == null || context.getDeleted()) {
				throw new NoResultException("Context Not Found");
			}
			course.setContext(context);
		}
		course.setOrganization(organization);
		course.setDescription(toSave.getDescription());
		course.setName(toSave.getName());
		course.setTrainer(toSave.getTrainer());

		Course tmp = entityManager.merge(course);

		return CourseConverter.entityToDto(tmp, true);
	}

	@Override
	public boolean deleteCourse(long id) throws BusinessException {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			Course toDelete = (Course) entityManager.createNamedQuery(Course.NQ_COURSE_BY_ID)
					.setParameter("idCourse", id).getSingleResult();
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
	public CourseDto findCoursesById(long id) throws BusinessException {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			ses.enableFilter(GenericEntity.FILTER_ORG).setParameterList(GenericEntity.FILTER_ORG_PARAM, organizations);

			Course course = (Course) entityManager.createNamedQuery(Course.NQ_COURSE_BY_ID).setParameter("idCourse", id)
					.getSingleResult();
			return CourseConverter.entityToDto(course, true);
		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return null;
	}

}
