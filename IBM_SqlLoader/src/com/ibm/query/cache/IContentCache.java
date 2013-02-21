package com.ibm.query.cache;

public interface IContentCache<T> {

	public abstract T fetchContent(String contentKey);

	public abstract <V> void cacheContent(String key, V content);

	public abstract void clearContent(String key);

	public abstract void clearAllContent();

}