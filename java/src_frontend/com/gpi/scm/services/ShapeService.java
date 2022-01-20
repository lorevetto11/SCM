
package com.gpi.scm.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.gpi.scm.delegates.ShapeDelegate;
import com.gpi.scm.generic.dtos.ErrorMessage;
import com.gpi.scm.generic.dtos.ShapeDto;
import com.gpi.scm.generic.dtos.validators.ValidationUtil;
import com.gpi.scm.generic.utils.RestServiceUtils;

@Path("/rest/prerequisites")
public class ShapeService {
	private static final Logger logger = Logger.getLogger(ShapeService.class);

	@GET
	@Path("/shapes")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll() {
		try {
			List<ShapeDto> shapes = ShapeDelegate.findShape();
			return RestServiceUtils.createHttp200OkResponse(shapes);
		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@GET
	@Path("/shapes/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") long id) {
		try {
			ShapeDto shape = ShapeDelegate.findShapeById(id);
			return RestServiceUtils.createHttp200OkResponse(shape);
			
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
	public Response newShape(ShapeDto shape) {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(shape);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}
			return RestServiceUtils.createHttp200OkResponse(ShapeDelegate.saveShape(shape));

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
	public Response editShape(ShapeDto shape) {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(shape);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}
			return RestServiceUtils.createHttp200OkResponse(ShapeDelegate.editShape(shape));

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
	public Response deleteShape(@PathParam("id") long id) {
		try {
			boolean result = ShapeDelegate.deleteShape(id);
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