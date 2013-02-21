package com.ibm.query.rule;


import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ibm.query.connection.DataSourceHolder;
import com.ibm.query.connection.DataSourceManager;
import com.ibm.query.data.SimpleCompany;
import com.ibm.query.data.SimpleDept;
import com.ibm.query.data.SimpleUser;
import com.ibm.query.execute.DynamicHelper;
import com.ibm.query.execute.manager.QueryManager;
import com.ibm.query.init.SqlReader;
import com.ibm.query.mock.jdbc.ColumnData;
import com.ibm.query.mock.jdbc.DummyConnection;
import com.ibm.query.mock.jdbc.DummyDataSource;
import com.ibm.query.mock.jdbc.DummyResultSet;
import com.ibm.query.mock.jdbc.DummyResultSetMetaData;
import com.ibm.query.mock.jdbc.ResultData;
import com.ibm.query.mock.jdbc.ResultRow;
import com.ibm.query.model.IHasQuery;
import com.ibm.query.parser.SqlXMLParserHandler;

public class For2Test {

	@BeforeClass
	public static void setUpClass() throws Exception{
		DummyConnection connection = new DummyConnection();
		DummyDataSource ds = new DummyDataSource(connection);
		connection.setResultData(getDummyResultSet());
		
		DataSourceHolder holder = new DataSourceHolder("sample", ds);
		
		DataSourceManager.getInstance().addDataSource(holder);
		
		FileInputStream fio = null;
		
		try {
			fio = new FileInputStream("sample/ForCondition2.xml");
			
			SqlReader reader = SqlReader.getInstance();
			reader.read(fio, new SqlXMLParserHandler());
		} finally{
			try {
				fio.close();
			} catch (Exception ignore) {
			}
		}
	}
	
	@AfterClass
	public static void tearDownClass() throws Exception{
		DataSourceManager.getInstance().dispose();
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
	
	@Test
	public void testSelectObject1() throws Exception{
		SimpleUser user1 = new SimpleUser();
		user1.setName("jaepil");
		
		List<SimpleUser> users = new ArrayList<SimpleUser>();
		users.add(user1);
		
		SimpleDept dept1 = new SimpleDept();
		dept1.setName("test1");
		dept1.setUsers(users);
		
		SimpleDept dept2 = new SimpleDept();
		dept2.setName("test2");
		
		List<SimpleDept> depts = new ArrayList<SimpleDept>();
		depts.add(dept1);
		depts.add(dept2);
		
		SimpleCompany company = new SimpleCompany();
		company.setName("test");
		company.setDepts(depts);
		
		IHasQuery container = QueryManager.getInstance().getCachedData("select1");
		RuleContext queryData = DynamicHelper.getInstance().query(container, company);
		assertEquals(getExpectQuery1(), queryData.getQuery());
		
	}
	
	private String getExpectQuery1() {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from aaa\n");
		sb.append("		where \n");
		sb.append("			1=1\n");
		sb.append("		 and a=?  and b=?\n");
		sb.append(" and a=?  and b=?");
		
		return sb.toString();
	}
	
}
