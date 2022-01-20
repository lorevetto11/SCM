package com.gpi.scm.services;

import java.util.ArrayList;
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

import com.gpi.scm.delegates.ParticipantDelegate;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.generic.dtos.ParticipantDto;
import com.gpi.scm.generic.dtos.validators.ValidationUtil;
import com.gpi.scm.generic.dtos.ErrorMessage;
import com.gpi.scm.generic.utils.RestServiceUtils;
import com.gpi.scm.generic.utils.UserContextHolder;

@Path("/rest/trainings")

public class ParticipantService {
	private static final Logger logger = Logger.getLogger(ParticipantService.class);

	@GET
	@Path("/participants")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll(@HeaderParam("roleId") Long roleId) {
		try {
			// List<Long>
			// organizations=OrganizationDelegate.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
			List<ParticipantDto> participants = new ArrayList<>();
			if (roleId != null) {
				participants = ParticipantDelegate.findParticipants(roleId);

			} else {
				participants = ParticipantDelegate.findParticipants();
			}

			return RestServiceUtils.createHttp200OkResponse(participants);

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@GET
	@Path("/participants/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") long id) {
		try {

			ParticipantDto participant = ParticipantDelegate.findParticipantById(id);
			if (participant != null) {
				return RestServiceUtils.createHttp200OkResponse(participant);

			}
			return ErrorMessage.MyResponse(11, this.getClass().getSimpleName());

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@POST
	@Path("/participants")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newParticipant(ParticipantDto toSave) {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(toSave);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
			return RestServiceUtils.createHttp200OkResponse(ParticipantDelegate.saveParticipant(toSave, organizations));

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@PUT
	@Path("/participants")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editParticipant(ParticipantDto toSave) {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(toSave);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
			return RestServiceUtils.createHttp200OkResponse(ParticipantDelegate.editParticipant(toSave, organizations));

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@DELETE
	@Path("/participants/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteParticipant(@PathParam("id") long id) {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());

			boolean result = ParticipantDelegate.delete(id, organizations);
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
