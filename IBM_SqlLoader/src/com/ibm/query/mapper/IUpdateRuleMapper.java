package com.ibm.query.mapper;

import java.sql.SQLException;

public interface IUpdateRuleMapper<T> {

	public void execute(T inputObject) throws SQLException;
}
