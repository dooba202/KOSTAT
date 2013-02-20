package com.ibm.query.mapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface IUpdateSetCondition<T> {

	public String getColumnName();

	public boolean isSatisfied(T inputObject);

	public void execute(int index, PreparedStatement ps, T inputObject) throws SQLException;
}
