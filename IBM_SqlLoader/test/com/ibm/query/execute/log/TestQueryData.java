package com.ibm.query.execute.log;

import java.util.Map;

public class TestQueryData implements IHasQueryData {

	private String sql = null;
	
	private String sqlId = null;
	
	private Map<Integer, Object> paramMap = null;
	
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Map<Integer, Object> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<Integer, Object> paramMap) {
		this.paramMap = paramMap;
	}

	@Override
	public String getSqlId() {
		return sqlId;
	}

	@Override
	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}

	@Override
	public long getDuration() {
		return 0;
	}
	
	
}
