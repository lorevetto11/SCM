package com.gpi.scm.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.gpi.scm.delegates.AttachmentDelegate;
import com.gpi.scm.generic.dtos.AttachmentDto;
import com.gpi.scm.generic.dtos.ErrorMessage;
import com.gpi.scm.generic.dtos.validators.ValidationUtil;
import com.gpi.scm.generic.utils.RestServiceUtils;

@Path("/rest/context")
public class AttachmentService {

	private static final Logger logger = Logger.getLogger(AttachmentService.class);

	@POST
	@Path("/attachments")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newAtachment(AttachmentDto attachment) {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(attachment);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}

			AttachmentDto toSave = AttachmentDelegate.saveAttachment(attachment);
			return RestServiceUtils.createHttp200OkResponse(toSave);
		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@PUT
	@Path("/attachments")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editAttachment(AttachmentDto attachment) {
		try {

			AttachmentDto toEdit = AttachmentDelegate.editAttachment(attachment);
			return RestServiceUtils.createHttp200OkResponse(toEdit);
		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@GET
	@Path("/attachments/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findByContextId(@PathParam("id") long idContext, @HeaderParam("metadati") boolean metadati) {
		try {

			List<AttachmentDto> attachments = AttachmentDelegate.findById(idContext, metadati);
			return RestServiceUtils.createHttp200OkResponse(attachments);
		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@DELETE
	@Path("/attachments/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteAttachment(@PathParam("id") long idAttachment) {
		try {

			boolean result = AttachmentDelegate.delete(idAttachment);
			if (result) {
				return Response.noContent().build();
			}
			return ErrorMessage.MyResponse(2, "");

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}
}
