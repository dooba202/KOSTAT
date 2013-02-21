package com.ibm.query.rule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

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
import com.ibm.query.execute.DynamicHelper;
import com.ibm.query.execute.Jdbc;
import com.ibm.query.execute.manager.QueryManager;
import com.ibm.query.init.SqlReader;
import com.ibm.query.mapper.IRowMapper;
import com.ibm.query.mock.jdbc.ColumnData;
import com.ibm.query.mock.jdbc.DummyConnection;
import com.ibm.query.mock.jdbc.DummyDataSource;
import com.ibm.query.mock.jdbc.DummyResultSet;
import com.ibm.query.mock.jdbc.DummyResultSetMetaData;
import com.ibm.query.mock.jdbc.ResultData;
import com.ibm.query.mock.jdbc.ResultRow;
import com.ibm.query.model.IHasQuery;
import com.ibm.query.parser.SqlXMLParserHandler;

public class SimpleSelectTest {

	@BeforeClass
	public static void setUpClass() throws Exception{
		DummyConnection connection = new DummyConnection();
		DummyDataSource ds = new DummyDataSource(connection);
		connection.setResultData(getDummyResultSet());
		
		DataSourceHolder holder = new DataSourceHolder("sample", ds);
		
		DataSourceManager.getInstance().addDataSource(holder);
		
		FileInputStream fio = null;
		
		try {
			fio = new FileInputStream("sample/simpleCondition1.xml");
			
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
	public void testIsNotNull() throws Exception{
		
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
		
		Object result = Jdbc.getInstance().dynamicSelectObject("select1", rMapper, company);
		assertNotNull(result);
		
		IHasQuery container = QueryManager.getInstance().getCachedData("select1");
		RuleContext queryData = DynamicHelper.getInstance().query(container, company);
		System.out.println(queryData.getQuery());
	}
	
	@Test
	public void testIsNotEmpty() throws Exception{
		
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
		
		Object result = Jdbc.getInstance().dynamicSelectObject("select2", rMapper, company);
		assertNotNull(result);
		
		IHasQuery container = QueryManager.getInstance().getCachedData("select2");
		RuleContext queryData = DynamicHelper.getInstance().query(container, company);
		System.out.println(queryData.getQuery());
	}
	
	@Test
	public void testIsEmpty() throws Exception{
		
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
		
		Object result = Jdbc.getInstance().dynamicSelectObject("select3", rMapper, company);
		assertNotNull(result);
		
		IHasQuery container = QueryManager.getInstance().getCachedData("select3");
		RuleContext queryData = DynamicHelper.getInstance().query(container, company);
		System.out.println(queryData.getQuery());
	}
	
	@Test
	public void testIsSame() throws Exception{
		
		IRowMapper<Object> rMapper = new IRowMapper<Object>(){

			@Override
			public Object mapRow(ResultSet resultSet, int row) throws SQLException, IllegalArgumentException,
					IllegalAccessException, InvocationTargetException {
				SimpleUser user = new SimpleUser();
				
				return user;
			}
			
		};
		
		SimpleCompany company = new SimpleCompany();
		company.setName("Test2");
		
		Object result = Jdbc.getInstance().dynamicSelectObject("select4", rMapper, company);
		assertNotNull(result);
		
		IHasQuery container = QueryManager.getInstance().getCachedData("select4");
		RuleContext queryData = DynamicHelper.getInstance().query(container, company);
		System.out.println(queryData.getQuery());
	}
	
	@Test
	public void testIsNotSame() throws Exception{
		
		IRowMapper<Object> rMapper = new IRowMapper<Object>(){

			@Override
			public Object mapRow(ResultSet resultSet, int row) throws SQLException, IllegalArgumentException,
					IllegalAccessException, InvocationTargetException {
				SimpleUser user = new SimpleUser();
				
				return user;
			}
			
		};
		
		SimpleCompany company = new SimpleCompany();
		company.setName("Test2");
		
		Object result = Jdbc.getInstance().dynamicSelectObject("select5", rMapper, company);
		assertNotNull(result);
		
		IHasQuery container = QueryManager.getInstance().getCachedData("select5");
		RuleContext queryData = DynamicHelper.getInstance().query(container, company);
		System.out.println(queryData.getQuery());
		assertEquals("select * from company", queryData.getQuery());
	}
	
	@Test
	public void testIsTrue() throws Exception{
		
		IRowMapper<Object> rMapper = new IRowMapper<Object>(){

			@Override
			public Object mapRow(ResultSet resultSet, int row) throws SQLException, IllegalArgumentException,
					IllegalAccessException, InvocationTargetException {
				SimpleUser user = new SimpleUser();
				
				return user;
			}
			
		};
		
		SimpleCompany company = new SimpleCompany();
		company.setName("Test2");
		
		Object result = Jdbc.getInstance().dynamicSelectObject("select6", rMapper, company);
		assertNotNull(result);
		
		IHasQuery container = QueryManager.getInstance().getCachedData("select6");
		RuleContext queryData = DynamicHelper.getInstance().query(container, company);
		System.out.println(queryData.getQuery());
		assertNotSame("select * from company", queryData.getQuery());
	}
}
