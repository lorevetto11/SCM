package com.gpi.scm.ejb.sessions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import com.gpi.scm.converters.AttachmentConverter;
import com.gpi.scm.converters.AttachmentMetaConverter;
import com.gpi.scm.ejb.entities.Attachment;
import com.gpi.scm.ejb.entities.AttachmentMetadata;
import com.gpi.scm.ejb.entities.Context;
import com.gpi.scm.ejb.entities.GenericEntity;
import com.gpi.scm.generic.dtos.AttachmentDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interceptors.LoggingInterceptor;
import com.gpi.scm.interfaces.AttachmentLocal;

@Stateless
@Interceptors(value = LoggingInterceptor.class)
public class AttachmentBean extends GenericBean implements AttachmentLocal {
	private static final Logger logger = Logger.getLogger(AttachmentBean.class);

	@Override
	public AttachmentDto saveAttachment(AttachmentDto toSave) throws BusinessException {

		Attachment attachment = AttachmentConverter.dtoToEntity(toSave);

		if (toSave.getContext() != null) {
			Context context = entityManager.find(Context.class, toSave.getContext().getId());
			if (context == null || context.getDeleted()) {
				throw new NoResultException("Context not found");
			}
			attachment.setContext(context);

		}
		Attachment attachmentMerge = entityManager.merge(attachment);

		AttachmentDto dto = new AttachmentDto();
		dto.setId(attachmentMerge.getId());
		return dto;
	}

	@SuppressWarnings("unused")
	@Override
	public List<AttachmentDto> findAttachmentByContextId(long idContext, boolean metadati) throws BusinessException {
		try {

			Session ses = (Session) entityManager.getDelegate();
			ses.enableFilter(GenericEntity.FILTER_DELETED).setParameter(GenericEntity.FILTER_DELETED_PARAM, false);
			List<AttachmentDto> attachments = new ArrayList<>();

			// may get exceptions when managing both classes at the same time
			if (metadati) {
				Query query = entityManager
						.createQuery("Select r from AttachmentMetadata r where r.context.id =:idContext ")
						.setParameter("idContext", idContext);
				List<AttachmentMetadata> results = (List<AttachmentMetadata>) query.getResultList();
				return AttachmentMetaConverter.entityToDto(results, true);
			}

			Query query = entityManager.createQuery("Select r from Attachment r where r.context.id =:idContext ")
					.setParameter("idContext", idContext);
			List<Attachment> results = (List<Attachment>) query.getResultList();
			return AttachmentConverter.entityToDto(results, true);

			// backup criteria mess
			/*
			 * Criteria criteria = ses.createCriteria(Attachment.class);
			 * //criteria.add((Restrictions.eq("context.id", idContext)));
			 * 
			 * ProjectionList prjection = Projections.projectionList();
			 * prjection.add(Projections.property("filename"));
			 * prjection.add(Projections.property("description"));
			 * prjection.add(Projections.property("mimeType"));
			 * prjection.add(Projections.property("type"));
			 * prjection.add(Projections.property("fileSize"));
			 * prjection.add(Projections.property("context"));
			 * prjection.add(Projections.property("version"));
			 * 
			 * 
			 * 
			 * 
			 * criteria.setProjection(prjection);
			 * 
			 * List<Attachment> test= new ArrayList<>(); test=criteria.list(); Context sa=
			 * (Context)test.get(0).getContext();
			 */

			// backup raw method
			/*
			 * Query query = entityManager.
			 * createQuery("select r.filename,r.description,r.mimeType,r.type," +
			 * "r.fileSize,r.context,r.version,r.deleted,r.insertTime,r.insertUser,r.updateTime,r.updateUser,r.deleteTime,r.deleteUser"
			 * + " from Attachment r where r.context.id=:idContext")
			 * .setParameter("idContext", idContext);
			 * 
			 * 
			 * ddd=query.getResultList();
			 * 
			 * for(Object [] data : metadata) { tmp.setFilename(data[0].toString());
			 * tmp.setDescription(data[1].toString()); tmp.setMimeType(data[2].toString());
			 * tmp.setType(data[3].toString()); tmp.setFileSize((Long)data[4]);
			 * tmp.setContext(ContextConverter.entityToDto(((Context)data[5]),false));
			 * tmp.setVersion((Long)data[6]); tmp.setDeleted((boolean)data[7]);
			 * tmp.setInsertTime((Date)data[8]); tmp.setInsertUser(data[9].toString());
			 * tmp.setUpdateTime((Date)data[10]); tmp.setUpdateUser(data[11].toString());
			 * tmp.setDeleteTime((Date)data[12]); tmp.setDeleteUser(data[13].toString());
			 * 
			 * attachments.add(tmp);
			 * 
			 * }
			 */

		} catch (NoResultException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		return null;
	}

	@Override
	public AttachmentDto editAttachment(AttachmentDto attachment) throws BusinessException {

		AttachmentMetadata toEdit = entityManager.find(AttachmentMetadata.class, attachment.getId());
		if (toEdit == null || toEdit.getDeleted() == true) {
			throw new NoResultException("Attachment not found");
		}
		if (attachment.getContext() != null) {
			Context context = entityManager.find(Context.class, attachment.getContext().getId());
			if (!context.getDeleted())
				toEdit.setContext(context);
		}
		
		// toEdit.setLink(attachment.getLink());   if needed
		toEdit.setDescription(attachment.getDescription());

		AttachmentMetadata attachmentM = entityManager.merge(toEdit);
		return AttachmentMetaConverter.entityToDto(attachmentM, true);
	}

	@Override
	public boolean deleteAttachment(long idAttachment) throws BusinessException {

		AttachmentMetadata toDelete = entityManager.find(AttachmentMetadata.class, idAttachment);
		if (toDelete == null || toDelete.getDeleted()) {
			throw new NoResultException("Attachment not found");
		}
		toDelete.setDeleted(true);
		entityManager.merge(toDelete);
		return true;
	}

}
