package com.ibm.green.kostat.exception;

import com.ibm.green.exception.ErrorCode;
import com.ibm.green.exception.GreenRuntimeException;

/**
 * <tt>DataNotFoundException</tt> class
 */
public class DataNotFoundException extends GreenRuntimeException {
	private static final long serialVersionUID = 1L;
	
	public DataNotFoundException() {
		super(ErrorCode.DBIO_DATA_NOTFOUND);
	}
	
	public DataNotFoundException(Throwable t) {
		super(ErrorCode.DBIO_DATA_NOTFOUND, t);
	}
}
