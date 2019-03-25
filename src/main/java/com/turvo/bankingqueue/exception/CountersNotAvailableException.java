package com.turvo.bankingqueue.exception;

public class CountersNotAvailableException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CountersNotAvailableException() {
		super("Counters not available");
	}
}
