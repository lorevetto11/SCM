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

import org.apache.commons.lang3.exception.*;
import org.apache.log4j.Logger;

import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.delegates.WasteDisposalDelegate;
import com.gpi.scm.generic.dtos.BasePrerequisiteDto;
import com.gpi.scm.generic.dtos.ErrorMessage;
import com.gpi.scm.generic.dtos.validators.ValidationUtil;
import com.gpi.scm.generic.utils.RestServiceUtils;
import com.gpi.scm.generic.utils.UserContextHolder;

@Path("/rest/prerequisites")

public class WasteDisposalService {
	private static final Logger logger = Logger.getLogger(WasteDisposalService.class);

	@GET
	@Path("/wasteDisposal")
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
			List<BasePrerequisiteDto> layouts = WasteDisposalDelegate.findAll(organizationId);
			return RestServiceUtils.createHttp200OkResponse(layouts);
		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@GET
	@Path("/wasteDisposal/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") long id) {
		try {

			BasePrerequisiteDto disposal = WasteDisposalDelegate.findById(id);
			return RestServiceUtils.createHttp200OkResponse(disposal);

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}
	
	@GET
	@Path("/wasteDisposal/context/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findByContextId(@PathParam("id") long id) {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
			BasePrerequisiteDto supply = WasteDisposalDelegate.findByContextId(id, organizations);
			return RestServiceUtils.createHttp200OkResponse(supply);

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@POST
	@Path("/wasteDisposal")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newWasteDisposal(BasePrerequisiteDto disposal) throws Exception {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(disposal);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}

			return RestServiceUtils.createHttp200OkResponse(WasteDisposalDelegate.save(disposal));

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());

			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@PUT
	@Path("/wasteDisposal")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editWasteDisposal(BasePrerequisiteDto disposal) {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(disposal);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}
			return RestServiceUtils.createHttp200OkResponse(WasteDisposalDelegate.edit(disposal));

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@DELETE
	@Path("/wasteDisposal/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteWasteDisposal(@PathParam("id") long id) {
		try {

			boolean result = WasteDisposalDelegate.delete(id);
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
