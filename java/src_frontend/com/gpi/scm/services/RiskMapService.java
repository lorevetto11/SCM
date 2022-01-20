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

import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.delegates.RiskMapDelegate;
import com.gpi.scm.generic.dtos.ErrorMessage;
import com.gpi.scm.generic.dtos.RiskMapDto;
import com.gpi.scm.generic.dtos.validators.ValidationUtil;
import com.gpi.scm.generic.utils.RestServiceUtils;

@Path("/rest/danger")
public class RiskMapService {
	private static final Logger logger = Logger.getLogger(RiskMapService.class);

	@GET
	@Path("/riskMap")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll(@HeaderParam("dangerId") Long dangerId, @HeaderParam("elementId") Long elementId,
			@HeaderParam("organizationId") Long organizationId) {
		try {
			
			if (dangerId == null && elementId == null && organizationId == null)
				return ErrorMessage.MyResponse(15, "");

			List<RiskMapDto> classes = RiskMapDelegate.findMaps(elementId, dangerId, organizationId);
			return RestServiceUtils.createHttp200OkResponse(classes);
		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@POST
	@Path("/riskMap")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newRiskMap(RiskMapDto toSave) {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(toSave);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}
			return RestServiceUtils.createHttp200OkResponse(RiskMapDelegate.saveMap(toSave));

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@PUT
	@Path("/riskMap")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editRiskMap(RiskMapDto toSave) {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(toSave);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}

			return RestServiceUtils.createHttp200OkResponse(RiskMapDelegate.editMap(toSave));

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@DELETE
	@Path("/riskMap/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteRiskMap(@PathParam("id") long id) {
		try {

			boolean result = RiskMapDelegate.delete(id);
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
