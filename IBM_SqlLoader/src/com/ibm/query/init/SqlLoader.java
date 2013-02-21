package com.ibm.query.init;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.query.connection.DataSourceHolder;
import com.ibm.query.connection.DataSourceManager;
import com.ibm.query.exception.ApplicationException;
import com.ibm.query.execute.manager.QueryManager;
import com.ibm.query.model.ConfigModel;
import com.ibm.query.model.DataSourceModel;
import com.ibm.query.parser.ConfigXMLParserHandler;
import com.ibm.query.parser.SqlXMLParserHandler;

/**
  * SqlLoader.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightâ“’. IBM Korea 2012 All Rights Reserved.
  */
public class SqlLoader {

	private static ClassLoader defaultClassLoader;
	private static final String DEFAULT_FILE_NAME = "sqlFiles.xml";
	
	public SqlLoader() throws ServletException{
		SqlLoader.init(this.getClass().getClassLoader(), DEFAULT_FILE_NAME);
	}
	
	
	public static ClassLoader getDefaultClassLoader() {
		return defaultClassLoader;
	}

	
	public static void setDefaultClassLoader(ClassLoader defaultClassLoader) {
		SqlLoader.defaultClassLoader = defaultClassLoader;
	}
	
	public static void init(String path) throws ServletException {
		if(path.toLowerCase().endsWith("xml")){
			init(getClassLoader(), path);	
		}else{
			init(getClassLoader(), path + "/" +DEFAULT_FILE_NAME);
		}
	}

	public static void init(ClassLoader loader, String configFile) throws ServletException {
		StringBuffer sb = new StringBuffer();
		sb.append("================ DATASOURCE INFORMATION ==================");
		
		Log logger = LogFactory.getLog(SqlLoader.class);
		ConfigModel configModel = readConfig(loader, configFile, sb);
		
		try {
			
			
			InitialContext ic = new InitialContext();
			List<DataSourceModel> datasources = configModel.getDataSources();
			for (DataSourceModel dataSourceModel : datasources) {
				String jndi = dataSourceModel.getValue("DataSource");
				sb.append("\n=== DataSource Name : "+ jndi);
				
				Object obj = ic.lookup(jndi);
				if(obj ==null){
					logger.debug("get connection failed " + jndi);
					continue;
				}
				
				if(! (obj instanceof DataSource)){
					logger.debug("Invalid DataSource " + jndi);
					continue;
				}
				
				if(obj instanceof DataSource){
					DataSource ds = (DataSource) obj;
					DataSourceHolder holder = new DataSourceHolder(dataSourceModel.getName(), ds);
					
					DataSourceManager.getInstance().addDataSource(holder);
				}
				
			}
			sb.append("\n=== DataSource count : "+ DataSourceManager.getInstance().getDataSourceSize());
			sb.append("\n==========================================================");
			
		} catch (Exception e) {
			throw new ApplicationException("Could not read Configure "+ e.getMessage());
		} finally {
			logger.debug("\n" + sb.toString());
		}
		
	}
	
	static ConfigModel readConfig(ClassLoader loader, String path, StringBuffer sb){
		Log logger = LogFactory.getLog(SqlLoader.class);
		ConfigModel configModel = null;
		
		InputStream fio = null;
		try {
			fio = loader.getResourceAsStream(path);
			
			SqlReader reader = SqlReader.getInstance();
			
			ConfigXMLParserHandler configHandler = new ConfigXMLParserHandler();
			SqlXMLParserHandler sqlHandler = new SqlXMLParserHandler();
			
			reader.read(fio, configHandler);
			
			configModel = configHandler.getRootModel();
			List<String> list = configHandler.getRootModel().getList();
			
			for (String xml : list) {
				InputStream sql = null;
				try {
					String configFile = xml;
					sb.append("\n=== "+ configFile);
					sql = loader.getResourceAsStream(configFile);
					
					if(sql != null){
						reader.read(sql, sqlHandler);
					}
					sb.append(" : OK");
				} catch (Exception e) {
					sb.append(" : FAIL");
					logger.error("SqlLoader.readConfig failed\n"+e.getMessage(), e);
				}finally{
					try {
						sql.close();
					} catch (Exception ignore) {
					}
				}
			}
			
			
			sb.append("\n");
			sb.append("\n=== File count : "+ list.size());
			sb.append("\n=== Query count : " + QueryManager.getInstance().getSize());
		} catch (Exception e) {
			throw new ApplicationException("Could not read Configure "+ e.getMessage());
		}finally{
			try {
				fio.close();
			} catch (Exception ignore) {
			}
		}
		
		return configModel;
	}
	
	public static void initTest(String path) throws ServletException {
		readConfig(getClassLoader(), path + DEFAULT_FILE_NAME, new StringBuffer());
	}

	public static void initTest(ClassLoader loader, String path) throws ServletException {
		readConfig(loader, path + DEFAULT_FILE_NAME, new StringBuffer());
	}
	
	private static ClassLoader getClassLoader() {
		if (defaultClassLoader != null) {
			return defaultClassLoader;
		} else {
			return Thread.currentThread().getContextClassLoader();
		}
	}
	
	public static URL getResourceURL(String resource) throws IOException {
	    return getResourceURL(getClassLoader(), resource);
	  }

	public static URL getResourceURL(ClassLoader loader, String resource)
			throws IOException {
		URL url = null;
		
		if (loader != null){
			url = loader.getResource(resource);
		}
			
		if (url == null){
			url = ClassLoader.getSystemResource(resource);
		}
			
		if (url == null){
			throw new FileNotFoundException("Could not find resource " + resource);
		}
		return url;
	}
	
}
