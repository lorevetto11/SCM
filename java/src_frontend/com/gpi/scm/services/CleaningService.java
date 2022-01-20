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

import com.gpi.scm.delegates.CleaningDelegate;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.generic.dtos.BasePrerequisiteDto;
import com.gpi.scm.generic.dtos.ErrorMessage;
import com.gpi.scm.generic.dtos.validators.ValidationUtil;
import com.gpi.scm.generic.utils.RestServiceUtils;
import com.gpi.scm.generic.utils.UserContextHolder;

@Path("/rest/prerequisites")

public class CleaningService {
	private static final Logger logger = Logger.getLogger(CleaningService.class);

	@GET
	@Path("/cleanings")
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
			List<BasePrerequisiteDto> cleanings = CleaningDelegate.findAll(organizationId);
			return RestServiceUtils.createHttp200OkResponse(cleanings);
		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@GET
	@Path("/cleanings/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") long id) {
		try {

			BasePrerequisiteDto cleaning = CleaningDelegate.findById(id);
			return RestServiceUtils.createHttp200OkResponse(cleaning);

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@GET
	@Path("/cleanings/context/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findByContextId(@PathParam("id") long id) {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
			BasePrerequisiteDto supply = CleaningDelegate.findByContextId(id, organizations);
			return RestServiceUtils.createHttp200OkResponse(supply);

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}
	
	@POST
	@Path("/cleanings")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newCleaning(BasePrerequisiteDto cleaning) {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(cleaning);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}
			return RestServiceUtils.createHttp200OkResponse(CleaningDelegate.save(cleaning));

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@PUT
	@Path("/cleanings")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editCleaning(BasePrerequisiteDto cleaning) {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(cleaning);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}

			return RestServiceUtils.createHttp200OkResponse(CleaningDelegate.edit(cleaning));

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@DELETE
	@Path("/cleanings/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCleaning(@PathParam("id") long id) {
		try {

			boolean result = CleaningDelegate.delete(id);
			if (result) {
				return Response.noContent().build();
			}
			return ErrorMessage.MyResponse(2, this.getClass().getSimpleName());

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}

	}

}
