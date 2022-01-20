package com.gpi.scm.generic.exceptions;


/**
 * @author  Gpi Spa
 * 
 */
public class ApplicationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public ApplicationException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public ApplicationException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ApplicationException(Throwable cause) {
		super(cause);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
	

}
