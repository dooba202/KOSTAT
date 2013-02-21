package com.ibm.query.execute.refelect;

import java.beans.IntrospectionException;
import java.util.Map;

import com.ibm.query.exception.InvokeFailedException;
import com.ibm.query.exception.MethodNotFoundException;

@SuppressWarnings("rawtypes")
public class MapBeanInfo implements IBeanInfo{
	private Map map = null;

	public MapBeanInfo(Object inputData) throws IntrospectionException {
		this.map = (Map)inputData;
	}
	
	public Object invokeTargetOperation(String methodName) throws MethodNotFoundException, InvokeFailedException {
		return map.get(methodName);
	}
	
	public String getString(String fieldName) throws MethodNotFoundException, InvokeFailedException{
		Object result = invokeTargetOperation(fieldName);
		return (String)result;
	}

	public Integer getInteger(String fieldName) throws MethodNotFoundException, InvokeFailedException{
		Object result = invokeTargetOperation(fieldName);
		return (Integer)result;
	}
	
	public Long getLong(String fieldName) throws MethodNotFoundException, InvokeFailedException{
		Object result = invokeTargetOperation(fieldName);
		return (Long)result;
	}
}
