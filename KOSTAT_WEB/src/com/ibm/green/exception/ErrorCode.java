package com.ibm.green.exception;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

/*
 * Enumeration class for error codes that are being used in Green common system. 
 * It also provides internalized error message. 
 */
public enum ErrorCode {

	// Error code is composed of 4 digits component name and 3 digit sequence number
	
	COMMON_OK					  ("COMM000"),
	COMMON_UNKNOWN 				  ("COMM001"),
	COMMON_INVALID_REQ			  ("COMM002"),
	COMMON_NOT_SUPPORTED_DATATYPE ("COMM003"),
	COMMON_SCHEDULER_FAILURE	  ("COMM004"),
	COMMON_NOT_IMPLEMENTED		  ("COMM005"),
	COMMON_NOT_ALLOWED			  ("COMM006"),
	COMMON_AUTH_REQUIRED		  ("COMM007"),
	
	
	DBIO_UNKNOWN				  ("DBIO001"),
	DBIO_DATA_LOADFAIL			  ("DBIO002"),
	DBIO_DATA_NOTLOAD			  ("DBIO003"),	
	DBIO_DATA_CREATEFAIL		  ("DBIO004"),
	DBIO_DATA_NOTFOUND			  ("DBIO005"),
	DBIO_INVALID_REQ			  ("DBIO006"),
	DBIO_DATA_UPDATEFAIL		  ("DBIO007"),
	
	ACCT_SVC_UNKOWN				  ("ACCT001"),
	ACCT_INVALID_ID_PWD			  ("ACCT002"),
	
	
	DUMMY_PLACEHOLDER			  ("_______");
	
	private final String code;
	private String message;
	
	static final String BUNDLE_NAME = "com/ibm/green/exception/green_msg";
	
	// PropertyResourceBundle for the default Locale
	static ResourceBundle errMsgBundle;
	
	static HashMap<Locale, ResourceBundle> errMsgBundlesMap = new HashMap<Locale, ResourceBundle>();

	static {
		errMsgBundle = ResourceBundle.getBundle(BUNDLE_NAME);		
	}
	
	static synchronized ResourceBundle loadResourceBundle(Locale locale) {
		ResourceBundle resBundle = errMsgBundlesMap.get(locale);
		
		if (resBundle == null) {
			resBundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
			errMsgBundlesMap.put(locale, resBundle);
		}
		
		return resBundle;		
	}
	
	private ErrorCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	
	/*
	 * Retrieve the error message for the default Locale.
	 */
	public String getDescription() {
		if (this.message == null) { // synchronization is not important here.
			this.message = errMsgBundle.getString(code);
		}
		return this.message;
	}
	
	/*
	 * Retrieve the error message for the given Locale
	 */
	public String getDescription(Locale locale) {
		if (errMsgBundle.getLocale() == locale) {
			return getDescription();
		}
		
		ResourceBundle resBundle = errMsgBundlesMap.get(locale);
		if (resBundle == null) {
			resBundle = loadResourceBundle(locale);
		}
		return resBundle.getString(code);
	}

	@Override
	public String toString() {
		return code + ": " + getDescription();
	}
	
	/**
	 * Return the enum const of <code>ErrorCode</code> that has the given <code>code</code>.
	 * 
	 * @param code error code string
	 * @return
	 * @throws IllegalArgumentException if there is no matched constant with the given error code.
	 */
	public static ErrorCode fromCode(String code) throws IllegalArgumentException {
		
		for (ErrorCode errCode : ErrorCode.values()) {
			if (errCode.getCode().equalsIgnoreCase(code)) {
				return errCode;
			}
		}
		
		throw new IllegalArgumentException("Unknown error code : " + code);
	}
	
}
