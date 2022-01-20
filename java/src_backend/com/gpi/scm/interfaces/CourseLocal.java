package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.CourseDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface CourseLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "CourseBean" + "!" + CourseLocal.class.getName();

	public List<CourseDto> findCourses(List<Long> organizations) throws BusinessException;

	public CourseDto newCourse(CourseDto toSave) throws BusinessException;

	public CourseDto editCourse(CourseDto toSave) throws BusinessException;

	public boolean deleteCourse(long id) throws BusinessException;

	public CourseDto findCoursesById(long id) throws BusinessException;

}
