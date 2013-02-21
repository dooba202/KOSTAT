package com.ibm.green.exception;

public class GreenRuntimeException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	ErrorCode code = ErrorCode.COMMON_UNKNOWN;;
	
	public GreenRuntimeException() {
		this(ErrorCode.COMMON_UNKNOWN);
	}
	
	public GreenRuntimeException(ErrorCode code) {
		super(code.toString());
		this.code = code;
	}
	
	public GreenRuntimeException(String msg) {
		super(msg);
	}
	
	public GreenRuntimeException(ErrorCode code, String subMsg) {
		super(code.toString() + " : " + subMsg);
		this.code = code;
	}
	
	public GreenRuntimeException(Throwable t) {
		super(t);
	}
	
	public GreenRuntimeException(ErrorCode code, Throwable t) {
		super(code.toString(), t);
		this.code = code;
	}
	
	public ErrorCode getErrorCode() {
		return code;
	}

}
