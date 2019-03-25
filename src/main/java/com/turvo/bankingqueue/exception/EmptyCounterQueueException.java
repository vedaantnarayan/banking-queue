package com.turvo.bankingqueue.exception;

public class EmptyCounterQueueException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmptyCounterQueueException() {
		super("CounterDetails Queue is empty");
	}

	public EmptyCounterQueueException(String msg) {
		super("CounterDetails Queue is empty:" + msg);
	}

}
