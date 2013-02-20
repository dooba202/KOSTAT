package com.ibm.query.execute.refelect;

import com.ibm.query.exception.InvokeFailedException;
import com.ibm.query.exception.MethodNotFoundException;

public interface IBeanInfo {

	public Object invokeTargetOperation(String methodName) throws MethodNotFoundException, InvokeFailedException;
	
	public String getString(String fieldName) throws MethodNotFoundException, InvokeFailedException;

	public Integer getInteger(String fieldName) throws MethodNotFoundException, InvokeFailedException;
	
	public Long getLong(String fieldName) throws MethodNotFoundException, InvokeFailedException;
}
