package com.ibm.query.execute.handler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
  * BlobDataHandler.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
  */
public class BlobDataHandler implements IDataHandler{

	public String readData(ResultSet rs, String columnName) throws IOException, SQLException {
		String result = null;
		StringBuffer output = new StringBuffer();
		if (rs.next()) {
			Reader input = rs.getCharacterStream(columnName);
			
			try {
				char[] buffer = new char[1024];
				int byteRead;
				while ((byteRead = input.read(buffer, 0, 1024)) != -1) {
					output.append(buffer, 0, byteRead);
				}
				input.close();
				result = output.toString();	
			} catch (IOException e) {
				throw e;
			}finally{
				try {
					input.close();
				} catch (Exception e2) {
				}
			}
			
		}
		
		return result;
	}
	
	public void saveData(PreparedStatement ps, int index, byte[] inputData) throws SQLException{
		InputStream is = new ByteArrayInputStream(inputData);
		ps.setBinaryStream(index, is);
	}
}
