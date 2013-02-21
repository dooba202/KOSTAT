package com.ibm.query.utils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.ibm.query.exception.InvokeFailedException;
import com.ibm.query.exception.NotSupportedTypeException;
import com.ibm.query.execute.handler.ITypeHandler;
import com.ibm.query.execute.handler.TypeHandlerFactory;
import com.ibm.query.execute.refelect.BeanInfoFactory;
import com.ibm.query.execute.refelect.IBeanInfo;
import com.ibm.query.model.IHasRule;
import com.ibm.query.model.ParamterType;
import com.ibm.query.rule.RuleContext;

/**
  * StringUtil.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
  */
public class QueryStringUtil {
	
	public static boolean isNull(String arg0) {
    	if (arg0 == null || "".equals(arg0.trim()) || arg0.trim().equals("null")) {
            return true;
        } else {
            return false;
        }
    }
	
	public static boolean isNotEmpty(String condition) {
		return condition != null && !"".equals(condition);
	}

	public static String replace(String src, String oldstr, String newstr)
    {
        if (src == null)
            return null;

        StringBuffer dest = new StringBuffer("");
        int  len = oldstr.length();
        int  srclen = src.length();
        int  pos = 0;
        int  oldpos = 0;

        while ((pos = src.indexOf(oldstr, oldpos)) >= 0) {
            dest.append(src.substring(oldpos, pos));
            dest.append(newstr);
            oldpos = pos + len;
        }

        if (oldpos < srclen)
            dest.append(src.substring(oldpos, srclen));

        return dest.toString();
    }
	
	public static void replace(StringBuilder src, String oldstr, String newstr)
    {
        StringBuffer dest = new StringBuffer("");
        int  len = oldstr.length();
        int  srclen = src.length();
        int  pos = 0;
        int  oldpos = 0;

        while ((pos = src.indexOf(oldstr, oldpos)) >= 0) {
            dest.append(src.substring(oldpos, pos));
            dest.append(newstr);
            oldpos = pos + len;
        }

        if (oldpos < srclen)
            dest.append(src.substring(oldpos, srclen));

        src.replace(0, src.length(), dest.toString());
    }

	public static String findAndReplace(IHasRule ruleContainer, String sql, RuleContext context, Object inputData) throws Exception {
		String findStr = "$";
		
		StringBuilder dest = null;
        int firstPos = 0;
        int nextPos = 0;
        
        while ((firstPos = sql.indexOf(findStr, 0)) >= 0) {
        	
        	dest = new StringBuilder("");
        	
        	nextPos = sql.indexOf(findStr, firstPos+1);
        	
            dest.append(sql.substring(0, firstPos));
            
            String fieldName = sql.substring(firstPos+1, nextPos);
			
			context.addFieldIndex(fieldName);
			
            dest.append("?");
            dest.append(sql.substring(nextPos+1, sql.length()));
            
            sql = dest.toString();
        }

        dest = null;
        return sql;
	}
	
	public static String replaceSpecificWord(IHasRule ruleContainer, String sql, RuleContext context, Object inputData) throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		
        String prefix = context.getObjectKey(inputData);
        
        IBeanInfo bean = BeanInfoFactory.getBeanInfo(inputData);
        
        List<String> findSpecificWords = findSpecificWords(sql);
        
        Iterator<String> keys = findSpecificWords.iterator();
        while (keys.hasNext()) {
			String key = (String) keys.next();
			
			ParamterType parameterType = ruleContainer.getParameterType(key);
			if(parameterType == null){
				throw new InvokeFailedException(ruleContainer + " : fieldname : "+ key);
			}
			
			ITypeHandler handler = TypeHandlerFactory.getInstance().getTypeHandler(parameterType.getType());
			if(handler != null){
				Object value = handler.getValue(parameterType, bean);
				context.addFieldValue(prefix + "."+ key, key, value);
			}else{
				throw new NotSupportedTypeException(parameterType.toString());
			}
		
			paramMap.put(key, "$"+ prefix + "."+ key+ "$");
			
		}

        return findAndReplace(sql, paramMap);
	}
	
	public static List<String> findSpecificWords(String sql){
		String findStr = "$";
		List<String> list = new ArrayList<String>();
	
		int firstPos = 0;
        int nextPos = 0;
        
        while ((firstPos = sql.indexOf(findStr, nextPos)) >= 0) {
        	nextPos = sql.indexOf(findStr, firstPos+1);
        	if(nextPos ==-1){
        		break;
        	}
        	
        	String key = sql.substring(firstPos+1, nextPos);
        	list.add(key);
        	
        	nextPos++;
        }
        
		return list;
	}
	
	public static String findAndReplace(String sql, final Map<String, String> paramMap) {
		String findStr = "$";
		
		return findAndReplace(findStr, sql, paramMap);
	}
	
	public static String findAndReplace(String findStr, String sql, final Map<String, String> paramMap) {
		
		
		Map<String, String> paramMap2 = getReplaceMap(paramMap);
		
		StringBuilder dest = null;
        int firstPos = 0;
        int nextPos = 0;
        
        boolean isFind = false;
        
        while ((firstPos = sql.indexOf(findStr, nextPos)) >= 0) {
        	isFind = true;
        	
        	dest = new StringBuilder("");
        	
        	nextPos = sql.indexOf(findStr, firstPos+1);
        	if(nextPos==-1){
        		isFind = false;
        		
        		break;
        	}
        	
            dest.append(sql.substring(0, firstPos));
            String key = sql.substring(firstPos+1, nextPos);
            String value = paramMap2.get(key.toLowerCase());
            if(value != null){
            	dest.append(value);	
            }else{
            	dest.append(findStr + key + findStr);
            }
            
            dest.append(sql.substring(nextPos+1, sql.length()));
            
            sql = dest.toString();
        }

        paramMap2 = null;
        return isFind ? dest.toString() : sql;
	}

	private static Map<String, String> getReplaceMap(
			final Map<String, String> paramMap) {
		Map<String, String> paramMap2 = new HashMap<String, String>();
		
		Set<Entry<String, String>> contents = paramMap.entrySet();
		for (Entry<String, String> entry : contents) {
			paramMap2.put(entry.getKey().toLowerCase(), entry.getValue());
		}
		return paramMap2;
	}
	
	public static String appendValue(String source,String suffix){
		if(source == null){
			return suffix;
		}else{
			return source + suffix;
		}
	}
	
	public static java.util.Date getDateFromSQLTimeStamp(org.apache.commons.logging.Log logger,java.sql.ResultSet rs, String columnName){
		return getDateFromSQLTimeStamp(logger, rs, columnName, false);
	}
	
	public static java.util.Date getDateFromSQLTimeStamp(org.apache.commons.logging.Log logger,java.sql.ResultSet rs, String columnName, boolean viewTraceLog){
		try{
			return new java.util.Date(rs.getTimestamp(columnName).getTime());
		}catch (Exception e) {
			String message = "[TimeStamp Null] "+columnName + " value is null.";
			if(viewTraceLog){
				logger.error(message, e);
			}else{
				logger.error(message);
			}
			return null;
		}
	}	
	
	public static String getInString(List<String> ids){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<ids.size();i++){
			String id = ids.get(i);
			sb.append(" '"+id+"' ");
			if(i!=ids.size()-1) sb.append(" , ");
		}
		
		return sb.toString();
	}
	
	public static String getInLong(List<Long> ids){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<ids.size();i++){
			Long id = ids.get(i);
			sb.append(" "+id+" ");
			if(i!=ids.size()-1) sb.append(" , ");
		}
		
		return sb.toString();
	}

	public static int countWordCount(String fullString, String word) {
		if(fullString == null || fullString.trim().isEmpty()){
			return 0;
		}
		
		int firstPos = 0;
		int nextPos = 0;
		int count = 0;
		
		while ((firstPos = fullString.indexOf(word, nextPos)) >= 0) {
			count++;
			nextPos = fullString.indexOf(word, firstPos+1);
			if(nextPos==-1){
				break;
			}
		}
		return count;
	}

	public static String getReplacedQuery(String sql, Map<Integer, Object> paramMap) {
		if(sql == null || sql.trim().isEmpty()){
			return "";
		}
		
		if(paramMap==null || paramMap.isEmpty()){
			return sql;
		}
		
		int index = 1;
		boolean isComment = false;
		StringBuilder query = new StringBuilder();
		for (int i = 0; i < sql.length(); i++) {
			char currentChar = sql.charAt(i);
			
			if(currentChar == '\''){
				if(isComment){
					isComment = false;	
				}else{
					isComment = true;
				}
				
				query.append(currentChar);	
			}else if(currentChar == '?'){
				Object value = paramMap.get(index);
				
				if(!isComment){
					if(value instanceof String){
						query.append("'"+value + "'");	
					}else if(value instanceof Integer){
						query.append(value);	
					}else if(value instanceof Long){
						query.append(value);	
					}else if(value instanceof Timestamp){
						query.append("'"+value + "'");	
					}
					
					index++;
				}else{
					query.append(currentChar);	
				}
			}else{
				query.append(currentChar);	
			}
			
			
		}
		
		return query.toString();
	}

	public static boolean isEqual(String val1, String val2){
		if(val1==null){
			return false;
		}
		if(val2==null){
			return false;
		}
		
		if(val1.trim().toLowerCase().equals(val2.trim().toLowerCase())){
			return true;
		}
		
		return false;
	}
	
	public static String getQueryString(String str, String escape){
		if(isNull(str)){
			return "";
		}
		if(isNull(escape)){
			return str;
		}
		
		str = replaceString(str, "\\", escape + "\\");
//		str = replaceString(str, "'", escape + "'");
//		str = replaceString(str, "'", "''");
		str = replaceString(str, "%", escape + "%");
		str = replaceString(str, "_", escape + "_");
		
		return str;
	}
	
	public static String replaceString(String str, String source, String target) {
		int startIndex = 0;
		int totalLength = target.length();
		while (true) {
			int len = source.length();
			if (str.indexOf(source, startIndex) < 0) {
				break;
			}

			int pos = str.indexOf(source, startIndex);
			str = str.substring(0, pos) + target + str.substring(pos + len);
			startIndex = pos + totalLength;
		}

		return str;
	}

	public static String subString(String query, String startStr, String endStr) {
		int startIndex = query.indexOf(startStr, 0);
		int endIndex = query.indexOf(endStr, 0);
		
		return query.substring(startIndex + startStr.length(), endIndex);
	}

	public static void replaceRange(StringBuilder query, String startStr, String endStr, String subQuery) {
		int startIndex = query.indexOf(startStr, 0);
		int endIndex = query.indexOf(endStr, 0);
		
		StringBuilder sb = new StringBuilder();
		sb.append(query.substring(0, startIndex));
		sb.append(subQuery);
		sb.append(query.substring(endIndex + endStr.length(), query.length()));
		
		query.replace(0, query.length(), sb.toString());
	}
}
