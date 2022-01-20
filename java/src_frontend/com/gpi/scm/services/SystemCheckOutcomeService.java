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

import com.gpi.scm.delegates.SystemCheckOutcomeDelegate;
import com.gpi.scm.generic.dtos.ErrorMessage;
import com.gpi.scm.generic.dtos.SystemCheckOutcomeDto;
import com.gpi.scm.generic.dtos.validators.ValidationUtil;
import com.gpi.scm.generic.utils.RestServiceUtils;

@Path("/rest/check/system")
public class SystemCheckOutcomeService {
	private static final Logger logger = Logger.getLogger(SystemCheckOutcomeService.class);

	@GET
	@Path("/outcome")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll(@HeaderParam("systemCheckRequirementId") Long systemCheckRequirementId) {
		try {
			if (systemCheckRequirementId == null) {
				return ErrorMessage.MyResponse(22, "Missing Header");
	
			} 
			List<SystemCheckOutcomeDto> systemChecks = SystemCheckOutcomeDelegate.
					findSystemCheckOutcomes(systemCheckRequirementId);

			return RestServiceUtils.createHttp200OkResponse(systemChecks);
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
	public Response newSystemCheckOutcomes(SystemCheckOutcomeDto sysCheckOutcome) {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(sysCheckOutcome);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}
			return RestServiceUtils.createHttp200OkResponse(
					SystemCheckOutcomeDelegate.saveSystemCheckOutcome(sysCheckOutcome));
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
	public Response editSystemCheckOutcomes(SystemCheckOutcomeDto sysCheckOutcome) {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(sysCheckOutcome);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}
			return RestServiceUtils.createHttp200OkResponse(
					SystemCheckOutcomeDelegate.editSystemCheckOutcome(sysCheckOutcome));

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

			boolean result = SystemCheckOutcomeDelegate.deleteById(id);
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
