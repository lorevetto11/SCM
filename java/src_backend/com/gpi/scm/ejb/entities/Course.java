package com.gpi.scm.ejb.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="TRN_COURSES")

@NamedQueries({ @NamedQuery(name = Course.NQ_COURSE_BY_ID, query = "Select u from Course u where u.id = :idCourse"),
	@NamedQuery(name = Course.NQ_COURSES_IN_ORGANIZATIONS, query = "Select u from Course u where u.organization.id IN :organizations")
	})
public class Course extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NQ_COURSE_BY_ID = "course.getCourseById";
	public static final String NQ_COURSES_IN_ORGANIZATIONS = "course.courseInOrganizations";
	
	String name;
	String description;
	String trainer;
	Organization organization;
	Context context;
	List<Training> training= new ArrayList<>(); 
	


	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_trn_courses")
	@SequenceGenerator(name = "seq_trn_courses", sequenceName = "seq_trn_courses", allocationSize = 1)
	public Long getId()
	{
		return this.id;
	}
	
	@Column(name="NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name="TRAINER")
	public String getTrainer() {
		return trainer;
	}
	public void setTrainer(String trainer) {
		this.trainer = trainer;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REF_ORGANIZATION")
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REF_CONTEXT")
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="REF_COURSE")
	public List<Training> getTraining() {
		return training;
	}

	public void setTraining(List<Training> training) {
		this.training = training;
	}
	
	
}
