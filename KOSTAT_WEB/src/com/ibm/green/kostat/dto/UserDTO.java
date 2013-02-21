package com.ibm.green.kostat.dto;

import com.ibm.green.kostat.enums.UserType;

public class UserDTO {

	String userID;
	
	UserType userType;
	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

}
