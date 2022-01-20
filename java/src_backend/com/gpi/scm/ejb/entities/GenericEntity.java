package com.gpi.scm.ejb.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

@MappedSuperclass
@FilterDefs({
	@FilterDef(name = GenericEntity.FILTER_DELETED, parameters = @ParamDef(name = GenericEntity.FILTER_DELETED_PARAM, type = "boolean")),
@FilterDef(name = GenericEntity.FILTER_ORG, parameters = @ParamDef(name =GenericEntity.FILTER_ORG_PARAM, type = "long")) })
@Filters({@Filter(name = GenericEntity.FILTER_DELETED, condition = "deleted = :deleted"),
	@Filter(name=GenericEntity.FILTER_ORG, condition="ref_organization in (:organization)")

	})
public class GenericEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String FILTER_DELETED = "deletedFilter";
	public static final String FILTER_DELETED_PARAM = "deleted";
	public static final String FILTER_ORG = "orgFilter";
	public static final String FILTER_ORG_PARAM = "organization";

	protected Long id;
	private Long version;
	private Boolean deleted;
	private Date insertTime;
	private String insertUser;
	private Date updateTime;
	private String updateUser;
	private Date deleteTime;
	private String deleteUser;

	public static final String VERSION_FIELD = "version";
	public static final String DELETED_FIELD = "deleted";
	public static final String INSERTTIME_FIELD = "insertTime";
	public static final String INSERTUSER_FIELD = "insertUser";
	public static final String UPDATETIME_FIELD = "updateTime";
	public static final String UPDATEUSER_FIELD = "updateUser";
	public static final String DELETETIME_FIELD = "deleteTime";
	public static final String DELETEUSER_FIELD = "deleteUser";

	public GenericEntity() {
		setDeleted(false);
	}

	public void setId(Long id) {
		if (id >= 0)
			this.id = id;
	}

	@Version
	@Column(name = "VERSION")
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@Column(name = "DELETED")
	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INSERT_TIME")
	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	@Column(name = "INSERT_USER")
	public String getInsertUser() {
		return insertUser;
	}

	public void setInsertUser(String insertUser) {
		this.insertUser = insertUser;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_TIME")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "UPDATE_USER")
	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DELETE_TIME")
	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	@Column(name = "DELETE_USER")
	public String getDeleteUser() {
		return deleteUser;
	}

	public void setDeleteUser(String deleteUser) {
		this.deleteUser = deleteUser;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GenericEntity other = (GenericEntity) obj;
		if (id.longValue() != other.id.longValue())
			return false;
		return true;
	}
}
