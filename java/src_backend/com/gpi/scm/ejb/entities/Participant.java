package com.gpi.scm.ejb.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="TRN_PARTICIPANTS")
@NamedQueries({ @NamedQuery(name = Participant.NQ_PARTICIPANT_BY_ROLEID, query = "Select p from Participant p inner join p.user u where u.role.id = :IdRole"),
	 @NamedQuery(name = Participant.NQ_PARTICIPANT_BY_ID, query = "Select p from Participant p where p.id = :IdParticipant"),
	@NamedQuery(name = Participant.NQ_PARTICIPANTS, query = "Select u from Participant u "),
	@NamedQuery(name = Participant.NQ_PARTICIPANTS_ORGANIZATIONS, query = "Select u from Participant p inner join p.user u where u.organization.id in :organizations and u.id=:IdUser")
	})
public class Participant extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NQ_PARTICIPANT_BY_ID = "participant.getParticipantById";

	public static final String NQ_PARTICIPANT_BY_ROLEID = "participant.getParticipantByRoleId";
	public static final String NQ_PARTICIPANTS = "participant.getAllParticipants";
	public static final String NQ_PARTICIPANTS_ORGANIZATIONS = "participant.getParticipantsInOrganizations";

	boolean passed;
	String note;
	Context context;
	User user;
	@ManyToMany(mappedBy="participants",targetEntity=Training.class)
	private List<Training> trainings = new ArrayList<>() ;
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_trn_participants")
	@SequenceGenerator(name = "seq_trn_participants", sequenceName = "seq_trn_participants", allocationSize = 1)
	public Long getId()
	{
		return id;
	}
	@Column(name="PASSED")
	public boolean isPassed() {
		return passed;
	}
	public void setPassed(boolean passed) {
		this.passed = passed;
	}
	@Column(name="NOTE")
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	@OneToOne
	@JoinColumn(name="REF_CONTEXT")
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	@OneToOne
	@JoinColumn(name="REF_USER")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	

}
