package com.ibm.query.execute.jta;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.ibm.query.exception.UnknownDataSourceException;

public interface ItransactionalState {

	public DataSource getDataSource(String name) throws UnknownDataSourceException;
	
	public Connection getConnection(DataSource ds) throws SQLException;

	public void commit() throws SQLException;

	public void commit(Connection conn) throws SQLException;

	public void rollback() throws SQLException;
	
	public void rollback(Connection conn) throws SQLException;
	
	public void close(Connection conn);
	
	public void close();

		
}
