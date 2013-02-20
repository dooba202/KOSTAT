package com.ibm.green.kostat.daofactory;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.green.kostat.dao.CredentialDAO;
import com.ibm.green.kostat.dao.SysInfoDAO;
import com.ibm.green.kostat.dao.UserDAO;
import com.ibm.green.kostat.dao.sqlloader.SqlLoaderCrendentialDAO;
import com.ibm.green.kostat.dao.sqlloader.SqlLoaderSysInfoDAO;
import com.ibm.green.kostat.dao.sqlloader.SqlLoaderUserDAO;
import com.ibm.query.execute.Jdbc;

public class SqlLoaderDAOFactory extends DAOFactory {

	Log logger = LogFactory.getLog(this.getClass());
	
	@Override
	public void beginTransaction() {
		Jdbc.getInstance().begin();
	}

	@Override
	public void endTransaction() {
		Jdbc.getInstance().close();
	}

	@Override
	public void commitTransaction() {
		try {
			Jdbc.getInstance().commit();
		} catch (SQLException ex) {
			logger.error("Commit was failed.", ex);
		}
	}

	@Override
	public void rollbackTransaction() {
		try {
			Jdbc.getInstance().rollback();
		} catch (SQLException ex) {
			logger.error("Roollback was failed." , ex);
		}
	}

	@Override
	public SysInfoDAO getSysInfoDAO() {
		
		return new SqlLoaderSysInfoDAO();
	}

	@Override
	public CredentialDAO getCredentialDAO() {

		return new SqlLoaderCrendentialDAO();
	}

	@Override
	public UserDAO getUserDAO() {

		return new SqlLoaderUserDAO();
	}

}
