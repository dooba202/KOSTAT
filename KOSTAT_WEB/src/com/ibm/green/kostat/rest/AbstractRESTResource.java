package com.ibm.green.kostat.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;

import com.ibm.green.exception.ErrorCode;
import com.ibm.green.exception.GreenRuntimeException;
import com.ibm.green.kostat.dto.UserDTO;
import com.ibm.green.kostat.rest.json.JSONSerializer;

/**
 * Abstract parent class for all REST resource classes of KoStat service. 
 * 
 * This class provides common implementation that would be required to implement REST resource 
 * in KoStat service.
 */
public abstract class AbstractRESTResource implements KoStatConstants {
	
	public final static String SESSKEY_USER = "KoStat_USER";
	
	@Context protected UriInfo uriInfo;
	@Context protected HttpServletRequest httpRequest;
	
	/*
	 * Apache common logger
	 */
	protected Log logger; 
	
	/*
	 * Jackson ObjectMapper
	 */
	protected ObjectMapper mapper;
	
	/*
	 * JSON Serializer
	 */
	protected JSONSerializer serializer;
	
	/* 
	 * Default constructor. 
	 * Logger will have the class name as its name.
	 */
	protected AbstractRESTResource() {
		
		this.logger = LogFactory.getLog(this.getClass());
		this.mapper = new ObjectMapper();
		this.serializer = JSONSerializer.getSerializer(JSONSerializer.JACKSON); // Use Jackson JSON Serializer
	}
	
	/*
	 * Constructor that supports customer logger.
	 */
	protected AbstractRESTResource(Log customLogger) {
		
		this.logger = customLogger;
		this.mapper = new ObjectMapper();		
	}

	public Log getLogger() {
		
		return logger;
	}

	public void setLogger(Log logger) {
		
		this.logger = logger;
	}

	public ObjectMapper getMapper() {
		
		return mapper;
	}

	public void setMapper(ObjectMapper mapper) {
		
		this.mapper = mapper;
	}
	
	public JSONSerializer getSerializer() {
		
		return serializer;
	}

	public void setSerializer(JSONSerializer serializer) {
		
		this.serializer = serializer;
	}

	/*
	 * log the information of current request.
	 */
	protected void logReq(String curMethod) throws IOException {
		
		if (!logger.isDebugEnabled()) return;

		logReq(curMethod, null);
	}
	
	/*
	 * log the information of current request with entity
	 */
	protected void logReq(String curMethod, Object entity) throws IOException {

		if (!logger.isDebugEnabled()) return;
		
		String msg = curMethod + " was called by REQUEST : " + httpRequest.getMethod() + " " + uriInfo.getPath();
		
		String queryStr = httpRequest.getQueryString();
		if (queryStr != null) {
			msg += "?" + queryStr;
		}
		
		if (entity != null) {
			msg += " ENTITY : " + mapper.defaultPrettyPrintingWriter().writeValueAsString(entity);
		}
		
		HttpSession session = httpRequest.getSession(false);
		if (session != null) {
			UserDTO user = (UserDTO) session.getAttribute(SESSKEY_USER);
			if (user != null) {
				msg = "[userid:" + user.getUserID() + "] " + msg;
			}
		}
		
		logger.debug(msg);
	}
	
	/*
	 * Generate entity object for error message with the given <code>Exception</code>
	 */
	protected ErrorMessage generateErrorMessage(Exception ex) {

		return new ErrorMessage(ex, httpRequest.getLocale());
	}
	
	/*
	 * Generate entity object for error message with the given <code>ErrorCode</code>
	 */
	protected ErrorMessage generateErrorMessage(ErrorCode errorCode) {
		
		return new ErrorMessage(errorCode, httpRequest.getLocale());
	}

	/*
	 * Generate entity object for error message with the given <code>ErrorCode</code>
	 */
	protected ErrorMessage generateErrorMessage(ErrorCode errorCode, String redirectURL) {
		
		RedirectMessage msg =  new RedirectMessage(errorCode, httpRequest.getLocale());
		msg.setRedirect(redirectURL);
		return msg;
	}
	
	
	/*
	 * Convert the given MultivaluedMap to general Map
	 */
	protected static <K, V> Map<K, V> convertMultivaluedMapToSingleMap(MultivaluedMap<K,V> multiMap) {
	
		Map<K, V> map = new HashMap<K, V>();
		
		for (K key : multiMap.keySet()) {
			map.put(key, multiMap.getFirst(key));
		}
		
		return map;
	}
	
	/*
	 * Remove then entry that has "_ALL_" as value.
	 */
	protected static <K, V> void removeReservedALLValueFromMap(Map<K, V> valueMap) {
		
		List<K> removeKeys = new ArrayList<K>();
		for (K key : valueMap.keySet()) {
			V value = valueMap.get(key);
			if ((value != null) && (value.toString().equals(FIELDS_ALL))) {
				removeKeys.add(key);
			}
		}
		
		for (K key : removeKeys) {
			valueMap.remove(key);
		}
	}
	
	/*
	 * Generate URI for the given page.
	 * The given relative path should be the path from the context root.
	 * This method MUST be called when uriInfo and httpRequest is available
	 */
	protected URI generateURI(String relativePath) {
		return this.generateURI(relativePath, new HashMap<String, String>());
	}
	
	/*
	 * Generate URI for the given page.
	 * The given relative path should be the path from the context root.
	 * This method MUST be called when uriInfo and httpRequest is available
	 * Given query param will be appended into the end of the URL as query param format
	 */	
	protected URI generateURI(String relativePath, HashMap<String, String> queryParam) {
		
		String baseURI = uriInfo.getBaseUri().toString();
		String servletPath =  httpRequest.getServletPath();
		
		if (baseURI.endsWith(servletPath)) {
			baseURI = baseURI.substring(0, baseURI.length() - servletPath.length());
		} else if (baseURI.endsWith(servletPath + "/")) {
			baseURI = baseURI.substring(0, baseURI.length() - (servletPath.length() + 1));
		}		
		
		StringBuilder stbURI = new StringBuilder(baseURI + "/" + relativePath);
		
		boolean bFirst = true;
		for (String key : queryParam.keySet()) {
			String val = queryParam.get(key);
			
			if (val != null) {
				if (bFirst) {
					bFirst = false;
					stbURI.append("?");
				} else {
					stbURI.append("&");
				}
				stbURI.append(key).append("=").append(val);
			}
		}
		
		try {
			URI uri = new URI(stbURI.toString());
			return uri;
		} catch (URISyntaxException ex) {
			logger.error("");
			throw new GreenRuntimeException(ErrorCode.COMMON_UNKNOWN, ex);
		}
		
	}	

	/*
	 * Convert the given yyyymm to the previous year and same month.
	 */
	protected String convertPreviousYearInSameMonth(String yyyymm) {
		
		if (yyyymm.length() < 6) {
			throw new IllegalArgumentException("yyyymm has wrong format." + yyyymm);
		}
		
		String yyyy = yyyymm.substring(0,  4);
		String mm = yyyymm.substring(4);
			
		int nYYYY = 0;
		try {
			nYYYY = Integer.parseInt(yyyy);
			nYYYY = nYYYY - 1;
		} catch (Exception ex) {
			throw new IllegalArgumentException("yyyymm has wrong format." + yyyymm);
		}
		return "" + nYYYY + mm;	
	}
	
}
