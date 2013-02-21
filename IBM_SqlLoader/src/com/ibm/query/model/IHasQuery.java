package com.ibm.query.model;

/**
  * IHasQuery.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
  */
public interface IHasQuery {

	public String getJdbc();
	
	public void setJdbc(String jndi);
	
	public String getId();
	
	public void setId(String id);
	
	public String getCategory();
	
	public void setCategory(String category);
	
	public String getRawQuery();
	
	public void setRawQuery(String sql);
	
	public boolean isUseCache();
	
	public boolean setUseCache(boolean isUseCache);
}
