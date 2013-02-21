package com.ibm.query.connection;

import javax.sql.DataSource;

/**
  * DataSourceManager.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
  */
public class DataSourceHolder {
	
	private String name = null;
	
	private DataSource datasource = null;

	public DataSourceHolder(String name, DataSource datasource) {
		this.name = name;
		this.datasource = datasource;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}
	
	
}
