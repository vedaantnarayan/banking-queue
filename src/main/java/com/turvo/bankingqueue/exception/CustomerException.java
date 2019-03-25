package com.turvo.bankingqueue.exception;

public class CustomerException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomerException(String errMSG) {
		super(errMSG);
	}
}
