package com.ibm.query.model;

import java.util.HashMap;
import java.util.Map;

/**
  * DataSourceModel.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
  */
public class DataSourceModel {
	
	private String name = null;
	
	private Map<String, String> propertyMap = new HashMap<String, String>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addProperty(String key, String value) {
		propertyMap.put(key, value);		
	}

	public String getValue(String key) {
		return propertyMap.get(key);
	}

}
