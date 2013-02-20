package com.ibm.query.execute.refelect;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.lang.reflect.Method;

import com.ibm.query.exception.InvokeFailedException;
import com.ibm.query.exception.MethodNotFoundException;
import com.ibm.query.utils.QueryStringUtil;

public class ObjectBeanInfo implements IBeanInfo{

	private BeanInfo beanInfo = null; 
	private Object inputData = null;

	public ObjectBeanInfo(Object inputData) throws IntrospectionException {
		this.inputData = inputData;
		this.beanInfo = Introspector.getBeanInfo(inputData.getClass());
	}

	public Object invokeTargetOperation(String methodName) throws MethodNotFoundException, InvokeFailedException {
		
		Method localMethod = null;
		MethodDescriptor[] arrayOfMethodDescriptor = beanInfo.getMethodDescriptors();
		for (int i = 0; i < arrayOfMethodDescriptor.length; i++) {
			String methodDisplayName = arrayOfMethodDescriptor[i].getDisplayName();
			if(QueryStringUtil.isEqual(methodDisplayName, methodName)){
				localMethod = arrayOfMethodDescriptor[i].getMethod();
				break;
			}
		}
		
		if(localMethod ==null){
			throw new MethodNotFoundException(methodName);
		}else{
			Object[] arrayOfObject = new Object[0];
			
			try {
				return localMethod.invoke(inputData, arrayOfObject);
			} catch (Exception e) {
				throw new InvokeFailedException(methodName, e);
			}
		}
		
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
