package com.gpi.scm.services;

import java.util.Iterator;
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

import com.gpi.scm.delegates.FlowAnchorPointDelegate;
import com.gpi.scm.generic.dtos.FlowAnchorPointDto;
import com.gpi.scm.generic.dtos.ErrorMessage;
import com.gpi.scm.generic.dtos.validators.ValidationUtil;
import com.gpi.scm.generic.utils.RestServiceUtils;

@Path("/rest/flow")

public class FlowAnchorPointService {
	private static final Logger logger = Logger.getLogger(FlowAnchorPointService.class);

	@GET
	@Path("/anchorPoints")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll(@HeaderParam("diagramId") Long diagramId) {
		try {
			if (diagramId == null) {
				return ErrorMessage.MyResponse(22, "Missing Headers");

			}

			List<FlowAnchorPointDto> points = FlowAnchorPointDelegate.findAll(diagramId);
			return RestServiceUtils.createHttp200OkResponse(points);
		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@POST
	@Path("/anchorPoints")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newFlowAnchorPoint(FlowAnchorPointDto point) {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(point);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}

			return RestServiceUtils.createHttp200OkResponse(FlowAnchorPointDelegate.save(point));

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}
	
	@POST
	@Path("/anchorPoints/all")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newFlowAnchorPointAll(List<FlowAnchorPointDto> points) {
		try {
			Iterator<FlowAnchorPointDto> it = points.iterator();
			while(it.hasNext()){
				ValidationUtil valid = new ValidationUtil();
				String validated = valid.isValid(it.next());
				if (!validated.isEmpty()) {
					return ErrorMessage.MyResponse(4, validated);
				}
			};
			
			return RestServiceUtils.createHttp200OkResponse(FlowAnchorPointDelegate.saveAll(points));

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@PUT
	@Path("/anchorPoints")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editFlowAnchorPoint(FlowAnchorPointDto point) {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(point);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}

			return RestServiceUtils.createHttp200OkResponse(FlowAnchorPointDelegate.edit(point));

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}
	
	@PUT
	@Path("/anchorPoints/all")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editFlowAnchorPoint(List<FlowAnchorPointDto> points) {
		try {
			Iterator<FlowAnchorPointDto> it = points.iterator();
			while(it.hasNext()){
				ValidationUtil valid = new ValidationUtil();
				String validated = valid.isValid(it.next());
				if (!validated.isEmpty()) {
					return ErrorMessage.MyResponse(4, validated);
				}
			};
			return RestServiceUtils.createHttp200OkResponse(FlowAnchorPointDelegate.editAll(points));

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@DELETE
	@Path("/anchorPoints/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteFlowAnchorPoint(@PathParam("id") long id) {
		try {

			boolean result = FlowAnchorPointDelegate.delete(id);
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
