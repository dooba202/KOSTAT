package com.ibm.query.mock.jdbc;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

public class DummyResultSetMetaData implements ResultSetMetaData {
	
	private List<ColumnData> columnList = null;
	
	public DummyResultSetMetaData(List<ColumnData> columnList) {
		super();
		this.columnList = columnList;
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
	public String getCatalogName(int index) throws SQLException {
		
		return null;
	}

	@Override
	public String getColumnClassName(int index) throws SQLException {
		
		return null;
	}

	@Override
	public int getColumnCount() throws SQLException {
		
		return columnList.size();
	}

	@Override
	public int getColumnDisplaySize(int index) throws SQLException {
		
		return 0;
	}

	@Override
	public String getColumnLabel(int index) throws SQLException {
		
		return null;
	}

	@Override
	public String getColumnName(int index) throws SQLException {
		ColumnData column = columnList.get(index-1);
		
		return column.getColumnName();
	}

	@Override
	public int getColumnType(int index) throws SQLException {
		ColumnData column = columnList.get(index-1);
		return column.getColumnType();
	}

	@Override
	public String getColumnTypeName(int index) throws SQLException {
		
		return null;
	}

	@Override
	public int getPrecision(int index) throws SQLException {
		
		return 0;
	}

	@Override
	public int getScale(int index) throws SQLException {
		
		return 0;
	}

	@Override
	public String getSchemaName(int index) throws SQLException {
		
		return null;
	}

	@Override
	public String getTableName(int index) throws SQLException {
		
		return null;
	}

	@Override
	public boolean isAutoIncrement(int index) throws SQLException {
		
		return false;
	}

	@Override
	public boolean isCaseSensitive(int index) throws SQLException {
		
		return false;
	}

	@Override
	public boolean isCurrency(int index) throws SQLException {
		
		return false;
	}

	@Override
	public boolean isDefinitelyWritable(int index) throws SQLException {
		
		return false;
	}

	@Override
	public int isNullable(int index) throws SQLException {
		
		return 0;
	}

	@Override
	public boolean isReadOnly(int index) throws SQLException {
		
		return false;
	}

	@Override
	public boolean isSearchable(int index) throws SQLException {
		
		return false;
	}

	@Override
	public boolean isSigned(int index) throws SQLException {
		
		return false;
	}

	@Override
	public boolean isWritable(int index) throws SQLException {
		
		return false;
	}

}
