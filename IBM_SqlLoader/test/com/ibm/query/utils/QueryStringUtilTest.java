package com.ibm.query.utils;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.ibm.query.data.SimpleCompany;
import com.ibm.query.model.ParamterType;
import com.ibm.query.model.SelectQueryModel;
import com.ibm.query.rule.RuleContext;


/**
  * StringUtilTest.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
  */
public class QueryStringUtilTest {

	@Test
	public void testFindAndReplace() throws Exception{
		String fulltext = "select * from test where id=$id$";
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", "'test'");
		
		String query = QueryStringUtil.findAndReplace(fulltext, paramMap);
		assertEquals("select * from test where id='test'", query);
		
		fulltext = "select * from test where id=$id2$";
		query = QueryStringUtil.findAndReplace(fulltext, paramMap);
		assertEquals("select * from test where id=$id2$", query);
	}
	
	@Test
	public void testFindWordCount() throws Exception{
		assertEquals(0, QueryStringUtil.countWordCount(null, "?"));
		assertEquals(0, QueryStringUtil.countWordCount("", "?"));
		assertEquals(1, QueryStringUtil.countWordCount("?", "?"));
		assertEquals(2, QueryStringUtil.countWordCount("??", "?"));
		assertEquals(3, QueryStringUtil.countWordCount("??-?", "?"));
		assertEquals(3, QueryStringUtil.countWordCount("??\n?", "?"));
	}
	
	@Test
	public void testQetReplacedQuery1() throws Exception{
		Map<Integer, Object> paramMap = new HashMap<Integer, Object>();
		
		assertEquals("id=?)", QueryStringUtil.getReplacedQuery("id=?)", paramMap));
		
		paramMap.put(1, 1);
		assertEquals("id=1", QueryStringUtil.getReplacedQuery("id=?", paramMap));
		
		paramMap.put(2, "a");
		assertEquals("id=1 and name='a'", QueryStringUtil.getReplacedQuery("id=? and name=?", paramMap));
		
		paramMap.put(3, new Timestamp(createCalendar(2012, 01, 01).getTimeInMillis()));
		
//		assertEquals("id=1 and name='a' and created='2012-01-01 00:00:00.264''", QueryStringUtil.getReplacedQuery("id=? and name=? and created=?", paramMap));
	}
	
	@Test
	public void testQetReplacedQuery2() throws Exception{
		Map<Integer, Object> paramMap = new HashMap<Integer, Object>();
		
		paramMap.put(1, "a");
		assertEquals("id='?' and name='a'", QueryStringUtil.getReplacedQuery("id='?' and name=?", paramMap));
	}
	
	private static Calendar createCalendar(int year, int month, int date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(year, month - 1, date, 0, 0, 0);
        return calendar;
    }
	
	@Test
	public void testFindAndReplace1() throws Exception{
		Map<String, String> paramMap = new HashMap<String, String>();
		
		assertEquals("#", QueryStringUtil.findAndReplace("#", "#", paramMap));
		assertEquals("#1", QueryStringUtil.findAndReplace("#", "#1", paramMap));
		assertEquals("#a#", QueryStringUtil.findAndReplace("#", "#a#", paramMap));
		assertEquals("#abc#", QueryStringUtil.findAndReplace("#", "#abc#", paramMap));
		assertEquals("123#abc#", QueryStringUtil.findAndReplace("#", "123#abc#", paramMap));
		
		paramMap.put("abc", "test");
		assertEquals("test", QueryStringUtil.findAndReplace("#", "#abc#", paramMap));
		assertEquals("#test", QueryStringUtil.findAndReplace("#", "##abc#", paramMap));
		assertEquals("test#", QueryStringUtil.findAndReplace("#", "#abc##", paramMap));
		assertEquals("testtest", QueryStringUtil.findAndReplace("#", "#abc##abc#", paramMap));
	}
	
	@Test
	public void testgetQueryString(){
		assertEquals("", QueryStringUtil.getQueryString(null, "\\"));
		assertEquals("", QueryStringUtil.getQueryString("", "\\"));
		assertEquals("*'", QueryStringUtil.getQueryString("'", "*"));
		assertEquals("*%", QueryStringUtil.getQueryString("%", "*"));
		assertEquals("1*'2", QueryStringUtil.getQueryString("1'2", "*"));
		assertEquals("1*%2", QueryStringUtil.getQueryString("1%2", "*"));
	}
	
	@Test
	public void testStringbuilder(){
		StringBuilder sb = new StringBuilder();
		sb.append("1").append("2").append("3");
		sb.replace(1, 2, "a");
		
		assertEquals("1a3", sb.toString());
	}
	
	@Test
	public void testStringbuilder2(){
		StringBuilder sb = new StringBuilder();
		sb.append("test1234abcd");
		QueryStringUtil.replace(sb, "1234", "kkkk");
		
		assertEquals("testkkkkabcd", sb.toString());
	}
	
	@Test
	public void testFindSpecificWord() throws Exception{
		ParamterType paramterType = new ParamterType();
		paramterType.setType("string");
		paramterType.setMethod("getName");
		
		SelectQueryModel ruleContainer = new SelectQueryModel();
		ruleContainer.addType("abc", paramterType);
		RuleContext context = new RuleContext();
		SimpleCompany inputData = new SimpleCompany();
		inputData.setName("test");
		
		String query = QueryStringUtil.replaceSpecificWord(ruleContainer, "select * from $abc$", context, inputData);
		assertEquals("select * from $0.abc$", query.trim());
		
		query = QueryStringUtil.replaceSpecificWord(ruleContainer, "$abc$$abc$", context, inputData);
		assertEquals("$0.abc$$0.abc$", query.trim());
		
		query = QueryStringUtil.replaceSpecificWord(ruleContainer, "", context, inputData);
		assertEquals("", query.trim());
	}
	
	@Test
	public void testFindSpecificWords() throws Exception{
		assertEquals(0, QueryStringUtil.findSpecificWords("").size());
		assertEquals(0, QueryStringUtil.findSpecificWords("$abc").size());
		assertEquals(0, QueryStringUtil.findSpecificWords("abc$").size());
		assertEquals(1, QueryStringUtil.findSpecificWords("$abc$").size());
		assertEquals(2, QueryStringUtil.findSpecificWords("$abc$$abc$").size());
		assertEquals(3, QueryStringUtil.findSpecificWords("$abc$$abc$$abc$").size());
	}
}
