package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.CourseDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.CourseLocal;

public class CourseDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(CourseDelegate.class);
	private static CourseLocal getBean() throws NamingException {
		return (CourseLocal) getContext().lookup(CourseLocal.JNDI_NAME);
	}
	
	public static List<CourseDto> findCourses(List<Long> organizations) {
		List<CourseDto> obj = null;
		try {
			obj = getBean().findCourses(organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static Object saveCourse(CourseDto toSave) {
		CourseDto obj = null;
		try {
			obj = getBean().newCourse(toSave);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static Object editCourse(CourseDto toSave) {
		CourseDto obj = null;
		try {
			obj = getBean().editCourse(toSave);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static boolean delete(long id) {
		try {
			return getBean().deleteCourse(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return false;
	}

	public static CourseDto findCourseById(long id) {
		CourseDto obj = null;
		try {
			obj = getBean().findCoursesById(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

}
