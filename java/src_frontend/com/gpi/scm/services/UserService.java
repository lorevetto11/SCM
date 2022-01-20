package com.gpi.scm.services;

import java.util.List;

import javax.persistence.NonUniqueResultException;
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

import com.gpi.scm.authentication.TokenManager;
import com.gpi.scm.delegates.OrganizationDelegate;
import com.gpi.scm.delegates.UserDelegate;
import com.gpi.scm.generic.constants.ScmConstants;
import com.gpi.scm.generic.dtos.ErrorMessage;
import com.gpi.scm.generic.dtos.UserDto;
import com.gpi.scm.generic.dtos.validators.ValidationUtil;
import com.gpi.scm.generic.utils.RestServiceUtils;
import com.gpi.scm.generic.utils.UserContextHolder;
import com.gpi.scm.utils.AllowAnonymous;

@Path("/rest/roles")
public class UserService {

	private static final Logger logger = Logger.getLogger(UserService.class);
	private TokenManager validator = new TokenManager();

	@PUT
	@Path("/login")
	@AllowAnonymous
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(UserDto login) {
		UserDto user = UserDelegate.findUserByLogin(login);
		if (user != null) {
			final String token = validator.generateToken(user.getIdAsString());
			return Response.ok(user).header(ScmConstants.TOKEN, token).build();
		}
		return ErrorMessage.MyResponse(0, "");
	}

	@GET
	@Path("/users")
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
			List<UserDto> users = UserDelegate.findUsers(organizations);
			return RestServiceUtils.createHttp200OkResponse(users);
		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@GET
	@Path("/users/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") long id) {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
			UserDto user = UserDelegate.findUserById(id, organizations);
			if (user != null) {
				return RestServiceUtils.createHttp200OkResponse(user);
			}
			return ErrorMessage.MyResponse(11, this.getClass().getSimpleName());

		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@POST
	@Path("/users")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response newUser(UserDto user) {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(user);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}

			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
			if (organizations.contains(user.getOrganization().getId())) {

				return RestServiceUtils.createHttp200OkResponse(UserDelegate.saveUser(user));

			}
			return ErrorMessage.MyResponse(1, "");
		} 
		catch (NonUniqueResultException be) {
			return ErrorMessage.MyResponse(14, ExceptionUtils.getRootCause(be).toString());
		}
		catch (RuntimeException be) {
			return ErrorMessage.MyResponse(13, ExceptionUtils.getRootCause(be).toString());
		}
		catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}

	}

	@PUT
	@Path("/users")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editUser(UserDto user) {
		try {
			ValidationUtil valid = new ValidationUtil();
			String validated = valid.isValid(user);
			if (!validated.isEmpty()) {
				return ErrorMessage.MyResponse(4, validated);
			}

			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
			if (organizations.contains(user.getOrganization().getId())) {
				return RestServiceUtils.createHttp200OkResponse(UserDelegate.editUser(user));
			}
			return ErrorMessage.MyResponse(1, "");
		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

	@DELETE
	@Path("/users/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUser(@PathParam("id") long id) {
		try {
			List<Long> organizations = OrganizationDelegate
					.organizationsIdsTree(UserContextHolder.getUser().getOrganization().getId());
			boolean result = UserDelegate.deleteUser(id, organizations);
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
