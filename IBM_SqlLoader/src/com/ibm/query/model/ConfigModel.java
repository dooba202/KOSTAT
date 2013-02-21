package com.ibm.query.model;

import java.util.ArrayList;
import java.util.List;

/**
  * ConfigModel.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
  */
public class ConfigModel {

	private List<String> list = null;
	
	private List<DataSourceModel> dataSources = null;
	
	public List<String> getList() {
		if(list==null){
			list = new ArrayList<String>();
		}
		
		return list;
	}
	
	public List<DataSourceModel> getDataSources() {
		if(dataSources==null){
			dataSources = new ArrayList<DataSourceModel>();
		}
		
		return dataSources;
	}

	public void addQuery(String file) {
		if(list == null){
			list = new ArrayList<String>();
		}
		
		list.add(file);
	}
	
	public void addDataSource(DataSourceModel dataSource){
		if(dataSources == null){
			dataSources = new ArrayList<DataSourceModel>();
		}
		
		dataSources.add(dataSource);
	}
}
