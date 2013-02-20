package com.ibm.query.parser;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;

import org.junit.Test;

import com.ibm.query.execute.manager.QueryManager;
import com.ibm.query.init.SqlReader;

/**
  * SimpleParserTest.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
  */
public class SimpleParserTest {

	@Test
	public void testSelect1() throws Exception{
		FileInputStream fio = null;
		
		try {
			fio = new FileInputStream("sample/simpleSelect1.xml");
			
			SqlReader reader = SqlReader.getInstance();
			reader.read(fio, new SqlXMLParserHandler());
			
			assertEquals("select1", QueryManager.getInstance().getCachedData("select1").getId());
			assertEquals("select * from users", QueryManager.getInstance().getCachedData("select1").getRawQuery());
		} catch (Exception e) {
			throw e;
		}finally{
			fio.close();
		}
	}
}
