package com.ibm.query.execute.jta;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.query.connection.DataSourceManager;
import com.ibm.query.exception.UnknownDataSourceException;
import com.ibm.query.execute.proxy.ConnectionProxy;

public class XATransactionalState implements ItransactionalState{
	private Log logger = LogFactory.getLog(getClass());
	
	@Override
	public DataSource getDataSource(String name) throws UnknownDataSourceException{
		return DataSourceManager.getInstance().getDataSource(name);
	}
	
	public Connection getConnection(DataSource ds) throws SQLException {
		return new ConnectionProxy(ds.getConnection());
	}
	
	public void commit(Connection conn) throws SQLException {
		logger.debug("XATransactionalState.commit");
	}
	
	public void commit() throws SQLException {
		logger.debug("XATransactionalState.commit");
	}

	public void rollback(Connection conn) throws SQLException{
		logger.debug("XATransactionalState.rollback");
	}

	@Override
	public void close(Connection conn){
		logger.debug("XATransactionalState.close");
	}

	@Override
	public void close() {
		logger.debug("XATransactionalState.close");
	}

	@Override
	public void rollback() throws SQLException {
		logger.debug("XATransactionalState.rollback");
	}
}
