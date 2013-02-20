package com.ibm.green.auth;

import com.ibm.green.exception.ErrorCode;
import com.ibm.green.exception.GreenException;

/**
 * General Checked Exception for Account Service
 */
public class AccountServiceException extends GreenException {

	private static final long serialVersionUID = 1L;
	
	public AccountServiceException(String msg) {
		super(ErrorCode.ACCT_SVC_UNKOWN, msg);
	}
	
	public AccountServiceException(Throwable t) {
		super(ErrorCode.ACCT_SVC_UNKOWN, t);
	}	
	
	public AccountServiceException(ErrorCode code) {
		super(code);
	}
	

}
