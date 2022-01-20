package com.gpi.scm.generic.exceptions;




/**
 * 
 * @author Gpi Spa
 *
 */
public class ValidationException extends SystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public ValidationException() {
		super();
	}

	/**
	 * 
	 * @param message
	 * @param cause
	 */
	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 
	 * @param message
	 */
	public ValidationException(String message) {
		super(message);
	}

	
	/**
	 * 
	 * @param cause
	 */
	public ValidationException(Throwable cause) {
		super(cause);
	}

}
