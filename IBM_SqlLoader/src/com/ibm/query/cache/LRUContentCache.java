package com.ibm.query.cache;

import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LRUContentCache<T> implements IContentCache<T> {

	private static IContentCache<Object> cache = null;

	final Map<String, ContentCacheData<T>> contentCache = Collections.synchronizedMap(new HashMap<String, ContentCacheData<T>>());
	final Deque<ContentCacheData<T>> contentList = new LinkedList<ContentCacheData<T>>();
	
	private int MAX_COUNT = 30;
	
	public static IContentCache<Object> getInstance() {
		synchronized (LRUContentCache.class) {
			if (cache == null) {
				cache = new LRUContentCache<Object>();
			}

			return cache;	
		}
	}

	/**
	 * << description >>
	 * 
	 * @param contentKey
	 * @return
	 */
	@Override
	public T fetchContent(String contentKey) {
		ContentCacheData<T> content =contentCache.get(contentKey);
		if (content != null) {
			contentList.remove(content);
			contentList.addFirst(content);
		}
		return content.data;
	}

	/**
	 * << description >>
	 * 
	 * @param key
	 * @param content
	 */
	@SuppressWarnings("unchecked")
	public <V> void cacheContent(String key, V content) {
		ContentCacheData<T> oldContent = contentCache.get(key);
		if (oldContent != null) {
			contentList.remove(oldContent);
		}else{
			if(contentList.size() >= MAX_COUNT){
				ContentCacheData<T> deleteme = contentList.getLast();
				clearContent(deleteme.key);
			}
		}
		
		ContentCacheData<T> newContent = new ContentCacheData<T>();
		newContent.key = key;
		newContent.data = (T) content;
		
		contentCache.put(key, newContent);
		contentList.addFirst(newContent);
	}

	public void clearContent(String key) {
		Object value = contentCache.get(key);
		contentList.remove(value);
		contentCache.remove(key);
	}

	@Override
	public void clearAllContent() {
		contentCache.clear();
		contentList.clear();
	}
	
	
	@Override
	public String toString() {
		return "LRUContentCache [contentCache=" + contentCache.size()
				+ ", contentList=" + contentList.size() + "]";
	}


	private class ContentCacheData<E>{
		private String key = null;
		private E data = null;
	}
}
