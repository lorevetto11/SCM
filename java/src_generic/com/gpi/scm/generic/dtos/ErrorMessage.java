package com.gpi.scm.generic.dtos;

import java.io.Serializable;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


public class ErrorMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static ErrorMessage INVALID_CREDENTIALS = new ErrorMessage(-1, "Invalid username or password");


	private int code;
	private String message;

	public ErrorMessage(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code=code;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message)
	{
		this.message= message;
	}

	public static Response MyResponse(int codes,String message)
	{

		switch(codes)
		{
		case 0: return Response.status(Status.UNAUTHORIZED).entity(new ErrorMessage(0, "Invalid username or password " )).type(MediaType.APPLICATION_JSON).build();

		case 1: return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorMessage(1, "Access denied ! " )).type(MediaType.APPLICATION_JSON).build();

		case 2: return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorMessage(2, "Error in deletion : " + message )).type(MediaType.APPLICATION_JSON).build();

		case 3: return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorMessage(3, "Errors on path : " + message )).type(MediaType.APPLICATION_JSON).build();

		case 4: return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorMessage(4,"Validation Error : " + message)).type(MediaType.APPLICATION_JSON).build();

		case 5: return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorMessage(5,"Service could not finalize : " + message)).type(MediaType.APPLICATION_JSON).build();

		case 6: return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorMessage(6,"Query/Argument error  : " + message)).type(MediaType.APPLICATION_JSON).build();

		case 7: return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorMessage(7,"Constraint Error : " + message)).type(MediaType.APPLICATION_JSON).build();

		case 8: return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorMessage(8,"Validation Exception : " + message)).type(MediaType.APPLICATION_JSON).build();

		case 9:  return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorMessage(9,"Hibernate Exception : " + message)).type(MediaType.APPLICATION_JSON).build();

		case 10:  return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorMessage(10,"Applicaton Exception : " + message)).type(MediaType.APPLICATION_JSON).build();

		case 11: return Response.status(Status.NOT_FOUND).entity(new ErrorMessage(11, "Entity not found :"+ message )).type(MediaType.APPLICATION_JSON).build();
		
		case 12: return Response.ok().build();

		case 13: return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorMessage(13, "Error sending mail :"+ message )).type(MediaType.APPLICATION_JSON).build();
		
		case 14: return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorMessage(14, "Error adding user :"+ message )).type(MediaType.APPLICATION_JSON).build();
		
		case 15: return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorMessage(15,"Missing Params")).type(MediaType.APPLICATION_JSON).build();

		
		
		default: return Response.status(Status.BAD_GATEWAY).entity(new ErrorMessage(-1,"Unknown error : " + message)).type(MediaType.APPLICATION_JSON).build();

		}


		
		
		
	
		
	}
}
