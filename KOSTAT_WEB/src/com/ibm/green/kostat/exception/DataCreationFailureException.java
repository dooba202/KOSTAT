package com.ibm.green.kostat.exception;

import com.ibm.green.exception.ErrorCode;
import com.ibm.green.exception.GreenRuntimeException;

/**
 * <tt>DataCreationFailureException</tt> class
 */
public class DataCreationFailureException extends GreenRuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DataCreationFailureException() {
		super(ErrorCode.DBIO_DATA_CREATEFAIL);
	}
	
	public DataCreationFailureException(Throwable t) {
		super(ErrorCode.DBIO_DATA_CREATEFAIL, t);
	}
}
