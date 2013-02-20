package com.ibm.green.kostat.rest;

import java.util.Locale;

import com.ibm.green.exception.ErrorCode;

/**
 * Error Message class to make JSON that contains error message with redirection URL. 
 */
public class RedirectMessage extends ErrorMessage {
	
	String redirect;

	public RedirectMessage() {
	}

	public RedirectMessage(ErrorCode errorCode, Locale locale) {
		super(errorCode, locale);
	}

	public RedirectMessage(ErrorCode errorCode) {
		super(errorCode);
	}

	public RedirectMessage(Exception ex, Locale locale) {
		super(ex, locale);
	}

	public RedirectMessage(Exception ex) {
		super(ex);
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

}
