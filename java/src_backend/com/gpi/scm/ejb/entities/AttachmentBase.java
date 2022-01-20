package com.gpi.scm.ejb.entities;


import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

@MappedSuperclass



public class AttachmentBase extends GenericEntity {


	
	private static final long serialVersionUID = 1L;
	private String filename;
	private String description;
	private String mimeType;
	private String type;
	private String link;
	private Long fileSize;
	private Context context;
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ctx_attachments")
	@SequenceGenerator(name = "seq_ctx_attachments", sequenceName = "seq_ctx_attachments", allocationSize = 1)

	public Long getId()
	{
		return this.id;
	}
	
	@Column(name="FILENAME")
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name="MIMETYPE")
	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	

	@Column(name="TYPE")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Column(name="FILESIZE")
	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="REF_CONTEXT")
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	@Column(name="LINK")
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	
	

}
