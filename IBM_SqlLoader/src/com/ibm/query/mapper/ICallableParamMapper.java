package com.ibm.query.mapper;

import java.sql.CallableStatement;
import java.sql.SQLException;

/**
  * IParamMapper.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
 * @param <T>
  */
public interface ICallableParamMapper<T> {

	void execute(CallableStatement cs, T inputObject) throws SQLException ;

}
