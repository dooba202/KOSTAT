package com.ibm.query.execute.refelect;

import java.beans.IntrospectionException;
import java.util.Map;

public class BeanInfoFactory {

	public static IBeanInfo getBeanInfo(Object inputData) throws IntrospectionException{
		if(inputData instanceof Map){
			return new MapBeanInfo(inputData);
		}
		
		if(inputData == null){
			return new MapBeanInfo(inputData);
		}
		
		return new ObjectBeanInfo(inputData);
	}
}
