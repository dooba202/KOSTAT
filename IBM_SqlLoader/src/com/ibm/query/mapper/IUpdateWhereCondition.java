package com.ibm.query.mapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface IUpdateWhereCondition<T> {
	
	public void execute(int index, PreparedStatement ps, T inputObject) throws SQLException;
}
