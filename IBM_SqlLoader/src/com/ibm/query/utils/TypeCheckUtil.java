package com.ibm.query.utils;

public class TypeCheckUtil {

	public static boolean isPrimitive(Object obj){
		if(obj instanceof String){
			return true;
		}
		
		if(obj instanceof Integer){
			return true;
		}
		
		if(obj instanceof Long){
			return true;
		}
		
		return false;
	}
}
