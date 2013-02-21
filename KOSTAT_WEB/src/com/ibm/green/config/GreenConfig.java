package com.ibm.green.config;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Properties;

/**
 * Configuration Utility Class
 */
public class GreenConfig {
	
	static HashMap<String, Object> defaults = new HashMap<String, Object>(); 
	static Properties prop = new Properties();
	
	static {
		initialize();
	}
	
	static void initialize() {
		// load properties or initialize configuration libraries
		
		loadDefaultProperties();
		loadPropertiesFile();
	}
	
	static void loadDefaultProperties() {
		
		/* database configuration */
		//defaults.put("green.sqlloader.datasource", "iBEE"); // logical datasource name in SqlLoader
	}
	
	
	static void loadPropertiesFile() {
		
		try {
			String propURL = System.getenv("GREEN_PROPERTIES_URL");
			
			if ((propURL == null) || (propURL.equals(""))) {
				System.out.println("Not found 'GREEN_PROPERTIES_URL' env variable. Use default properties.");			
				return;
			}
			
			propURL = adjustURL(propURL);			
			
			URI uri = new URI(propURL);
			File propFile = new File(uri);
			
			if (!propFile.exists()) {
				System.out.println("Given 'GREEN_PROPERTIES_URL' doesn't exist. Use default properties.");
				return;
			}
			
			if (!propFile.canRead()) {
				System.out.println("No permission to read the given property file. Use default properties.");
				return;
			}
			
			prop.load(new FileInputStream(propFile));
			System.out.println("Green properties file was loaded from " + propURL);
			
		} catch (Exception ex) {
			System.out.println("Failed to load Green properties. Use default properties.");			
		}
		
	}
	
	/*
	 * Adjust the given <code>originalURL</code> to fit for the invocation of new URI(String);
	 */
	static String adjustURL(String originalURL) {
		
		// replace all backslashes with slash
		originalURL = originalURL.replaceAll("[\\\\]", "/");
		
		// regex : windows drive path ex) c:/test.properties
		String patternWin  = "^[a-zA-Z]:/.+"; 
		
		// regex : Unix path ex) /test.properties
		String patternUnix = "^/.+";
		
		if (originalURL.matches(patternWin)) {
			return "file:///" + originalURL;
		} else if (originalURL.matches(patternUnix)) {
			return "file://" + originalURL;
		}
		
		return originalURL;		
	}
	
	public static String getString(String key) {
		return getString(key, null);
	}

	public static String getString(String key, String defaultVal) {
		Object objVal = getValue(key);
		if (objVal == null) {
			return defaultVal;
		} else if (objVal instanceof String) {
			return (String)objVal;
		} else {
			return objVal.toString();
		}
	}
	
	public static double getDouble(String key) {
		return getDouble(key, 0.0);
	}

	public static double getDouble(String key, double defaultVal) {
		Object objVal = getValue(key);
		if (objVal == null) {
			return defaultVal;
		} else if (objVal instanceof Double) {
			return (Double)objVal;
		} else {
			return Double.parseDouble(objVal.toString());
		}		
	}
	
	public static int getInt(String key) {
		return getInt(key, 0);
	}
	
	public static int getInt(String key, int defaultVal) {
		Object objVal = getValue(key);
		if (objVal == null) {
			return defaultVal;
		} else if (objVal instanceof Integer) {
			return (Integer)objVal;
		} else {
			return Integer.parseInt(objVal.toString());
		}		
	}
	
	
	private static Object getValue(String key) {
		String strVal = prop.getProperty(key);
		if (strVal != null) return strVal;
		
		return defaults.get(key);
	}
	
}
