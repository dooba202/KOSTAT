package com.ibm.query.mock.jdbc;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;

import com.ibm.query.execute.proxy.CallableStatementProxy;
import com.ibm.query.execute.proxy.PreparedStatementProxy;

public class DummyConnection implements Connection {
	
	private DummyPreparedStatement ps = new DummyPreparedStatement();
	
	private DummyCallableStatement cs = new DummyCallableStatement();

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		return null;
	}

	@Override
	public void clearWarnings() throws SQLException {

	}

	@Override
	public void close() throws SQLException {
		ConnectionState.decreaseCount();
	}

	@Override
	public void commit() throws SQLException {

	}

	@Override
	public Array createArrayOf(String arg0, Object[] arg1) throws SQLException {
		return null;
	}

	@Override
	public Blob createBlob() throws SQLException {
		return null;
	}

	@Override
	public Clob createClob() throws SQLException {
		return null;
	}

	@Override
	public NClob createNClob() throws SQLException {
		return null;
	}

	@Override
	public SQLXML createSQLXML() throws SQLException {
		return null;
	}

	@Override
	public Statement createStatement() throws SQLException {
		return null;
	}

	@Override
	public Statement createStatement(int arg0, int arg1) throws SQLException {
		return null;
	}

	@Override
	public Statement createStatement(int arg0, int arg1, int arg2) throws SQLException {
		return null;
	}

	@Override
	public Struct createStruct(String arg0, Object[] arg1) throws SQLException {
		return null;
	}

	@Override
	public boolean getAutoCommit() throws SQLException {
		return false;
	}

	@Override
	public String getCatalog() throws SQLException {
		return null;
	}

	@Override
	public Properties getClientInfo() throws SQLException {
		return null;
	}

	@Override
	public String getClientInfo(String arg0) throws SQLException {
		return null;
	}

	@Override
	public int getHoldability() throws SQLException {
		return 0;
	}

	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		return null;
	}

	@Override
	public int getTransactionIsolation() throws SQLException {
		return 0;
	}

	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		return null;
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		return null;
	}

	@Override
	public boolean isClosed() throws SQLException {
		return false;
	}

	@Override
	public boolean isReadOnly() throws SQLException {
		return false;
	}

	@Override
	public boolean isValid(int arg0) throws SQLException {
		return false;
	}

	@Override
	public String nativeSQL(String arg0) throws SQLException {
		return null;
	}

	@Override
	public CallableStatement prepareCall(String sql) throws SQLException {
		return new CallableStatementProxy(sql, cs);
	}

	@Override
	public CallableStatement prepareCall(String sql, int arg1, int arg2) throws SQLException {
		return new CallableStatementProxy(sql, cs);
	}

	@Override
	public CallableStatement prepareCall(String sql, int arg1, int arg2, int arg3) throws SQLException {
		return new CallableStatementProxy(sql, cs);
	}

	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return new PreparedStatementProxy(sql, ps);
	}

	@Override
	public PreparedStatement prepareStatement(String arg0, int arg1) throws SQLException {
		return ps;
	}

	@Override
	public PreparedStatement prepareStatement(String arg0, int[] arg1) throws SQLException {
		return ps;
	}

	@Override
	public PreparedStatement prepareStatement(String arg0, String[] arg1) throws SQLException {
		return ps;
	}

	@Override
	public PreparedStatement prepareStatement(String arg0, int arg1, int arg2) throws SQLException {
		return ps;
	}

	@Override
	public PreparedStatement prepareStatement(String arg0, int arg1, int arg2, int arg3) throws SQLException {
		return ps;
	}

	@Override
	public void releaseSavepoint(Savepoint arg0) throws SQLException {

	}

	@Override
	public void rollback() throws SQLException {

	}

	@Override
	public void rollback(Savepoint arg0) throws SQLException {

	}

	@Override
	public void setAutoCommit(boolean arg0) throws SQLException {

	}

	@Override
	public void setCatalog(String arg0) throws SQLException {

	}

	@Override
	public void setClientInfo(Properties arg0) throws SQLClientInfoException {

	}

	@Override
	public void setClientInfo(String arg0, String arg1) throws SQLClientInfoException {

	}

	@Override
	public void setHoldability(int arg0) throws SQLException {

	}

	@Override
	public void setReadOnly(boolean arg0) throws SQLException {

	}

	@Override
	public Savepoint setSavepoint() throws SQLException {
		return null;
	}

	@Override
	public Savepoint setSavepoint(String arg0) throws SQLException {
		return null;
	}

	@Override
	public void setTransactionIsolation(int arg0) throws SQLException {

	}

	@Override
	public void setTypeMap(Map<String, Class<?>> arg0) throws SQLException {

	}

	public void setResultData(ResultSet rs){
		ps.setResultData(rs);
	}
}
