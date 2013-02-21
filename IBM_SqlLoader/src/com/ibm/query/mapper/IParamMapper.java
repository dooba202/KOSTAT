package com.ibm.query.mapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
  * IParamMapper.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
 * @param <T>
  */
public interface IParamMapper<T> {

	void execute(PreparedStatement ps, T inputObject) throws SQLException ;

}
