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

import com.gpi.scm.delegates.FlowRelationDelegate;
import com.gpi.scm.generic.dtos.FlowRelationDto;
import com.gpi.scm.generic.dtos.ErrorMessage;
import com.gpi.scm.generic.dtos.validators.ValidationUtil;
import com.gpi.scm.generic.utils.RestServiceUtils;

@Path("/rest/flow")

public class FlowRelationService {
	private static final Logger logger = Logger.getLogger(FlowRelationService.class);

	@GET
	@Path("/relations")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll(@HeaderParam("diagramId") Long diagramId) {
		try {
			if (diagramId == null) {
				return ErrorMessage.MyResponse(22, "Missing Headers");
			}
			List<FlowRelationDto> relations = FlowRelationDelegate.findAll(diagramId);
			return RestServiceUtils.createHttp200OkResponse(relations);
		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@POST
	@Path("/relations")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newFlowRelation(FlowRelationDto relation) {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(relation);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}

			return RestServiceUtils.createHttp200OkResponse(FlowRelationDelegate.save(relation));

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@PUT
	@Path("/relations")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editFlowRelation(FlowRelationDto relation) {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(relation);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}

			return RestServiceUtils.createHttp200OkResponse(FlowRelationDelegate.edit(relation));

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@DELETE
	@Path("/relations/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteFlowRelation(@PathParam("id") long id) {
		try {

			boolean result = FlowRelationDelegate.delete(id);
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
