package com.ibm.query.mapper;

import java.lang.reflect.InvocationTargetException;
import java.sql.CallableStatement;
import java.sql.SQLException;

/**
  * IRowMapper.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
  */
public interface ICallbaleResultMapper<T> {

	public T mapRow(CallableStatement cs) throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException;
}
