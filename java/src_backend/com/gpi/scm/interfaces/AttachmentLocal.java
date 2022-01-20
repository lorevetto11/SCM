package com.gpi.scm.interfaces;


import java.util.List;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.AttachmentDto;
import com.gpi.scm.generic.exceptions.BusinessException;

public interface AttachmentLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "AttachmentBean" + "!" + AttachmentLocal.class.getName();

	public AttachmentDto saveAttachment(AttachmentDto attachment) throws BusinessException;

	public List<AttachmentDto> findAttachmentByContextId(long idContext, boolean metadati) throws BusinessException;

	public AttachmentDto editAttachment(AttachmentDto attachment)throws BusinessException;

	public boolean deleteAttachment(long idAttachment)throws BusinessException;;



}
