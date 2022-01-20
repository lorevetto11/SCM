package com.gpi.scm.generic.exceptions;

public class BusinessException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusinessException() {
		super();
	}
	
	public BusinessException(String message, Throwable cause){
		super(message, cause);
	}
	
	public BusinessException(Throwable cause){
		super(cause);
	}
	
	public BusinessException(String message){
		super(message);
	}
	
	@Override
	public String getMessage() {
		return super.getMessage();
	}

}
