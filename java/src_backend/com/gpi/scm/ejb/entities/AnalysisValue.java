package com.gpi.scm.ejb.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="anl_analysis_values")
@NamedQueries({
	@NamedQuery(name = AnalysisValue.NQ_VALUE_BY_PARAMETER_ID, query = "Select u from AnalysisValue u where u.analysisParameter.id = :idParameter ")

})
public class AnalysisValue extends GenericEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NQ_VALUE_BY_PARAMETER_ID = "analysisValue.findByParameterId";
	private Date date;
	private double value;
	private String note;
	private Context context;
	private AnalysisParameter analysisParameter;
	private Organization organization;
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_anl_analysis_values")
	@SequenceGenerator(name = "seq_anl_analysis_values", sequenceName = "seq_anl_analysis_values", allocationSize = 1)
	public Long getId()
	{
		return id;
	}
	@Column(name="\"DATE\"")
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Column(name="VALUE")
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	@Column(name="NOTE")
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	@OneToOne
	@JoinColumn(name="REF_PREREQUISITE_CONTEXT")
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	@ManyToOne
	@JoinColumn(name="REF_ANALYSIS_PARAMETER")
	public AnalysisParameter getAnalysisParameter() {
		return analysisParameter;
	}
	public void setAnalysisParameter(AnalysisParameter analysisParameter) {
		this.analysisParameter = analysisParameter;
	}
	@ManyToOne
	@JoinColumn(name="REF_ORGANIZATION")
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	

}
