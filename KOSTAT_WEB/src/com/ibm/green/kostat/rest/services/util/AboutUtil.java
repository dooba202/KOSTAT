package com.ibm.green.kostat.rest.services.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.green.exception.GreenRuntimeException;
import com.ibm.green.kostat.exception.SerializationException;
import com.ibm.green.kostat.rest.json.JSONSerializer;
import com.ibm.green.kostat.rest.services.AboutService;

public class AboutUtil {
	
	static Log logger = LogFactory.getLog(AboutUtil.class);
	
	// Use Jackson JSON Serializer
	static JSONSerializer serializer;
	
	static {
		serializer = JSONSerializer.getSerializer(JSONSerializer.JACKSON); 
	}	
	
	public static String getPropertiesAsJSON() {
		
		try {
			return serializer.serialize(AboutService.getAboutMap());
		} catch (SerializationException ex) {
			logger.error("Exception during serialization About properties.");
			throw new GreenRuntimeException(ex);
		}
	}
	
	public static String getProperty(String key) {
		
		return AboutService.getAboutMap().get(key);
	}
	
	public static String getLastConsolidationMonth() {
		
		return getProperty("lastConsolidationMonth");
	}
	
	public static String getFirstConsolidationMonth() {
		
		return getProperty("firstConsolidationMonth");
	}
	
}
