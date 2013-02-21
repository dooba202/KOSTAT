package com.ibm.green.kostat.dao.fake;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.ibm.green.kostat.dao.GenericDAO;

/**
 * Super class for fake implementation of <code>GenericDAO</code>
 * <br>
 * It provides common implementation for fake concrete DAO classes.
 *
 * @param <T> Persistent object type
 * @param <K> Primary key type
 */
public abstract class GenericDAOFakeImpl<T, K extends Serializable> implements GenericDAO<T, K> {

	// Store HashMap for each fake DAO implementation class
	static ConcurrentHashMap<Class<?>, ConcurrentHashMap<?, ?>> memoryStores;
	
	
	static {
		memoryStores = new ConcurrentHashMap<Class<?>, ConcurrentHashMap<?,?>>();
	}

	/*
	 * get memory store for the given class. If not exist, create new one
	 */
	@SuppressWarnings("unchecked")
	ConcurrentHashMap<K, T> getStore() {
		
		Class<?> cls = this.getClass();
		ConcurrentHashMap<K, T> store = null;
		store = (ConcurrentHashMap<K, T>) memoryStores.get(cls);

		if (store == null) {
			// store doesn't exist at this point
			ConcurrentHashMap<K, T> newStore = new ConcurrentHashMap<K, T>();
			store = (ConcurrentHashMap<K, T>) memoryStores.putIfAbsent(cls, newStore);
			
			if (store == null) {
				// newStore was added. Return newStore;
				store = newStore;
			}
		}
		return store;
	}

	/*
	 * remove memory store for the given class
	 */
	boolean removeStore(Class<?> cls) {
		
		if (memoryStores.remove(cls) != null) return true;
		return false;
	}
	
	/*
	 * Return the unique id for the given <code>value</code>.
	 * If <code>value</code> doesn't have unique id yet, it must provides one.
	 */
	protected abstract K getUniqueID(T value);

	@Override
	public T find(K id) {
		
		ConcurrentHashMap<K, T> store = (ConcurrentHashMap<K,T>) getStore();
		return store.get(id);
	}
	
	@Override
	public List<T> find() {

		ConcurrentHashMap<K, T> store = (ConcurrentHashMap<K,T>) getStore();
		return new ArrayList<T>(store.values());
	}

	@Override
	public K save(T value) {

		ConcurrentHashMap<K, T> store = (ConcurrentHashMap<K,T>) getStore();
		K id = getUniqueID(value);
		store.put(id, value);
		return id;
	}

	@Override
	public void update(T value) {
		
		ConcurrentHashMap<K, T> store = (ConcurrentHashMap<K,T>) getStore();
		store.put(getUniqueID(value), value);
	}

	@Override
	public void delete(T value) {
		
		ConcurrentHashMap<K, T> store = (ConcurrentHashMap<K,T>) getStore();
		store.remove(getUniqueID(value));
	}
	
}
