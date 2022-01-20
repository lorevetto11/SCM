package com.gpi.scm.generic.exceptions;



/**
 * @author  Gpi Spa
 *
 */
public class SystemException extends RuntimeException  {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	public SystemException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public SystemException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public SystemException(Throwable cause) {
		super(cause);
	}
}
