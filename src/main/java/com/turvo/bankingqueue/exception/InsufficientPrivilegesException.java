package com.turvo.bankingqueue.exception;

public class InsufficientPrivilegesException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InsufficientPrivilegesException(String errMsg) {
		super(errMsg);
	}
}
