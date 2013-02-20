package com.ibm.query.execute.log;

import java.util.Map;

import org.apache.commons.logging.Log;

import com.ibm.query.utils.QueryStringUtil;

public class QueryWriter {

	public static void debug(Log logger, IHasQueryData queryData) {
		String sql = queryData.getSql();
		Map<Integer, Object> paramMap = queryData.getParamMap();
		
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("SQL_ID : "+ queryData.getSqlId());
			sb.append("\nDURATION : "+ queryData.getDuration());
			
			String fullQuery = QueryStringUtil.getReplacedQuery(sql, paramMap);
			sb.append("\n\n").append(fullQuery);
			
			logger.debug("\n\n"+sb.toString()+"\n\n");
		} catch (Exception ignore) {
			logger.error(ignore);
		}
	}

}
