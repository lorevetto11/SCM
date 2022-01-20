package com.gpi.scm.services;

import javax.persistence.NoResultException;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.hibernate.HibernateException;

import com.gpi.scm.generic.dtos.ErrorMessage;
import com.gpi.scm.generic.exceptions.ApplicationException;
import com.gpi.scm.generic.exceptions.BusinessException;

@Provider
public class WSExceptionHandler implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(final Exception exception) {
		// TODO

		if (exception instanceof NotFoundException) {
			return ErrorMessage.MyResponse(3, exception.getMessage());

		}
		if (exception instanceof NoResultException) {

			return ErrorMessage.MyResponse(4, exception.getMessage());

		}
		if (exception instanceof BusinessException) {
			return ErrorMessage.MyResponse(5, exception.getMessage());

		}

		if (exception instanceof IllegalArgumentException) {
			return ErrorMessage.MyResponse(6, exception.getMessage());

		}
		if (exception instanceof ConstraintViolationException) {
			return ErrorMessage.MyResponse(7, exception.getMessage());

		}
		if (exception instanceof ValidationException) {
			return ErrorMessage.MyResponse(8, exception.getMessage());

		}
		if (exception instanceof HibernateException) {
			return ErrorMessage.MyResponse(9, exception.getMessage());
		}

		if (exception instanceof ApplicationException) {
			return ErrorMessage.MyResponse(10, exception.getMessage());
		}

		return ErrorMessage.MyResponse(42, exception.getMessage());
	}
}
