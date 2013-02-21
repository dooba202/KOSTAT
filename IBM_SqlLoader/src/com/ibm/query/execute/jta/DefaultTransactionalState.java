package com.ibm.query.execute.jta;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.query.connection.DataSourceManager;
import com.ibm.query.exception.UnknownDataSourceException;
import com.ibm.query.execute.proxy.ConnectionProxy;

public class DefaultTransactionalState implements ItransactionalState{
	private Log logger = LogFactory.getLog(getClass());
	protected ThreadLocal<Connection> localConnection = new ThreadLocal<Connection>();
	
	@Override
	public DataSource getDataSource(String name) throws UnknownDataSourceException{
		return DataSourceManager.getInstance().getDataSource(name);
	}
	
	public Connection getConnection(DataSource ds) throws SQLException {
		Connection conn = localConnection.get();
		if(conn == null){
			conn = new ConnectionProxy(ds.getConnection()); 
			conn.setAutoCommit(false);
			localConnection.set(conn);
		}
		
		if(conn.isClosed()){
			conn = new ConnectionProxy(ds.getConnection()); 
			conn.setAutoCommit(false);
			localConnection.set(conn);
		}
		return conn;
	}

	@Override
	public void close(Connection conn){
		logger.debug("DefaultTransactionalState.close");
	}
	
	public void close(){
		try {
			Connection conn = localConnection.get();
			if(conn != null){
				conn.close();	
			}
		} catch (SQLException e) {
			logger.debug("DefaultTransactionalState.close : "+ e.getMessage());
		}
		localConnection.remove();
	}

	@Override
	public void commit(Connection conn) throws SQLException{
		logger.debug("DefaultTransactionalState.commit");
	}

	public void commit() throws SQLException {
		try {
			Connection conn = localConnection.get();
			if(conn != null){
				conn.commit();	
			}
		} catch (SQLException e) {
			logger.debug("DefaultTransactionalState.commit : "+ e.getMessage());
		}
	}
	
	@Override
	public void rollback(Connection conn) throws SQLException {
		logger.debug("DefaultTransactionalState.rollback");
	}

	@Override
	public void rollback() throws SQLException {
		Connection conn = localConnection.get();
		if(conn != null){
			conn.rollback();	
		}
				
	}

	
}
