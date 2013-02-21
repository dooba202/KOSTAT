package com.ibm.green.kostat.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Generic DAO interface
 */
public interface GenericDAO<T, K extends Serializable> {
	
	/**
	 * Return the value to which the given <code>id</code> is mapped,
	 * or <code>null</code> if it doesn't contain mapping for the give <code>id</code>.
	 * 
	 * @param id - the <code>id</code> whose mapped value is to be returned
	 * @return the value to which the given <code>id</code> is mapped.
	 */
	public T find(K id);
	
	/**
	 * Return a <code>List</code> which contains all values.
	 * 
	 * @returns a <code>List</code> object which contains all values.
	 */
	public List<T> find();

	/**
	 * Store the given <code>value</code> and return the <code>id</code> of the store value.
	 * The <code>id</code> will be primary key in case of database.
	 * 
	 * @param value - value which is to be stored
	 * @return <code>id</code> which is assigned to the value
	 */
	public K save(T value);
	
	
	/**
	 * Update the given <code>value</code>.
	 * 
	 * @param value - the value which is to be updated
	 */
	public void update(T value);
	
	
	/**
	 * Delete the given <code>value</code>.
	 * 
	 * @param value - the value which is to be deleted
	 */
	public void delete(T value);	

}
