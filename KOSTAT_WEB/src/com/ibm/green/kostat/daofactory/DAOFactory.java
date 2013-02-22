package com.ibm.green.kostat.daofactory;

import com.ibm.green.exception.ErrorCode;
import com.ibm.green.exception.GreenRuntimeException;
import com.ibm.green.kostat.dao.CredentialDAO;
import com.ibm.green.kostat.dao.IndustryCodeDAO;
import com.ibm.green.kostat.dao.JisuDAO;
import com.ibm.green.kostat.dao.MulyangDAO;
import com.ibm.green.kostat.dao.SysInfoDAO;
import com.ibm.green.kostat.dao.UserDAO;

/**
 * abstract DAO factory class.<br>
 */
public abstract class DAOFactory {
	
	public static final int FAKE_DAO = 0;
	public static final int SQLLOADER_DAO = 1;

	/**
	 * Return the <code>DAOFactory</code> object which is for the given <code>type</code>.
	 * 
	 * @param type - the type of DAO factory 
	 * @return <code>DAOFactory</code> object
	 */
	public static DAOFactory getDAOFactory(int type) {
		
		switch (type) {
		case FAKE_DAO:
			return new FakeDAOFactory();
		case SQLLOADER_DAO:
			return new SqlLoaderDAOFactory();
		default:
			throw new GreenRuntimeException(ErrorCode.COMMON_UNKNOWN, "Unknown DAO factory type was given - " + type);
		}
	}
	
	/**
	 * Start new transaction. It's only valid in the current thread
	 */
	public abstract void beginTransaction();
	
	/**
	 * End the transaction.
	 */
	public abstract void endTransaction();
	
	/**
	 * Commit the transaction
	 */
	public abstract void commitTransaction();
	
	/**
	 * Rollback the transaction
	 */
	public abstract void rollbackTransaction();
	
	/**
	 * Return <code>SysInfoDAO</code> object.
	 * 
	 * @return DAO object which implements <code>SysInfoDAO</code> interface
	 */
	public abstract SysInfoDAO getSysInfoDAO();
	
	/**
	 * Return <code>CredentialDAO</code> object
	 *
	 * @return DAO object which implements <code>CredentialDAO</code> interface
	 */
	public abstract CredentialDAO getCredentialDAO();
	
	/**
	 * Return <code>UserDAO</code> object
	 *
	 * @return DAO object which implements <code>UserDAO</code> interface
	 */	
	public abstract UserDAO getUserDAO();
	
	/**
	 * Return <code>JisuDAO</code> object
	 *
	 * @return DAO object which implements <code>JisuDAO</code> interface
	 */		
	public abstract JisuDAO getJisuDAO();
	
	/**
	 * Return <code>IndustryCodeDAO</code> object
	 *
	 * @return DAO object which implements <code>IndustryCodeDAO</code> interface
	 */		
	public abstract IndustryCodeDAO getIndustryCodeDAO();
	
	/**
	 * Return <code>MulyangDAO</code> object
	 *
	 * @return DAO object which implements <code>MulyangDAO</code> interface
	 */		
	public abstract MulyangDAO getMulyangDAO();

}
