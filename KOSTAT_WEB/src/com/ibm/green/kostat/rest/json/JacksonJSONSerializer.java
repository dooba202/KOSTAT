package com.ibm.green.kostat.rest.json;

import org.codehaus.jackson.map.ObjectMapper;

import com.ibm.green.kostat.exception.SerializationException;

/**
 * Concrete implementation of <code>JSONSerializer</code> with Apache's Jackson.
 */
public class JacksonJSONSerializer extends JSONSerializer {
	
	ObjectMapper objMapper = new ObjectMapper();

	@Override
	String serializeImpl(Object object) throws SerializationException {
		try {
			return objMapper.writeValueAsString(object);
		} catch (Exception ex) {
			throw new SerializationException(ex);
		}
	}
}
