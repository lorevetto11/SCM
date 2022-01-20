package com.gpi.scm.services;

import java.util.ArrayList;
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

import com.gpi.scm.delegates.FlowShapeDelegate;
import com.gpi.scm.generic.dtos.FlowShapeDto;
import com.gpi.scm.generic.dtos.ErrorMessage;
import com.gpi.scm.generic.dtos.validators.ValidationUtil;
import com.gpi.scm.generic.utils.RestServiceUtils;

@Path("/rest/flow")

public class FlowShapeService {
	private static final Logger logger = Logger.getLogger(FlowShapeService.class);

	@GET
	@Path("/shapes")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll(@HeaderParam("diagramId") Long diagramId) {
		try {
			if (diagramId == null) {
				return ErrorMessage.MyResponse(22, "Missing Headers");

			}

			List<FlowShapeDto> shapes = FlowShapeDelegate.findAll(diagramId);
			return RestServiceUtils.createHttp200OkResponse(shapes);
		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@POST
	@Path("/shapes")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newFlowShape(FlowShapeDto shape) {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(shape);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}

			return RestServiceUtils.createHttp200OkResponse(FlowShapeDelegate.save(shape));

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}
	
	@POST
	@Path("/shapes/all")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newFlowShapes(List<FlowShapeDto> shapes) {
		try {
			ValidationUtil valid = new ValidationUtil();
			
			Iterator<FlowShapeDto> it = shapes.iterator();
			while(it.hasNext()){
				String validated = valid.isValid(it.next());
				if (!validated.isEmpty()) {
					return ErrorMessage.MyResponse(4, validated);
				}
			};
			
			return RestServiceUtils.createHttp200OkResponse(FlowShapeDelegate.saveAll(shapes));

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@PUT
	@Path("/shapes")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editFlowShape(FlowShapeDto shape) {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(shape);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}

			return RestServiceUtils.createHttp200OkResponse(FlowShapeDelegate.edit(shape));

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@DELETE
	@Path("/shapes/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteFlowShape(@PathParam("id") long id) {
		try {

			boolean result = FlowShapeDelegate.delete(id);
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
