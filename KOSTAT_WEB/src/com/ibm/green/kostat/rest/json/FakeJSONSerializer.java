package com.ibm.green.kostat.rest.json;

import java.util.Collection;

import com.ibm.green.util.ClassClassificator;


/**
 * Just Fake JSON Serializer
 */
public class FakeJSONSerializer extends JSONSerializer {

	@Override
	String serializeImpl(Object object) {
		
		Class<?> clz = object.getClass();
		
		if (ClassClassificator.isNumberWrapperClass(clz)) {
			return object.toString();	
		}
		
		if (object instanceof Boolean) {
			return object.toString();
		}
		
		if (object instanceof Collection<?>) {
			return "\"<collection>\"";
		}
		
		return "\"" + object.toString() + "\"";		
	}

}
