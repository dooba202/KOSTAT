package com.ibm.query.connection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.query.exception.UnknownDataSourceException;

/**
  * DataSourceManager.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
  */
public class DataSourceManager {
	private static DataSourceManager instance = new DataSourceManager();
	
	private Log logger = LogFactory.getLog(getClass());
	
	// map of data sources
	private Map<String, DataSourceHolder> datasources = new HashMap<String, DataSourceHolder>();
	
	
	public static synchronized DataSourceManager getInstance() {
		if(instance == null){
			instance = new DataSourceManager();
		}
		return instance;
	}
	
	public void addDataSource(DataSourceHolder ds){
		String key = ds.getName();
		logger.debug("Key : "+ key);
		
		datasources.put(key, ds);
	}
	
	public int getDataSourceSize(){
		return datasources.keySet().size();
	}
	
	public void dispose(){
		datasources.clear();	
	}
	
	public DataSource getDataSource(String name) throws UnknownDataSourceException{
		logger.debug("DataSource Name : "+ name);
		
		DataSourceHolder ds = datasources.get(name);
		if(ds == null){
			throw new UnknownDataSourceException("Unable to locate DataSource " + name);
		}else{
			return ds.getDatasource();
		}
	}
	
	
	public void setDataSources(List<DataSourceHolder> dataSources){
		for (DataSourceHolder dataSourceHolder : dataSources) {
			DataSourceManager.getInstance().addDataSource(dataSourceHolder);
		}
	}
	
}
