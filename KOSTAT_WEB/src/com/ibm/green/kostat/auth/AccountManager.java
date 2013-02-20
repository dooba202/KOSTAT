package com.ibm.green.kostat.auth;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.green.auth.AccountServiceException;
import com.ibm.green.auth.IAccountPersistenceSvc;
import com.ibm.green.auth.PasswordAuthenticator;
import com.ibm.green.exception.ErrorCode;
import com.ibm.green.exception.GreenRuntimeException;
import com.ibm.green.kostat.dao.CredentialDAO;
import com.ibm.green.kostat.dao.UserDAO;
import com.ibm.green.kostat.daofactory.DAOFactory;
import com.ibm.green.kostat.dto.CredentialDTO;
import com.ibm.green.kostat.dto.UserDTO;
import com.ibm.green.kostat.enums.UserType;

public class AccountManager implements IAccountPersistenceSvc {
	
	private static volatile AccountManager inst = null;
	
	Log logger = LogFactory.getLog(this.getClass());
	
	private DAOFactory daoFactory;
	private CredentialDAO credentialDAO;
	private UserDAO userDAO;

	
	public static AccountManager getInst() {
		if (inst == null) {
			synchronized(AccountManager.class) {
				if (inst == null) {
					inst = new AccountManager();
				}
			}
		}
		
		return inst;		
	}
	
	private AccountManager() {
		// set myself into PasswordAuthenticator as AccountPersistenceService
		PasswordAuthenticator.setAccountPersistenceService(this);
		
		// retrieve credential dao
		daoFactory  = DAOFactory.getDAOFactory(DAOFactory.SQLLOADER_DAO);
		credentialDAO = daoFactory.getCredentialDAO();
		userDAO = daoFactory.getUserDAO();
		
	}


	@Override
	public String[] getDigestAndSalt(String userID) throws AccountServiceException {

		try {
			logger.debug("AccountManager#getDigestAndSalt(String userID) was called with " + userID);			
			daoFactory.beginTransaction();			
			
			if (userID != null) {
				userID = userID.toUpperCase();
			}			
			
			CredentialDTO creDTO = credentialDAO.find(userID);
			daoFactory.commitTransaction();				
			
			// userid doesn't exist
			if (creDTO == null) {
				return null;
			}

			return new String[] {creDTO.getPassword(), creDTO.getSalt()};
		} catch (Exception ex) {
			logger.error("Exception occurs during looking password hash for : " + userID, ex);
			
			if (daoFactory != null) daoFactory.rollbackTransaction();			
			throw new AccountServiceException(ex);
		} finally {
			if (daoFactory != null) daoFactory.endTransaction();
		}	
	}
	
	/**
	 * Create new user account.
	 * 
	 * @param userID
	 * @param password
	 * @param userType 
	 * @throws AccountServiceException
	 */
	public void createAccount(String userID, String password, UserType userType) throws AccountServiceException {
		try {
			logger.debug("AccountManager#createAccount(String userID, String password, UserType userType, String orgID, String orgNm) was called with " + userID);			
			
			if (userID != null) {
				userID = userID.toUpperCase();
			}
			
			String[] digestAndSalt = PasswordAuthenticator.getInst().generateDigestAndSalt(userID, password);
			
			if ((digestAndSalt == null) || (digestAndSalt.length != 2)) {
				logger.debug("generateDigestAndSalt returns null with id,pwd :" + userID + ", " + password);
				throw new AccountServiceException(ErrorCode.ACCT_INVALID_ID_PWD);
			}

			daoFactory.beginTransaction();			
			
			// Create Credential info
			CredentialDTO creDTO = new CredentialDTO();
			creDTO.setUserID(userID);
			creDTO.setPassword(digestAndSalt[0]);
			creDTO.setSalt(digestAndSalt[1]);			
			credentialDAO.save(creDTO); 
			
			// Create User info
			UserDTO userDTO = new UserDTO();
			userDTO.setUserID(userID);
			userDTO.setUserType(userType);
			userDAO.save(userDTO);
			
			daoFactory.commitTransaction();				

			return;
		} catch (AccountServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occurs during creating account for : " + userID, ex);
			
			if (daoFactory != null) daoFactory.rollbackTransaction();			
			throw new AccountServiceException(ex);
		} finally {
			if (daoFactory != null) daoFactory.endTransaction();
		}
	}
	
	/**
	 * Authenticate userID and password.
	 * 
	 * <p> If succeeds, return <code>UserDTO</code> object that is corresponding to the given user id.
	 * 
	 * @param userID
	 * @param password
	 * @return <code>UserDTO</code> object
	 * @throws AccountServiceException
	 */
	public UserDTO authenticateUser(String userID, String password)  throws AccountServiceException {
		try {
			logger.debug("AccountManager#authenticateUser(String userID, String password) was called with " + userID);			

			if (userID != null) {
				userID = userID.toUpperCase();
			}			
			
			// authenticate
			boolean bSuccess = PasswordAuthenticator.getInst().authenticate(userID, password);
			
			if (!bSuccess) {
				logger.debug("Failed to authenticate user : " + userID);
				throw new AccountServiceException(ErrorCode.ACCT_INVALID_ID_PWD);
			}
			
			daoFactory.beginTransaction();			
			
			// retrieve user info
			UserDTO user = userDAO.find(userID);
			
			if (user == null) {
				logger.error("Failed to retrieve user info even though user id is authenticate !! : " + userID);
				throw new AccountServiceException(ErrorCode.ACCT_INVALID_ID_PWD);
			}
			
			daoFactory.commitTransaction();				
			return user;
			
		} catch (AccountServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occurs during authenticateUser for : " + userID, ex);
			
			if (daoFactory != null) daoFactory.rollbackTransaction();			
			throw new AccountServiceException(ex);
		} finally {
			if (daoFactory != null) daoFactory.endTransaction();
		}
	}


}
