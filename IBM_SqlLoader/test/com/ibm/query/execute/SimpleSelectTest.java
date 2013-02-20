package com.ibm.query.execute;

import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ibm.query.connection.DataSourceHolder;
import com.ibm.query.connection.DataSourceManager;
import com.ibm.query.data.SimpleCompany;
import com.ibm.query.data.SimpleUser;
import com.ibm.query.init.SqlReader;
import com.ibm.query.mapper.IParamMapper;
import com.ibm.query.mapper.IRowMapper;
import com.ibm.query.mapper.IStringMapper;
import com.ibm.query.mock.jdbc.ColumnData;
import com.ibm.query.mock.jdbc.DummyConnection;
import com.ibm.query.mock.jdbc.DummyDataSource;
import com.ibm.query.mock.jdbc.DummyResultSet;
import com.ibm.query.mock.jdbc.DummyResultSetMetaData;
import com.ibm.query.mock.jdbc.ResultData;
import com.ibm.query.mock.jdbc.ResultRow;
import com.ibm.query.parser.SqlXMLParserHandler;
import com.ibm.query.utils.QueryStringUtil;

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
	public void testSelectObject1() throws Exception{
		
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
		
		Object result = Jdbc.getInstance().selectObject("select1", rMapper, company);
		
		assertNotNull(result);
	}
	
	@Test
	public void testSelectObject2() throws Exception{
		
		IRowMapper<Object> rMapper = new IRowMapper<Object>(){

			@Override
			public Object mapRow(ResultSet resultSet, int row) throws SQLException, IllegalArgumentException,
					IllegalAccessException, InvocationTargetException {
				SimpleUser user = new SimpleUser();
				
				return user;
			}
			
		};
		
		IParamMapper<SimpleCompany> pMapper = new IParamMapper<SimpleCompany>() {
			@Override
			public void execute(PreparedStatement ps, SimpleCompany company) throws SQLException {
				ps.setString(1, company.getName());
			}
		};
		
		
		SimpleCompany company = new SimpleCompany();
		company.setName("test");
		
		Object result = Jdbc.getInstance().selectObject("select2", pMapper, rMapper, company);
		
		assertNotNull(result);
	}
	
	@Test
	public void testSelectObject3() throws Exception{
		
		IRowMapper<Object> rMapper = new IRowMapper<Object>(){

			@Override
			public Object mapRow(ResultSet resultSet, int row) throws SQLException, IllegalArgumentException,
					IllegalAccessException, InvocationTargetException {
				SimpleUser user = new SimpleUser();
				
				return user;
			}
			
		};
		
		IParamMapper<SimpleCompany> pMapper = new IParamMapper<SimpleCompany>() {
			@Override
			public void execute(PreparedStatement ps, SimpleCompany company) throws SQLException {
				ps.setString(1, company.getName());
			}
		};
		
		IStringMapper<SimpleCompany> sMapper = new IStringMapper<SimpleCompany>(){
			@Override
			public String replaceAll(String query, SimpleCompany company) {
				HashMap<String, String> paramMap = new HashMap<String, String>();
				
				paramMap.put("TS", "BSS");
				String sSql = QueryStringUtil.findAndReplace(query, paramMap);
				
				return sSql;
			}
		};
		
		
		SimpleCompany company = new SimpleCompany();
		company.setName("test");
		
		Object result = Jdbc.getInstance().selectObject("select2", pMapper, rMapper, sMapper, company);
		
		assertNotNull(result);
	}
	
}
