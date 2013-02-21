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

public class InvalidSelect2Test {

	@BeforeClass
	public static void setUpClass() throws Exception{
		FileInputStream fio = null;
		
		try {
			fio = new FileInputStream("sample/invalidCondition2.xml");
			
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
		
		SimpleUser user = new SimpleUser();
		user.setName("test");
		
		RuleContext query = DynamicHelper.getInstance().query(container, user);
		assertEquals(getResult(), query.getQuery());
	}
	
	private String getContent() {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ccc\n");
		sb.append("		\n");
		sb.append("		@[rule1]@[rule2]@[rule3]");
		return sb.toString();
	}

	private String getResult() {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ccc");
		return sb.toString();
	}
}
