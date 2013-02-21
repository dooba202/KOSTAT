package com.ibm.green.kostat.exception;

import com.ibm.green.exception.ErrorCode;
import com.ibm.green.exception.GreenException;

/**
 * Thrown when an exception happens during serialization.
 */
public class SerializationException extends GreenException {

	private static final long serialVersionUID = 1L;
	
	public SerializationException() {
		super(ErrorCode.COMMON_UNKNOWN);
	}
	
	public SerializationException(Throwable t) {
		super(ErrorCode.COMMON_UNKNOWN, t);
	}

}
