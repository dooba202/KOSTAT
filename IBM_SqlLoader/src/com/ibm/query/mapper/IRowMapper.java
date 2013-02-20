package com.ibm.query.mapper;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
  * IRowMapper.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
  */
public interface IRowMapper<T> {

	public T mapRow(ResultSet resultSet, int row) throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException;
}
