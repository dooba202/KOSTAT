package com.ibm.query.cache;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

public class WeakContentCache<T> implements IContentCache<T> {
	
	private static IContentCache<Object> cache = null;

	final Map<String, T> contentCache = Collections.synchronizedMap(new WeakHashMap<String, T>());

	public static IContentCache<Object> getInstance(){
		synchronized (WeakContentCache.class) {
			if(cache == null){
				cache = new WeakContentCache<Object>();
			}
			
			return cache;	
		}
	}
	
	/* (non-Javadoc)
	 * @see com.ibm.query.cache.IContentCache#fetchContent(java.lang.String)
	 */
	/**
	 * << description >>
	 * @param contentKey
	 * @return
	 */
	@Override
	public T fetchContent(String contentKey) {
		T content = (T) contentCache.get(contentKey);
		
		return content;
	}
	
	/* (non-Javadoc)
	 * @see com.ibm.query.cache.IContentCache#cacheContent(java.lang.String, V)
	 */
	/**
	 * << description >>
	 * @param key
	 * @param content
	 */
	@SuppressWarnings("unchecked")
	public <V> void cacheContent(String key, V content) {
		contentCache.put(key, (T)content);
	}
	
	/* (non-Javadoc)
	 * @see com.ibm.query.cache.IContentCache#clearContent(java.lang.String)
	 */
	/**
	 * << description >>
	 * @param key
	 */
	@Override
	public void clearContent(String key){
		contentCache.remove(key);
	}
	
	@Override
	public void clearAllContent() {
		contentCache.clear();		
	}
}
