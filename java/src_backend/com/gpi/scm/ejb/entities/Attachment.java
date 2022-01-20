package com.gpi.scm.ejb.entities;


import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="CTX_ATTACHMENTS")
@NamedQuery(name = Attachment.NQ_ATTACHMENT_BY_ID, query = "Select u from Floor u where u.id = :idFloor")


public class Attachment extends AttachmentBase {



	private static final long serialVersionUID = 1L;
	public static final String NQ_ATTACHMENT_BY_ID = "attachment.AttachmentById";

	private byte[] data;

	
	@Column(name="DATA")
	@Basic(fetch=FetchType.LAZY)
	public byte [] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	
	

}
