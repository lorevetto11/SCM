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

import com.gpi.scm.delegates.MonitoringDelegate;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.generic.dtos.MonitoringDto;
import com.gpi.scm.generic.dtos.validators.ValidationUtil;
import com.gpi.scm.generic.dtos.ErrorMessage;
import com.gpi.scm.generic.utils.RestServiceUtils;
import com.gpi.scm.generic.utils.UserContextHolder;

@Path("/rest/monitorings")

public class MonitoringService {
	private static final Logger logger = Logger.getLogger(MonitoringService.class);

	@GET
	@Path("/monitorings")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll(@HeaderParam("organizationId") Long organizationId,
			@HeaderParam("userRoleId") Long userRoleId) {
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
			
			List<MonitoringDto> monitorings = null;
			
			if(userRoleId != null) {
				monitorings = MonitoringDelegate.findByRole(organizationId, userRoleId);
			} else {
				monitorings = MonitoringDelegate.findAll(organizationId);
			}
						 
			return RestServiceUtils.createHttp200OkResponse(monitorings);
		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}
/*
	@GET
	@Path("/monitorings/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findByRoleId(@PathParam("id") long id) {
		try {
			List<MonitoringDto> monitoring = MonitoringDelegate.findByRole(id,
					UserContextHolder.getUser().getOrganization().getId());
			if (monitoring != null) {
				return RestServiceUtils.createHttp200OkResponse(monitoring);
			}
			return ErrorMessage.MyResponse(11, "");
		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}
*/
	@POST
	@Path("/monitorings")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newMonitoring(MonitoringDto monitoring) {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(monitoring);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}

			return RestServiceUtils.createHttp200OkResponse(MonitoringDelegate.save(monitoring));

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@PUT
	@Path("/monitorings")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editMonitoring(MonitoringDto monitoring) {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(monitoring);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}

			return RestServiceUtils.createHttp200OkResponse(MonitoringDelegate.edit(monitoring));

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@DELETE
	@Path("/monitorings/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteMonitoring(@PathParam("id") long id) {
		try {
			boolean result = MonitoringDelegate.delete(id);
			if (result) {
				return Response.noContent().build();
			}

			return ErrorMessage.MyResponse(4, "");
		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}

	}

}
