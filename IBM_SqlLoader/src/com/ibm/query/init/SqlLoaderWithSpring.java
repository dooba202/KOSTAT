package com.ibm.query.init;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.query.exception.ApplicationException;

public class SqlLoaderWithSpring {
	
	public SqlLoaderWithSpring() throws ServletException{
		
		init(this.getClass().getClassLoader(), "");
	}
	
	public SqlLoaderWithSpring(String path) throws ServletException{
		init(this.getClass().getClassLoader(), path);
	}

	public void init(ClassLoader loader, String path) throws ServletException {
		StringBuffer sb = new StringBuffer();
		sb.append("============ DATASOURCE INFORMATION with SPRING =============");
		
		Log logger = LogFactory.getLog(SqlLoader.class);
		
		try {
			SqlLoader.readConfig(loader, path, sb);
			
			sb.append("\n==========================================================");
			
		} catch (Exception e) {
			throw new ApplicationException("Could not read Configure "+ e.getMessage());
		} finally {
			logger.debug("\n" + sb.toString());
		}
	}
	
}
