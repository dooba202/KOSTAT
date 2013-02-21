package com.ibm.query.execute.manager;

import java.util.HashMap;
import java.util.Map;

import com.ibm.query.exception.QueryNotFoundException;
import com.ibm.query.model.IHasQuery;


/**
  * QueryCacheManager.java
  * << description >>
  * 
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
  */
public class QueryManager {

	private static QueryManager instance = null;

	private Map<String, IHasQuery> queryMap = null;
	
	private QueryManager() {
		queryMap = new HashMap<String, IHasQuery>();
	}
	
	public static QueryManager getInstance(){
		synchronized (QueryManager.class) {
			if(instance ==null){
				instance = new QueryManager();
			}
			
			return instance;	
		}
	}

	
	public void addQuery(String key, IHasQuery value){
		queryMap.put(key, value);
	}
	
	public int getSize(){
		return queryMap.size();
	}
	
	public void clear(){
		queryMap.clear();
	}

	public IHasQuery getCachedData(String key) throws QueryNotFoundException {
		IHasQuery query = queryMap.get(key);
		if(query ==null){
			throw new QueryNotFoundException(key);
		}
		return query;
	}
}
