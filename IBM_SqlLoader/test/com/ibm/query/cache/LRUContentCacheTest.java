package com.ibm.query.cache;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class LRUContentCacheTest {

	@Test
	public void testFetchContent(){
		IContentCache<Object> cache = LRUContentCache.getInstance();
		
		String key = "abc";
		String value = "abcde";
		cache.cacheContent(key, value);
		assertNotNull(cache.fetchContent(key));
	}
	
	@Test
	public void testFetchContent2(){
		IContentCache<Object> cache = LRUContentCache.getInstance();
		
		String key = "abc";
		String value = "abcde";
		for (int i = 0; i < 100; i++) {
			cache.cacheContent(key + i, value + i);
			assertNotNull(cache.fetchContent(key));	
		}
	}
}
