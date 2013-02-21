package com.ibm.query.exception;

public class NotSupportedQueryException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotSupportedQueryException() {
		super();
	}

	public NotSupportedQueryException(String message) {
		super(message);
	}

	public NotSupportedQueryException(Throwable cause) {
		super(cause);
	}

	public NotSupportedQueryException(String message, Throwable cause) {
		super(message, cause);
	}
}
