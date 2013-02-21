package com.ibm.query.exception;

/**
  * QueryNotFoundException.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
  */
public class QueryNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public QueryNotFoundException() {
		super();
	}

	public QueryNotFoundException(String message) {
		super(message);
	}

	public QueryNotFoundException(Throwable cause) {
		super(cause);
	}

	public QueryNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
