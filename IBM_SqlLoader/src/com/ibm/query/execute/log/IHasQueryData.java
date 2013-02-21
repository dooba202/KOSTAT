package com.ibm.query.execute.log;

import java.util.Map;

public interface IHasQueryData {
	
	public String getSqlId();

	public void setSqlId(String sqlId);

	public String getSql();
	
	public Map<Integer, Object> getParamMap();

	public long getDuration();
}
