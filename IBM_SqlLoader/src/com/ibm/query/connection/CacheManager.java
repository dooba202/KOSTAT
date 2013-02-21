package com.ibm.query.connection;

import java.sql.PreparedStatement;

import com.ibm.query.cache.WeakContentCache;
import com.ibm.query.exception.QueryNotFoundException;
import com.ibm.query.execute.log.IHasQueryData;
import com.ibm.query.execute.manager.QueryManager;
import com.ibm.query.model.IHasQuery;

public class CacheManager {

	@SuppressWarnings("unchecked")
	public <T> T get(String sqlId, PreparedStatement ps){
		try {
			IHasQuery container = QueryManager.getInstance().getCachedData(sqlId);
			if(container == null || !container.isUseCache()){
				return null;
			}
		} catch (QueryNotFoundException ignore) {
		}
		
		
		if(ps instanceof IHasQueryData){
			IHasQueryData queryData = (IHasQueryData)ps;
			String cacheKey = sqlId + ":" + queryData.getParamMap().values().toString();
			
			Object cachedData = WeakContentCache.getInstance().fetchContent(cacheKey); 
			if(cachedData != null){
				return (T) cachedData;
			}
			
		}
		return null;
	}
	
	public <T> void put(String sqlId, PreparedStatement ps, T t){
		try {
			IHasQuery container = QueryManager.getInstance().getCachedData(sqlId);
			if(container == null || !container.isUseCache()){
				return;
			}
		} catch (QueryNotFoundException ignore) {
		}
		
		if(ps instanceof IHasQueryData){
			IHasQueryData queryData = (IHasQueryData)ps;
			String cacheKey = sqlId + ":" + queryData.getParamMap().values().toString();
			
			WeakContentCache.getInstance().cacheContent(cacheKey, t);
		}
	}
}
