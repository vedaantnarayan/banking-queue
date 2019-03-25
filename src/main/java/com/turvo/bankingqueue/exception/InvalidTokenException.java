package com.turvo.bankingqueue.exception;

public class InvalidTokenException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidTokenException(String errMSg) {
		super(errMSg);
	}

	public InvalidTokenException(String errMsg, Exception e) {
		super(errMsg, e);
	}
}
