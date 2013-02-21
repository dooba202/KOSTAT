package com.ibm.green.auth;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;

/**
 * Password authenticator.

 * <p>It authenticate user with user id and password. Also generate hashed password to store.
 * It uses different salt string for each user id and it should be stored with user id
 * 
 * <p>Example of underlying database to store account information.
 * <pre>
 * CREATE TABLE CREDENTIAL (LOGIN VARCHAR(100) PRIMARY KEY, PASSWORD VARCHAR(32) NOT NULL, SALT VARCHAR(32) NOT NULL)
 * </pre>
 * 
 * Original code from "https://www.owasp.org/index.php/Hashing_Java"
 */
public class PasswordAuthenticator {
	
	private static volatile PasswordAuthenticator inst = null;
	
	private static volatile IAccountPersistenceSvc acctPerSvc = null;
	
	private final static int ITERATION_NUMBER = 1000;

	/**
	 * Retrieve singleton instance
	 * 
	 * <p>Before the initialization that is caused by first invocation, account persistence service
	 * must be set via <code>setAccountPersistenceService</code>. If not, <code>IllegalStateException</code>
	 * will be thrown.
	 * 
	 * @return singleton instance
	 * @throws IllegalStateException if account persistence service is not set.
	 */
	public static PasswordAuthenticator getInst() {
		if (inst == null) {
			synchronized(PasswordAuthenticator.class) {
				if (inst == null) {
					if (acctPerSvc == null) {
						throw new IllegalStateException("AccountPersistenceService must be set before initialization.");
					}
					inst = new PasswordAuthenticator();
				}
			}
		}
		
		return inst;		
	}
	
	/**
	 * Set account persistence service.
	 * 
	 * <p> This method must be called before any invocation of <code>getInst()</code> 
	 * 
	 * @param persisteSvc account persistence service
	 * @throws IllegalStateException if called after singleton instance of PasswordAuthenticator is created.
	 */
	public static synchronized void setAccountPersistenceService(IAccountPersistenceSvc persisteSvc) {

		if (inst != null) {
			throw new IllegalStateException("Can't change AccountPersistenceService after initilization");
		} 
		
		PasswordAuthenticator.acctPerSvc = persisteSvc;
		
	}
	
	private PasswordAuthenticator() {
		
	}
	
	/**
	 * Authenticates the user with a given login and password
	 * If password and/or login is null then always returns false.
	 * If the user does not exist in the database returns false.
	 * @param login String The login of the user
	 * @param password String The password of the user
	 * @return boolean Returns true if the user is authenticated, false otherwise
	 * @throws NoSuchAlgorithmException If the algorithm SHA-1 is not supported by the JVM
	 * @throws AccountServiceException If any problem in underlying account system.
	 */
	public boolean authenticate(String login, String password) throws NoSuchAlgorithmException, AccountServiceException {

		try {
			boolean userExist = true;

			if (login == null || password == null) {
				// TIME RESISTANT ATTACK
				// Computation time is equal to the time needed by a legitimate user
				userExist = false;
				login="";
				password="";
			}
			
			String[] digestAndSalt = acctPerSvc.getDigestAndSalt(login);
			String digest = null;
			String salt = null;
			
			// user doesn't exist
			if (digestAndSalt == null) {

				// TIME RESISTANT ATTACK Even if the user does not exist the
				// computation time is equal to the time needed for a legitimate user
				digest = "000000000000000000000000000=";
				salt = "00000000000=";
				userExist = false;
				
			} else if (digestAndSalt.length == 2) {
				
				digest = digestAndSalt[0];
				salt = digestAndSalt[1];
			}
			
			if ((digest == null) || (salt == null)) {
				throw new AccountServiceException("Account persistence service problem. Digest message or salt is null for user - " + login);
			}
 
           byte[] bDigest = Base64.decodeBase64(digest.getBytes("UTF-8"));
           byte[] bSalt = Base64.decodeBase64(salt.getBytes("UTF-8"));
 
           // Compute the new DIGEST
           byte[] proposedDigest = getHash(ITERATION_NUMBER, password, bSalt);
 
           return Arrays.equals(proposedDigest, bDigest) && userExist;
	   
       } catch (UnsupportedEncodingException ex){
           throw new AccountServiceException(ex);
       }
   }
	
	/**
	 * From a password, a number of iterations and a salt,
	 * returns the corresponding digest
	 * @param iterationNb int The number of iterations of the algorithm
	 * @param password String The password to encrypt
	 * @param salt byte[] The salt
	 * @return byte[] The digested password
	 * @throws NoSuchAlgorithmException If the algorithm doesn't exist
	 * @throws UnsupportedEncodingException If the UTF-8 decoding is not supported. 
	 */
	public byte[] getHash(int iterationNb, String password, byte[] salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		digest.reset();
		digest.update(salt);
		byte[] input = digest.digest(password.getBytes("UTF-8"));
		
		for (int i = 0; i < iterationNb; i++) {
			digest.reset();
			input = digest.digest(input);
		}
		
		return input;
	}
	
	/**
	 * Generate digested password and salt for the given user id and password
	 * 
	 * <p>It returns an array of String contains digested password at 0 and salt at 1.
	 * It will return null if id or password is not okay (null and length(login)>100)
	 *
	 * @param login String The login of the user
	 * @param password String The password of the user
	 * @return an Array of String contains digested password at 0 and salt at 1
	 * @throws NoSuchAlgorithmException If the algorithm SHA-1 or the SecureRandom is not supported by the JVM
	 * @throws AccountServiceException If the underlying account service is unavailable
	 */
	public String[] generateDigestAndSalt(String login, String password) throws NoSuchAlgorithmException, AccountServiceException {

		try {
			if (login != null && password != null && login.length() <= 100) {
				// Use a secure Random
				SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
				
				// 64 bits long
				byte[] bSalt = new byte[8]; 
				random.nextBytes(bSalt);
				
				byte[] bDigest = getHash(ITERATION_NUMBER, password, bSalt);
				
				String sDigest = new String(Base64.encodeBase64(bDigest), "UTF-8");
				String sSalt = new String(Base64.encodeBase64(bSalt), "UTF-8");
				

				return new String[] {sDigest, sSalt};
				
			} else {
				return null;
			}
			
		} catch (UnsupportedEncodingException ex) {
			throw new AccountServiceException(ex);
		}
	}

	
}
