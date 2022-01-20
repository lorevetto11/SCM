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

import com.gpi.scm.delegates.ProcessCheckOutcomeDelegate;
import com.gpi.scm.generic.dtos.ErrorMessage;
import com.gpi.scm.generic.dtos.ProcessCheckOutcomeDto;
import com.gpi.scm.generic.dtos.validators.ValidationUtil;
import com.gpi.scm.generic.utils.RestServiceUtils;

@Path("/rest/check/process")
public class ProcessCheckOutcomeService {
	private static final Logger logger = Logger.getLogger(ProcessCheckOutcomeService.class);

	@GET
	@Path("/outcome")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll(@HeaderParam("processCheckPlanningId") Long processCheckPlanningId,
			@HeaderParam("processCheckId") Long processCheckId) {
		try {
			if (processCheckPlanningId == null && processCheckId == null) {
				return ErrorMessage.MyResponse(22, "Missing Header");

			}
			List<ProcessCheckOutcomeDto> processChecks = ProcessCheckOutcomeDelegate
					.findProcessCheckOutcomes(processCheckPlanningId, processCheckId);

			return RestServiceUtils.createHttp200OkResponse(processChecks);
		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@POST
	@Path("/outcome")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newProcessCheckOutcomes(ProcessCheckOutcomeDto processCheckOutcome) {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(processCheckOutcome);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}
			return RestServiceUtils
					.createHttp200OkResponse(ProcessCheckOutcomeDelegate.saveProcessCheckOutcome(processCheckOutcome));
		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@PUT
	@Path("/outcome")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editProcessCheckOutcomes(ProcessCheckOutcomeDto processCheckOutcome) {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(processCheckOutcome);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}
			return RestServiceUtils
					.createHttp200OkResponse(ProcessCheckOutcomeDelegate.editProcessCheckOutcome(processCheckOutcome));

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@DELETE
	@Path("/outcome/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteById(@PathParam("id") long id) {
		try {

			boolean result = ProcessCheckOutcomeDelegate.deleteById(id);
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
