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
import com.gpi.scm.generic.dtos.ErrorMessage;
import com.gpi.scm.generic.dtos.OrganizationDto;
import com.gpi.scm.generic.dtos.validators.ValidationUtil;
import com.gpi.scm.generic.utils.RestServiceUtils;
import com.gpi.scm.generic.utils.UserContextHolder;

@Path("/rest/roles")
public class OrganizationService {
	private static final Logger logger = Logger.getLogger(OrganizationService.class);

	@GET
	@Path("/organizations")
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
			List<OrganizationDto> orgs = OrganizationDelegate.findOrganizations(organizations);
			return RestServiceUtils.createHttp200OkResponse(orgs);
		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@GET
	@Path("/organizations/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") long id) {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
			if (organizations.contains(id)) {
				OrganizationDto org = OrganizationDelegate.findOrganizationById(id);
				return RestServiceUtils.createHttp200OkResponse(org);
			}
			return ErrorMessage.MyResponse(1, "");

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@POST
	@Path("/organizations")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newOrganization(OrganizationDto organization) {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(organization);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}
			return RestServiceUtils.createHttp200OkResponse(OrganizationDelegate.saveOrganization(organization));
		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@PUT
	@Path("/organizations")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editOrganization(OrganizationDto organization) {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(organization);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}
			return RestServiceUtils.createHttp200OkResponse(OrganizationDelegate.editOrganization(organization));

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@DELETE
	@Path("/organizations/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteById(@PathParam("id") long id) {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
			if (organizations.contains(id)) {
				List<Long> hasNotDeletedChilds = OrganizationDelegate.organizationsIdsTree(id);
				if (hasNotDeletedChilds.isEmpty()) {
					return ErrorMessage.MyResponse(1, " Organization has not deleted childs");

				}
				boolean result = OrganizationDelegate.deleteById(id);
				if (result) {
					return Response.noContent().build();
				}
				return ErrorMessage.MyResponse(2, "");

			}
			return ErrorMessage.MyResponse(1, "");

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

}
