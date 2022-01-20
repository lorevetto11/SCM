package com.gpi.scm.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.gpi.scm.delegates.GrantDelegate;
import com.gpi.scm.generic.dtos.ErrorMessage;
import com.gpi.scm.generic.dtos.GrantDto;
import com.gpi.scm.generic.utils.RestServiceUtils;

@Path("/rest/roles")
public class GrantService {
	private static final Logger logger = Logger.getLogger(GrantService.class);

	@GET
	@Path("/grants")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll() {
		try {
			List<GrantDto> classes = GrantDelegate.findClasses();
			return RestServiceUtils.createHttp200OkResponse(classes);
		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@POST
	@Path("/grants/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response setGrants(@PathParam("id") long id, List<Long> grants) {
		try {

			/*
			 * List<Long> organizations =
			 * OrganizationDelegate.organizationsIdsTree(UserContextHolder.getUser().
			 * getOrganization().getId()); UserProfileDto profile =
			 * GrantDelegate.setGrants(organizations,id, grants); if(profile!=null) { return
			 * RestServiceUtils.createHttp200OkResponse(profile); }
			 */
			return ErrorMessage.MyResponse(66, "Service Not Available");

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

}
