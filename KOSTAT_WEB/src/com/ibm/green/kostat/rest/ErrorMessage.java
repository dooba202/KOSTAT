package com.ibm.green.kostat.rest;

import java.util.Locale;

import com.ibm.green.exception.ErrorCode;
import com.ibm.green.exception.GreenException;
import com.ibm.green.exception.GreenRuntimeException;

/**
 * Entity class to generate error message
 * 
 *  API user might use <code>userMessage</code> to display message to users.
 *  So it should be user-friendly and translated message.
 */
public class ErrorMessage {

	String code;
	
	String userMessage;
	
	String developerMessage;
	
	
	public ErrorMessage() {
		
	}
	
	/**
	 * Constructor with <code>ErrorCode</code>
	 * <code>code</code>, <code>userMessage</code> and <code>developerMessage</code> will be
	 * set with errorCode. 
	 * 
	 * @param errorCode
	 * @param locale java.util.Locale locale that will be used to generate <code>userMessage</code>
	 */
	public ErrorMessage(ErrorCode errorCode, Locale locale) {
		
		this.code = errorCode.getCode();
		this.userMessage = errorCode.getDescription(locale);
		this.developerMessage = "[Error] " + errorCode.getDescription(locale);

	}
	
	/**
	 * Constructor with <code>ErrorCode</code>
	 * <code>code</code>, <code>userMessage</code> and <code>developerMessage</code> will be
	 * set with errorCode. 
	 * <code>userMessage</code> will be generated with system default locale.
	 * 
	 * @param errorCode
	 */
	public ErrorMessage(ErrorCode errorCode) {
		
		this(errorCode, Locale.getDefault());
	}
	
	/**
	 * Constructor with <code>Exception</code>
	 * In case of <code>GreenException</code> or <code>GreenRuntimeException</code>, <code>ErrorCode</code> in exception
	 * will be used to generate messages.
	 * If not, <code>ErrorCode.COMMON_UNKNOWN</code> will be used instead.
	 * <code>developerMessage</code> will also have exception class name as well as error description.
	 * 
	 * @param ex Exception
	 * @param locale java.util.Locale locale that will be used to generate <code>userMessage</code>
	 */
	public ErrorMessage(Exception ex, Locale locale) {
		
		ErrorCode errorCode = ErrorCode.COMMON_UNKNOWN;
		
		if (ex instanceof GreenException) {
			errorCode = ((GreenException) ex).getErrorCode();
		} else if (ex instanceof GreenRuntimeException) {
			errorCode = ((GreenRuntimeException) ex).getErrorCode();
		}
		
		this.code = errorCode.getCode();
		this.userMessage = errorCode.getDescription(locale);
		this.developerMessage = "[" + ex.getClass().toString() + "] " + errorCode.getDescription(locale);
	
 
	}
	
	/**
	 * Constructor with <code>Exception</code>
	 * In case of <code>GreenException</code> or <code>GreenRuntimeException</code>, <code>ErrorCode</code> in exception
	 * will be used to generate messages.
	 * If not, <code>ErrorCode.COMMON_UNKNOWN</code> will be used instead.
	 * <code>developerMessage</code> will also have exception class name as well as error description.
	 * 
	 * @param ex Exception
	 * @param locale java.util.Locale locale that will be used to generate <code>userMessage</code>
	 */
	public ErrorMessage(Exception ex) {
		this(ex, Locale.getDefault());
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}

	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}

	
}
