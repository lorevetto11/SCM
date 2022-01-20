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
import com.gpi.scm.delegates.AirConditioningTypeDelegate;
import com.gpi.scm.generic.dtos.ErrorMessage;
import com.gpi.scm.generic.dtos.validators.ValidationUtil;
import com.gpi.scm.generic.dtos.AirConditioningTypeDto;
import com.gpi.scm.generic.utils.RestServiceUtils;
import com.gpi.scm.generic.utils.UserContextHolder;

@Path("/rest/prerequisites/airConditioning/types")
public class AirConditioningTypeService {
	private static final Logger logger = Logger.getLogger(AirConditioningTypeService.class);

	@GET
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAllReverseTree(@HeaderParam("organizationId") Long organizationId) {
		try {
			if (organizationId != null) {
				List<Long> myOrganizations = OrganizationDelegate
						.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
				if (!myOrganizations.contains(organizationId)) {
					return ErrorMessage.MyResponse(1, "");
				}
			} else {
				organizationId = UserContextHolder.getUser().getOrganization().getId();
			}
			List<Long> organizations = OrganizationDelegate.organizationsIdsReverseTree(organizationId);
			List<AirConditioningTypeDto> equipmenTypes = AirConditioningTypeDelegate.findAirConditioningTypes(organizations);

			return RestServiceUtils.createHttp200OkResponse(equipmenTypes);
		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@GET
	@Path("/all")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll(@HeaderParam("organizationId") Long organizationId) {
		try {
			if (organizationId != null) {
				List<Long> myOrganizations = OrganizationDelegate
						.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
				if (!myOrganizations.contains(organizationId)) {
					return ErrorMessage.MyResponse(1, "");
				}
			} else {
				organizationId = UserContextHolder.getUser().getOrganization().getId();
			}
			List<Long> organizations = OrganizationDelegate.organizationsIdsTree(organizationId);
			organizations.addAll(OrganizationDelegate.organizationsIdsReverseTree(organizationId));

			List<AirConditioningTypeDto> airconditioningTypes = AirConditioningTypeDelegate.findAirConditioningTypes(organizations);

			return RestServiceUtils.createHttp200OkResponse(airconditioningTypes);
		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@POST
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newAirConditioningTypes(AirConditioningTypeDto airconditioningType) {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(airconditioningType);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}
			return RestServiceUtils.createHttp200OkResponse(AirConditioningTypeDelegate.saveAirConditioningType(airconditioningType));
		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@PUT
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editAirConditioningTypes(AirConditioningTypeDto airconditioningType) {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(airconditioningType);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}
			return RestServiceUtils.createHttp200OkResponse(AirConditioningTypeDelegate.editAirConditioningType(airconditioningType));

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@DELETE
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteById(@PathParam("id") long id) {
		try {

			boolean result = AirConditioningTypeDelegate.deleteById(id);
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
