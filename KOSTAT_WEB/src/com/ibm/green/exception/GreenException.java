package com.ibm.green.exception;

public class GreenException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	ErrorCode code = ErrorCode.COMMON_UNKNOWN;
	
	public GreenException() {
		this(ErrorCode.COMMON_UNKNOWN);
	}
	
	public GreenException(ErrorCode code) {
		super(code.toString());
		this.code = code;
	}
	
	public GreenException(String msg) {
		super(msg);
	}

	public GreenException(ErrorCode code, String subMsg) {
		super(code.toString() + " : " + subMsg);
		this.code = code;
	}
	
	public GreenException(Throwable t) {
		super(t);
	}
	
	public GreenException(ErrorCode code, Throwable t) {
		super(code.toString(), t);
		this.code = code;
	}

	public GreenException(ErrorCode code, String subMsg, Throwable t) {
		super(code.toString() + " : " + subMsg, t);
		this.code = code;
	}
	
	public ErrorCode getErrorCode() {
		return code;
	}

}
