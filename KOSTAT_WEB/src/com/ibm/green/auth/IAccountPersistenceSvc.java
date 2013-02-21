package com.ibm.green.auth;

/**
 * Interface to retrieve digested message and salt information 
 *
 */
public interface IAccountPersistenceSvc {
	
	/**
	 * Retrieve the stored password digest and salt for the given user id.
	 * 
	 * <p>It will return array of String whose size is 2. digest message will be stored at index 0
	 * and salt will be stored at index 1. Returns null is the given user id doesn't exist in the 
	 * persistence service.
	 * 
	 * @param userID
	 * @return array of String that contains digest message and salt.
	 * @throws AccountServiceException if the service is not available
	 */
	String[] getDigestAndSalt(String userID) throws AccountServiceException;
	
}
