package com.ibm.query.execute;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ibm.query.connection.DataSourceHolder;
import com.ibm.query.connection.DataSourceManager;
import com.ibm.query.data.SimpleCompany;
import com.ibm.query.data.SimpleUser;
import com.ibm.query.init.SqlReader;
import com.ibm.query.mapper.IRowMapper;
import com.ibm.query.mock.jdbc.ColumnData;
import com.ibm.query.mock.jdbc.ConnectionState;
import com.ibm.query.mock.jdbc.DummyConnection;
import com.ibm.query.mock.jdbc.DummyDataSource;
import com.ibm.query.mock.jdbc.DummyResultSet;
import com.ibm.query.mock.jdbc.DummyResultSetMetaData;
import com.ibm.query.mock.jdbc.ResultData;
import com.ibm.query.mock.jdbc.ResultRow;
import com.ibm.query.parser.SqlXMLParserHandler;

public class ConnectionTest {

	@BeforeClass
	public static void setUpClass() throws Exception{
		DummyConnection connection = new DummyConnection();
		DummyDataSource ds = new DummyDataSource(connection);
		connection.setResultData(getDummyResultSet());
		
		DataSourceHolder holder = new DataSourceHolder("sample", ds);
		
		DataSourceManager.getInstance().addDataSource(holder);
		
		FileInputStream fio = null;
		
		try {
			fio = new FileInputStream("sample/simpleSelect1.xml");
			
			SqlReader reader = SqlReader.getInstance();
			reader.read(fio, new SqlXMLParserHandler());
		} finally{
			try {
				fio.close();
			} catch (Exception ignore) {
			}
		}
	}
	
	private static ResultSet getDummyResultSet(){
		List<ResultRow> resultList = new ArrayList<ResultRow>();
		
		ResultRow row1 = getResultRow(1, "test1");
		resultList.add(row1);
		
		ResultRow row2 = getResultRow(2, "test2");
		resultList.add(row2);
		
		return new DummyResultSet(resultList, getDummyResultSetMetaData());
	}
	
	private static ResultRow getResultRow(int id, String name) {
		ResultRow row = new ResultRow();
		
		ResultData column1 = new ResultData();
		column1.setIntValue(id);
		row.addData(column1);
		
		ResultData column2 = new ResultData();
		column2.setStringValue(name);
		row.addData(column2);
		
		return row;
	}
	
	private static DummyResultSetMetaData getDummyResultSetMetaData(){
		List<ColumnData> columnList = new ArrayList<ColumnData>();
		
		ColumnData column1 = new ColumnData();
		column1.setColumnName("id");
		column1.setColumnType(Types.INTEGER);
		columnList.add(column1);
		
		ColumnData column2 = new ColumnData();
		column2.setColumnName("name");
		column2.setColumnType(Types.VARCHAR);
		columnList.add(column2);
		
		return new DummyResultSetMetaData(columnList);
	}
	
	@Test
	public void testNoneTransactionalState() throws Exception{
		
		IRowMapper<Object> rMapper = new IRowMapper<Object>(){

			@Override
			public Object mapRow(ResultSet resultSet, int row) throws SQLException, IllegalArgumentException,
					IllegalAccessException, InvocationTargetException {
				SimpleUser user = new SimpleUser();
				
				return user;
			}
			
		};
		
		SimpleCompany company = new SimpleCompany();
		company.setName("test");
		
		int before  = ConnectionState.getCount();
		
		for (int i = 0; i < 100; i++) {
			Jdbc.getInstance().selectObject("select1", rMapper, company);
		}
		
		for (int i = 0; i < 100; i++) {
			Jdbc.getInstance().selectList("select1", rMapper, company);
		}
		
		for (int i = 0; i < 100; i++) {
			Jdbc.getInstance().insert("select1", company);
		}
		
		for (int i = 0; i < 100; i++) {
			Jdbc.getInstance().update("select1", company);
		}
		
		for (int i = 0; i < 100; i++) {
			Jdbc.getInstance().delete("select1", company);
		}
		
		assertEquals(before, ConnectionState.getCount());
	}
	
	@Test
	public void testDefaultTransactionalState() throws Exception{
		
		IRowMapper<Object> rMapper = new IRowMapper<Object>(){

			@Override
			public Object mapRow(ResultSet resultSet, int row) throws SQLException, IllegalArgumentException,
					IllegalAccessException, InvocationTargetException {
				SimpleUser user = new SimpleUser();
				
				return user;
			}
			
		};
		
		try {
			Jdbc.getInstance().begin();
			
			SimpleCompany company = new SimpleCompany();
			company.setName("test");
			
			for (int i = 0; i < 100; i++) {
				Jdbc.getInstance().selectObject("select1", rMapper, company);
			}
			
			assertEquals(1, ConnectionState.getCount());
			
			for (int i = 0; i < 100; i++) {
				Jdbc.getInstance().selectList("select1", rMapper, company);
			}
			
			assertEquals(1, ConnectionState.getCount());
			
			for (int i = 0; i < 100; i++) {
				Jdbc.getInstance().insert("select1", company);
			}
			
			assertEquals(1, ConnectionState.getCount());
			
			for (int i = 0; i < 100; i++) {
				Jdbc.getInstance().update("select1", company);
			}
			
			assertEquals(1, ConnectionState.getCount());
			
			for (int i = 0; i < 100; i++) {
				Jdbc.getInstance().delete("select1", company);
			}
			
			Jdbc.getInstance().commit();
		} finally{
			Jdbc.getInstance().close();
		}
		
		
		assertEquals(0, ConnectionState.getCount());
	}
	
}
