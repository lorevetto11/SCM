package com.gpi.scm.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.gpi.scm.delegates.PrerequisiteTypeDelegate;
import com.gpi.scm.generic.dtos.ErrorMessage;
import com.gpi.scm.generic.dtos.PrerequisiteTypeDto;
import com.gpi.scm.generic.utils.RestServiceUtils;

@Path("/rest/prerequisites")

public class PrerequisiteTypeService {
	private static final Logger logger = Logger.getLogger(PrerequisiteTypeService.class);

	@GET
	@Path("/prerequisiteTypes")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll() {
		try {

			List<PrerequisiteTypeDto> prerequisiteTypes = PrerequisiteTypeDelegate.findAll();
			return RestServiceUtils.createHttp200OkResponse(prerequisiteTypes);
		} catch (Exception be) {
			logger.error("Error creating document sign request. " + be.getMessage());
			String test = ExceptionUtils.getRootCause(be).toString();
			return ErrorMessage.MyResponse(22, test);
		}
	}

}
