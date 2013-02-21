package com.ibm.query.execute.handler;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
  * IDataHandler.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
  */
public interface IDataHandler {

	public void saveData(PreparedStatement ps, int index, byte[] inputData)	throws SQLException;

	public String readData(ResultSet rs, String columnName) throws IOException, SQLException;

}
