package com.ibm.query.mock.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

public class DummyPreparedStatement implements PreparedStatement {

	private ResultSet rs = null;
	
	@Override
	public void addBatch(String sql) throws SQLException {
		

	}

	@Override
	public void cancel() throws SQLException {
		

	}

	@Override
	public void clearBatch() throws SQLException {
		

	}

	@Override
	public void clearWarnings() throws SQLException {
		

	}

	@Override
	public void close() throws SQLException {
		

	}

	@Override
	public boolean execute(String sql) throws SQLException {
		
		return false;
	}

	@Override
	public boolean execute(String sql, int autoGeneratedKeys)
			throws SQLException {
		
		return false;
	}

	@Override
	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		
		return false;
	}

	@Override
	public boolean execute(String sql, String[] columnNames)
			throws SQLException {
		
		return false;
	}

	@Override
	public int[] executeBatch() throws SQLException {
		
		return null;
	}

	@Override
	public ResultSet executeQuery(String sql) throws SQLException {
		
		return null;
	}

	@Override
	public int executeUpdate(String sql) throws SQLException {
		
		return 0;
	}

	@Override
	public int executeUpdate(String sql, int autoGeneratedKeys)
			throws SQLException {
		
		return 0;
	}

	@Override
	public int executeUpdate(String sql, int[] columnIndexes)
			throws SQLException {
		
		return 0;
	}

	@Override
	public int executeUpdate(String sql, String[] columnNames)
			throws SQLException {
		
		return 0;
	}

	@Override
	public Connection getConnection() throws SQLException {
		
		return null;
	}

	@Override
	public int getFetchDirection() throws SQLException {
		
		return 0;
	}

	@Override
	public int getFetchSize() throws SQLException {
		
		return 0;
	}

	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		
		return null;
	}

	@Override
	public int getMaxFieldSize() throws SQLException {
		
		return 0;
	}

	@Override
	public int getMaxRows() throws SQLException {
		
		return 0;
	}

	@Override
	public boolean getMoreResults() throws SQLException {
		
		return false;
	}

	@Override
	public boolean getMoreResults(int current) throws SQLException {
		
		return false;
	}

	@Override
	public int getQueryTimeout() throws SQLException {
		
		return 0;
	}

	@Override
	public ResultSet getResultSet() throws SQLException {
		
		return null;
	}

	@Override
	public int getResultSetConcurrency() throws SQLException {
		
		return 0;
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		
		return 0;
	}

	@Override
	public int getResultSetType() throws SQLException {
		
		return 0;
	}

	@Override
	public int getUpdateCount() throws SQLException {
		
		return 0;
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
	public boolean isPoolable() throws SQLException {
		
		return false;
	}

	@Override
	public void setCursorName(String name) throws SQLException {
		

	}

	@Override
	public void setEscapeProcessing(boolean enable) throws SQLException {
		

	}

	@Override
	public void setFetchDirection(int direction) throws SQLException {
		

	}

	@Override
	public void setFetchSize(int rows) throws SQLException {
		

	}

	@Override
	public void setMaxFieldSize(int max) throws SQLException {
		

	}

	@Override
	public void setMaxRows(int max) throws SQLException {
		

	}

	@Override
	public void setPoolable(boolean poolable) throws SQLException {
		

	}

	@Override
	public void setQueryTimeout(int seconds) throws SQLException {
		

	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		
		return null;
	}

	@Override
	public void addBatch() throws SQLException {
		

	}

	@Override
	public void clearParameters() throws SQLException {
		

	}

	@Override
	public boolean execute() throws SQLException {
		
		return false;
	}

	@Override
	public ResultSet executeQuery() throws SQLException {
		if(rs instanceof DummyResultSet){
			((DummyResultSet)rs).init();
		}
		return rs;
	}

	@Override
	public int executeUpdate() throws SQLException {
		
		return 0;
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		
		return null;
	}

	@Override
	public ParameterMetaData getParameterMetaData() throws SQLException {
		
		return null;
	}

	@Override
	public void setArray(int arg0, Array arg1) throws SQLException {
		

	}

	@Override
	public void setAsciiStream(int arg0, InputStream arg1) throws SQLException {
		

	}

	@Override
	public void setAsciiStream(int arg0, InputStream arg1, int arg2)
			throws SQLException {
		

	}

	@Override
	public void setAsciiStream(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		

	}

	@Override
	public void setBigDecimal(int arg0, BigDecimal arg1) throws SQLException {
		

	}

	@Override
	public void setBinaryStream(int arg0, InputStream arg1) throws SQLException {
		

	}

	@Override
	public void setBinaryStream(int arg0, InputStream arg1, int arg2)
			throws SQLException {
		

	}

	@Override
	public void setBinaryStream(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		

	}

	@Override
	public void setBlob(int arg0, Blob arg1) throws SQLException {
		

	}

	@Override
	public void setBlob(int arg0, InputStream arg1) throws SQLException {
		

	}

	@Override
	public void setBlob(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		

	}

	@Override
	public void setBoolean(int arg0, boolean arg1) throws SQLException {
		

	}

	@Override
	public void setByte(int arg0, byte arg1) throws SQLException {
		

	}

	@Override
	public void setBytes(int arg0, byte[] arg1) throws SQLException {
		

	}

	@Override
	public void setCharacterStream(int arg0, Reader arg1) throws SQLException {
		

	}

	@Override
	public void setCharacterStream(int arg0, Reader arg1, int arg2)
			throws SQLException {
		

	}

	@Override
	public void setCharacterStream(int arg0, Reader arg1, long arg2)
			throws SQLException {
		

	}

	@Override
	public void setClob(int arg0, Clob arg1) throws SQLException {
		

	}

	@Override
	public void setClob(int arg0, Reader arg1) throws SQLException {
		

	}

	@Override
	public void setClob(int arg0, Reader arg1, long arg2) throws SQLException {
		

	}

	@Override
	public void setDate(int arg0, Date arg1) throws SQLException {
		

	}

	@Override
	public void setDate(int arg0, Date arg1, Calendar arg2) throws SQLException {
		

	}

	@Override
	public void setDouble(int arg0, double arg1) throws SQLException {
		

	}

	@Override
	public void setFloat(int arg0, float arg1) throws SQLException {
		

	}

	@Override
	public void setInt(int arg0, int arg1) throws SQLException {
		

	}

	@Override
	public void setLong(int arg0, long arg1) throws SQLException {
		

	}

	@Override
	public void setNCharacterStream(int arg0, Reader arg1) throws SQLException {
		

	}

	@Override
	public void setNCharacterStream(int arg0, Reader arg1, long arg2)
			throws SQLException {
		

	}

	@Override
	public void setNClob(int arg0, NClob arg1) throws SQLException {
		

	}

	@Override
	public void setNClob(int arg0, Reader arg1) throws SQLException {
		

	}

	@Override
	public void setNClob(int arg0, Reader arg1, long arg2) throws SQLException {
		

	}

	@Override
	public void setNString(int arg0, String arg1) throws SQLException {
		

	}

	@Override
	public void setNull(int arg0, int arg1) throws SQLException {
		

	}

	@Override
	public void setNull(int arg0, int arg1, String arg2) throws SQLException {
		

	}

	@Override
	public void setObject(int arg0, Object arg1) throws SQLException {
		

	}

	@Override
	public void setObject(int arg0, Object arg1, int arg2) throws SQLException {
		

	}

	@Override
	public void setObject(int arg0, Object arg1, int arg2, int arg3)
			throws SQLException {
		

	}

	@Override
	public void setRef(int arg0, Ref arg1) throws SQLException {
		

	}

	@Override
	public void setRowId(int arg0, RowId arg1) throws SQLException {
		

	}

	@Override
	public void setSQLXML(int arg0, SQLXML arg1) throws SQLException {
		

	}

	@Override
	public void setShort(int arg0, short arg1) throws SQLException {
		

	}

	@Override
	public void setString(int arg0, String arg1) throws SQLException {
		

	}

	@Override
	public void setTime(int arg0, Time arg1) throws SQLException {
		

	}

	@Override
	public void setTime(int arg0, Time arg1, Calendar arg2) throws SQLException {
		

	}

	@Override
	public void setTimestamp(int arg0, Timestamp arg1) throws SQLException {
		

	}

	@Override
	public void setTimestamp(int arg0, Timestamp arg1, Calendar arg2)
			throws SQLException {
		

	}

	@Override
	public void setURL(int arg0, URL arg1) throws SQLException {
		

	}

	@Override
	public void setUnicodeStream(int arg0, InputStream arg1, int arg2)
			throws SQLException {
		

	}

	public void setResultData(ResultSet rs2) {
		this.rs = rs2;
	}

}
