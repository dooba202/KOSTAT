package com.ibm.green.kostat.dto;

/**
 * Data Transfer Object for credential info
 *
 */
public class CredentialDTO {
	
	String userID;
	
	// digested message of salt + password
	String password;
	
	String salt;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

}
