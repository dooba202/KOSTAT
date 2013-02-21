package com.ibm.query.callable;

import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ibm.query.connection.DataSourceHolder;
import com.ibm.query.connection.DataSourceManager;
import com.ibm.query.data.SimpleUser;
import com.ibm.query.execute.Jdbc;
import com.ibm.query.execute.manager.QueryManager;
import com.ibm.query.init.SqlReader;
import com.ibm.query.mapper.ICallableParamMapper;
import com.ibm.query.mapper.ICallbaleResultMapper;
import com.ibm.query.mock.jdbc.ColumnData;
import com.ibm.query.mock.jdbc.DummyConnection;
import com.ibm.query.mock.jdbc.DummyDataSource;
import com.ibm.query.mock.jdbc.DummyResultSet;
import com.ibm.query.mock.jdbc.DummyResultSetMetaData;
import com.ibm.query.mock.jdbc.ResultData;
import com.ibm.query.mock.jdbc.ResultRow;
import com.ibm.query.model.IHasQuery;
import com.ibm.query.parser.SqlXMLParserHandler;

public class SimpleProcedureCallTest {

	@BeforeClass
	public static void setUpClass() throws Exception{
		DummyConnection connection = new DummyConnection();
		DummyDataSource ds = new DummyDataSource(connection);
		connection.setResultData(getDummyResultSet());
		
		DataSourceHolder holder = new DataSourceHolder("sample", ds);
		
		DataSourceManager.getInstance().addDataSource(holder);
		
		FileInputStream fio = null;
		
		try {
			fio = new FileInputStream("sample/simpleProcedure.xml");
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
	
	@Test
	public void testSelect1Object() throws Exception{
		IHasQuery container = QueryManager.getInstance().getCachedData("proc1");
		assertNotNull(container);
		
		ICallbaleResultMapper<Object> rMapper = new ICallbaleResultMapper<Object>(){

			@Override
			public Object mapRow(CallableStatement cs) throws SQLException,
					IllegalArgumentException, IllegalAccessException,
					InvocationTargetException {
				SimpleUser user = new SimpleUser();
				user.setName(cs.getString(3));
				
				return user;
			}
			
		};
		
		ICallableParamMapper<Object> pMapper = new ICallableParamMapper<Object>(){

			@Override
			public void execute(CallableStatement cs, Object inputObject) throws SQLException {
				cs.setString(1, "test");
				cs.setInt(2, 100);
				cs.registerOutParameter(3, Types.VARCHAR);
				cs.registerOutParameter(4, Types.NUMERIC);
			}
			
		};
		
		Object result = Jdbc.getInstance().call("proc1", pMapper, rMapper, null);
		assertNotNull(result);
	}
	
	
}
