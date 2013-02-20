package com.ibm.query.execute.log;

import java.util.HashMap;
import java.util.Map;

public class QueryWriterTest {

	public void testWrite() throws Exception{
		Map<Integer, Object> paramMap = new HashMap<Integer, Object>();
		TestQueryData queryData = new TestQueryData();
		queryData.setSql("select * from table where id=?");
		queryData.setParamMap(paramMap);
		
		TestLog logger = new TestLog();
		
		QueryWriter.debug(logger, queryData);
		
	}
}
