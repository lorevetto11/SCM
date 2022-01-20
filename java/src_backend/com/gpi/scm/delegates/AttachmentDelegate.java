package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.AttachmentDto;
import com.gpi.scm.interfaces.AttachmentLocal;

public class AttachmentDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(AttachmentDelegate.class);

	private static AttachmentLocal getBean() throws NamingException {
		return (AttachmentLocal) getContext().lookup(AttachmentLocal.JNDI_NAME);
	}

	public static AttachmentDto saveAttachment(AttachmentDto attachment) {
		try {
			return getBean().saveAttachment(attachment);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	public static List<AttachmentDto> findById(long idContext, boolean metadati) {
		try {
			return getBean().findAttachmentByContextId(idContext,metadati);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	public static AttachmentDto editAttachment(AttachmentDto attachment) {
		try {
			return getBean().editAttachment(attachment);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	public static boolean delete(long idAttachment) {
		try {
			return getBean().deleteAttachment(idAttachment);
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}


}
