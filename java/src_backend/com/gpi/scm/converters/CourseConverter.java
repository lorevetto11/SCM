package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.Course;
import com.gpi.scm.generic.dtos.CourseDto;

public class CourseConverter extends GenericConverter {
	public static CourseDto entityToDto(Course entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		CourseDto result = new CourseDto();
		result.setId(entity.getId());
		result.setName(entity.getName());
		result.setDescription(entity.getDescription());
		result.setTrainer(entity.getTrainer());
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setContext(ContextConverter.entityToDto(entity.getContext(), false));
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(), false));
		}
		
		return result;
	}

	public static Course dtoToEntity(CourseDto dto) {
		if (dto == null) {
			return null;
		}
		Course result = new Course();
		result.setId(dto.getId());
		result.setName(dto.getName());
		result.setDescription(dto.getDescription());
		result.setTrainer(dto.getTrainer());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<CourseDto> entityToDto(List<Course> entities, boolean loadRelations) {
		List<CourseDto> courses = new ArrayList<>();
		for (Course course : entities) {
			courses.add(entityToDto(course, loadRelations));
		}
		return courses;
	}
	public static List<Course> dtoToEntity(List<CourseDto> entities) {
		List<Course> courses = new ArrayList<>();
		for (CourseDto course : entities) {
			courses.add(dtoToEntity(course));
		}
		return courses;
	

	}
}
