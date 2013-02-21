package com.ibm.query.model;

/**
  * DeleteQueryModel.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
  */
public class DeleteQueryModel extends RuleContainer implements IHasQuery {

	private String jndi = null;
	
	private String sqlId = null;
	
	private String category = null;
	
	private String query = null;

	@Override
	public String getJdbc() {
		return jndi;
	}
	
	@Override
	public void setJdbc(String jndi) {
		this.jndi = jndi;		
	}

	@Override
	public String getRawQuery() {
		return query;
	}

	@Override
	public void setRawQuery(String sql) {
		query = sql;
	}

	@Override
	public String getId() {
		return sqlId;
	}

	@Override
	public void setId(String id) {
		sqlId = id;		
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public boolean isUseCache() {
		return false;
	}
	
	public boolean setUseCache(boolean isUseCache) {
		return false;
	}

	@Override
	public String toString() {
		return "SelectQueryModel [jndi=" + jndi + ", sqlId=" + sqlId
				+ ", category=" + category + "]";
	}
}
