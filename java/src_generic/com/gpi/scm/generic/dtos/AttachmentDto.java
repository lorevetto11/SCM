package com.gpi.scm.generic.dtos;



import javax.validation.constraints.NotNull;

public class AttachmentDto extends GenericDto {


	private static final long serialVersionUID = 1L;
	private String filename;
	private String description;
	private String mimeType;
	private byte[] data;
	private String type;
	private Long fileSize;
	private String link;
	private ContextDto context; 
	
	
	@NotNull
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	@NotNull
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	@NotNull
	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
	@NotNull
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@NotNull
	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	
	public ContextDto getContext() {
		return context;
	}

	public void setContext(ContextDto context) {
		this.context = context;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	

}
