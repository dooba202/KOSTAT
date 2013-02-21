package com.ibm.query.mock.jdbc;

import java.util.ArrayList;
import java.util.List;

public class ResultRow {

	List<ResultData> columnList = new ArrayList<ResultData>();

	public void addData(ResultData column) {
		columnList.add(column);
	}

	public ResultData getColumnData(int columnIndex) {
		return columnList.get(columnIndex);
	}
	
}
