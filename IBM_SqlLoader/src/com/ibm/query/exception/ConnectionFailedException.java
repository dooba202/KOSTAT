package com.ibm.query.exception;

public class ConnectionFailedException extends Exception {

	private static final long serialVersionUID = 1L;

	public ConnectionFailedException() {
		super();
	}

	public ConnectionFailedException(String message) {
		super(message);
	}

	public ConnectionFailedException(Throwable cause) {
		super(cause);
	}

	public ConnectionFailedException(String message, Throwable cause) {
		super(message, cause);
	}
}
