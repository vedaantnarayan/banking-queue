package com.turvo.bankingqueue.exception;

public class TokenException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TokenException(String errMSG, Exception e) {
		super(errMSG, e);
	}

	public TokenException(String errMSG) {
		super(errMSG);
	}

}
