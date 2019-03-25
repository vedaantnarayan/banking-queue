package com.turvo.bankingqueue.exception;


public class BranchNotExistsException extends RuntimeException{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BranchNotExistsException(String errMSG) {
        super(errMSG);
    }

    public BranchNotExistsException(String errMSG, Exception e) {
        super(errMSG, e);
    }
}
