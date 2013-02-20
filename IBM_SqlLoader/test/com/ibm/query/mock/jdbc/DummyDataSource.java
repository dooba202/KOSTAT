package com.ibm.query.mock.jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class DummyDataSource implements DataSource {
	
	private DummyConnection connection = null;

	public DummyDataSource(DummyConnection connection) {
		super();
		this.connection = connection;
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		
		return null;
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		
		return 0;
	}

	@Override
	public void setLogWriter(PrintWriter arg0) throws SQLException {
		

	}

	@Override
	public void setLoginTimeout(int arg0) throws SQLException {
		

	}

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		
		return null;
	}

	@Override
	public Connection getConnection() throws SQLException {
		
		ConnectionState.increaseCount();
		
		return connection;
	}

	@Override
	public Connection getConnection(String arg0, String arg1)
			throws SQLException {
		
		return null;
	}

}
