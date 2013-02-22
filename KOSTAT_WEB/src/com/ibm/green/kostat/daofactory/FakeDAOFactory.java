package com.ibm.green.kostat.daofactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.green.kostat.dao.CredentialDAO;
import com.ibm.green.kostat.dao.IndustryCodeDAO;
import com.ibm.green.kostat.dao.JisuDAO;
import com.ibm.green.kostat.dao.MulyangDAO;
import com.ibm.green.kostat.dao.SysInfoDAO;
import com.ibm.green.kostat.dao.UserDAO;

/**
 * Fake <code>DAOFactory</code> implementation class.
 * <br>
 * It will provides fake implementation for each DAO interface. This class will be used for test case.
 */
public class FakeDAOFactory extends DAOFactory {

	Log logger = LogFactory.getLog(this.getClass());
	
	@Override
	public void beginTransaction() {
		logger.debug("transaction is started");
	}

	@Override
	public void endTransaction() {
		logger.debug("transaction is ended");
	}

	@Override
	public void commitTransaction() {
		logger.debug("transaction is committed");
	}

	@Override
	public void rollbackTransaction() {
		logger.debug("transaction is rollbacked");
	}


	@Override
	public SysInfoDAO getSysInfoDAO() {
		return null;
	}

	@Override
	public CredentialDAO getCredentialDAO() {

		return null;
	}

	@Override
	public UserDAO getUserDAO() {

		return null;
	}

	@Override
	public JisuDAO getJisuDAO() {

		return null;
	}

	@Override
	public IndustryCodeDAO getIndustryCodeDAO() {

		return null;
	}

	@Override
	public MulyangDAO getMulyangDAO() {

		return null;
	}

}
