package com.ibm.query.rule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ibm.query.data.SimpleCompany;
import com.ibm.query.execute.DynamicHelper;
import com.ibm.query.execute.manager.QueryManager;
import com.ibm.query.init.SqlReader;
import com.ibm.query.mapper.IRowMapper;
import com.ibm.query.model.IHasQuery;
import com.ibm.query.parser.SqlXMLParserHandler;

public class SimpleSelect1Test {

	@BeforeClass
	public static void setUpClass() throws Exception{
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
	
	@Test
	public void testSelectObject() throws Exception{
		IHasQuery container = QueryManager.getInstance().getCachedData("select1");
		
		RuleContext query = DynamicHelper.getInstance().query(container, null);
		assertEquals(getResult(), query.getQuery());
	}

	@Test
	public void testSelectObject2() throws Exception{
		
		IRowMapper<Object> rMapper = new IRowMapper<Object>(){

			@Override
			public Object mapRow(ResultSet resultSet, int row) throws SQLException, IllegalArgumentException,
					IllegalAccessException, InvocationTargetException {
				SimpleCompany company = new SimpleCompany();
				
				return company;
			}
			
		};
		
		SimpleCompany company = new SimpleCompany();
		company.setName("test");
		
		IHasQuery container = QueryManager.getInstance().getCachedData("select2");
		RuleContext queryData = DynamicHelper.getInstance().query(container, company);
		
		assertNotNull(queryData);
		
	}
	
	private String getResult() {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from company");
		return sb.toString();
	}
}
