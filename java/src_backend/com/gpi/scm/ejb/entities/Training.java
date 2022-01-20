package com.gpi.scm.ejb.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="TRN_TRAININGS")
@NamedQueries({@NamedQuery(name = Training.NQ_TRAININGS, query = "Select u from Training u where u.organization.id=:idOrganization"),
	 @NamedQuery(name = Training.NQ_TRAINING_BY_ID, query = "Select u from Training u where u.id= :idTraining ")})

public class Training extends GenericEntity {

	/**
	 * 
	 */
	public static final String NQ_TRAININGS = "training.findAllTrainings";
	public static final String NQ_TRAINING_BY_ID = "training.findById";
	private static final long serialVersionUID = 1L;
	Organization organization;
	UserRole role;
	Course course;
	String name;
	Date date;
	boolean archived;
	List<Participant> participants= new ArrayList<>();
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_trn_trainings")
	@SequenceGenerator(name = "seq_trn_trainings", sequenceName = "seq_trn_trainings", allocationSize = 1)	
	public Long getId()
	{
		return id;
	}

	@Column(name="NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="ARCHIVED")
	public boolean isArchived() {
		return archived;
	}
	public void setArchived(boolean archived) {
		this.archived = archived;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"DATE\"")
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@ManyToMany(cascade = CascadeType.ALL,targetEntity=Participant.class)
	@JoinTable(name="trn_participants_trn_trainings",
    joinColumns={@JoinColumn(name="ref_trn_trainings", referencedColumnName="id")},
    inverseJoinColumns={@JoinColumn(name="ref_trn_participants", referencedColumnName="id")}) 
	
	public List<Participant> getParticipants() {
		return participants;
	}
	public void setParticipants(List<Participant> participants) {
		this.participants = participants;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REF_ORGANIZATION")
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REF_ROLE")
	public UserRole getUserRole() {
		return role;
	}
	public void setUserRole(UserRole role) {
		this.role = role;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REF_COURSE")
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}

}
