package com.ibm.query.execute.jta;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.query.connection.DataSourceManager;
import com.ibm.query.exception.UnknownDataSourceException;
import com.ibm.query.execute.proxy.ConnectionProxy;

public class NoneTransactionalState implements ItransactionalState{
	
	private Log logger = LogFactory.getLog(getClass());
	
	@Override
	public DataSource getDataSource(String name) throws UnknownDataSourceException{
		return DataSourceManager.getInstance().getDataSource(name);
	}
	
	public Connection getConnection(DataSource ds) throws SQLException {
		return new ConnectionProxy(ds.getConnection());
	}
	
	public void commit(Connection conn) throws SQLException {
		conn.commit();
	}
	
	public void commit() throws SQLException {
		logger.debug("NoneTransactionalState.close");
	}

	public void rollback(Connection conn) throws SQLException{
		conn.rollback();
	}

	@Override
	public void close(Connection conn){
		try {
			conn.close();
		} catch (SQLException e) {
			logger.debug("NoneTransactionalState.close : "+ e.getMessage());
		}
	}

	@Override
	public void close() {
		logger.debug("NoneTransactionalState.close");
	}

	@Override
	public void rollback() throws SQLException {
		logger.debug("NoneTransactionalState.rollback");
	}
}
