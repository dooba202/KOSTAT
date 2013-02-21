package com.ibm.green.util;

import java.util.HashSet;


/**
 * Simple Java Class Classification class
 */
public class ClassClassificator {
	
	private final static HashSet<Class<?>> wrapperClasses = new HashSet<Class<?>>();
	private final static HashSet<Class<?>> numberWrapperClasses = new HashSet<Class<?>>();	
	
	static {
		wrapperClasses.add(Byte.class);
		wrapperClasses.add(Short.class);
		wrapperClasses.add(Integer.class);
		wrapperClasses.add(Long.class);
		wrapperClasses.add(Float.class);
		wrapperClasses.add(Double.class);
		wrapperClasses.add(Character.class);
		wrapperClasses.add(Boolean.class);
		wrapperClasses.add(Void.class); // special case
		
		numberWrapperClasses.add(Short.class);
		numberWrapperClasses.add(Integer.class);
		numberWrapperClasses.add(Long.class);
		numberWrapperClasses.add(Float.class);
		numberWrapperClasses.add(Double.class);
	}
	
	/**
	 * Return the given Class <code>clz</code> is a wrapper class of primitive type or not.
	 * 
	 * @param clz Class
	 * @return
	 */
	public static boolean isWrapperClass(Class<?> clz) {
		
		return wrapperClasses.contains(clz);
	}
	
	/**
	 * Return the given Class <code>clz</code> is a wrapper class of primitive number type or not
	 * 
	 * @param clz
	 * @return
	 */
	public static boolean isNumberWrapperClass(Class<?> clz) {
		
		return numberWrapperClasses.contains(clz);
	}
	

}
