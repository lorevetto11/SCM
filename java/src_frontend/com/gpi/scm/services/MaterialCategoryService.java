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

import com.gpi.scm.delegates.MaterialCategoryDelegate;
import com.gpi.scm.generic.dtos.MaterialCategoryDto;
import com.gpi.scm.generic.dtos.validators.ValidationUtil;
import com.gpi.scm.generic.dtos.ErrorMessage;
import com.gpi.scm.generic.utils.RestServiceUtils;

@Path("/rest/material")

public class MaterialCategoryService {
	private static final Logger logger = Logger.getLogger(MaterialCategoryService.class);

	@GET
	@Path("/categories")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll() {
		try {

			List<MaterialCategoryDto> materials = MaterialCategoryDelegate.findAll();
			return RestServiceUtils.createHttp200OkResponse(materials);
		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@POST
	@Path("/categories")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newMaterialCategory(MaterialCategoryDto material) {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(material);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}

			return RestServiceUtils.createHttp200OkResponse(MaterialCategoryDelegate.save(material));

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@PUT
	@Path("/categories")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editMaterialCategory(MaterialCategoryDto material) {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(material);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}

			return RestServiceUtils.createHttp200OkResponse(MaterialCategoryDelegate.edit(material));

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@DELETE
	@Path("/categories/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteMaterialCategory(@PathParam("id") long id) {
		try {
			boolean result = MaterialCategoryDelegate.delete(id);
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
