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

import com.gpi.scm.delegates.AnalysisValueDelegate;
import com.gpi.scm.generic.dtos.AnalysisValueDto;
import com.gpi.scm.generic.dtos.ErrorMessage;
import com.gpi.scm.generic.dtos.validators.ValidationUtil;
import com.gpi.scm.generic.utils.RestServiceUtils;

@Path("/rest/analysis")

public class AnalysisValueService {
	private static final Logger logger = Logger.getLogger(AnalysisValueService.class);

	@GET
	@Path("/value")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll(@HeaderParam("analysisParameterId") Long analysisParameterId) {
		try {
			if (analysisParameterId == null) {
				return ErrorMessage.MyResponse(22, "Missing Headers");

			}
	
			List<AnalysisValueDto> values = AnalysisValueDelegate.findAll(analysisParameterId);
			return RestServiceUtils.createHttp200OkResponse(values);
		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@POST
	@Path("/value")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newAnalisisValue(AnalysisValueDto value) {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(value);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}

			return RestServiceUtils.createHttp200OkResponse(AnalysisValueDelegate.save(value));

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@PUT
	@Path("/value")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editAnalisisValue(AnalysisValueDto value) {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(value);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}

				return RestServiceUtils.createHttp200OkResponse(AnalysisValueDelegate.edit(value));
			
		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@DELETE
	@Path("/value/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteAnalisisValue(@PathParam("id") long id) {
		try {


			boolean result = AnalysisValueDelegate.delete(id);
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
