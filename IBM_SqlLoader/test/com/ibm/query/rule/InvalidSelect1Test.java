package com.ibm.query.rule;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ibm.query.data.SimpleUser;
import com.ibm.query.execute.DynamicHelper;
import com.ibm.query.execute.manager.QueryManager;
import com.ibm.query.init.SqlReader;
import com.ibm.query.model.IHasQuery;
import com.ibm.query.parser.SqlXMLParserHandler;

public class InvalidSelect1Test {

	@BeforeClass
	public static void setUpClass() throws Exception{
		FileInputStream fio = null;
		
		try {
			fio = new FileInputStream("sample/invalidCondition1.xml");
			
			SqlReader reader = SqlReader.getInstance();
			reader.read(fio, new SqlXMLParserHandler());
		} finally{
			try {
				fio.close();
			} catch (Exception ignore) {
			}
		}
	}
	
	@Test(expected=Exception.class)
	public void testSelect1Object() throws Exception{
		IHasQuery container = QueryManager.getInstance().getCachedData("select1");
		
		SimpleUser user = new SimpleUser();
		user.setName("test");
		
		RuleContext query = DynamicHelper.getInstance().query(container, user);
		assertEquals(getResult(), query.getQuery());
	}
	
	@Test(expected=Exception.class)
	public void testSelect2Object() throws Exception{
		IHasQuery container = QueryManager.getInstance().getCachedData("select2");
		
		SimpleUser user = new SimpleUser();
		user.setName("test");
		
		RuleContext query = DynamicHelper.getInstance().query(container, user);
		assertEquals(getResult(), query.getQuery());
	}
	
	@Test(expected=Exception.class)
	public void testSelect3Object() throws Exception{
		IHasQuery container = QueryManager.getInstance().getCachedData("select3");
		
		SimpleUser user = new SimpleUser();
		user.setName("test");
		
		RuleContext query = DynamicHelper.getInstance().query(container, user);
		assertEquals(getResult(), query.getQuery());
	}
	
	private String getResult() {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ccc");
		return sb.toString();
	}
}
