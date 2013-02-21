package com.ibm.green.kostat.boot;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.green.exception.ErrorCode;
import com.ibm.green.exception.GreenRuntimeException;
import com.ibm.green.kostat.dao.SysInfoDAO;
import com.ibm.green.kostat.daofactory.DAOFactory;

/**
 * KoStat System Information Cache
 * 
 * <p> The information is loaded from the database once and will be used until <code>reloadCache()</code>
 * will be called. 
 */
public class KoStatSysInfo {
	
	private Log logger = LogFactory.getLog(this.getClass());
	
	private static volatile  KoStatSysInfo instance;
	
	private volatile Map<String, String> infoMap;
	
	private KoStatSysInfo() {
		loadSysInfo();
	}

	/**
	 * Retrieve the singleton reference of this class
	 * @return
	 */
	public static KoStatSysInfo getInst() {
		
		if (instance == null) {
			synchronized (KoStatSysInfo.class) {
				
				if (instance == null) {
					instance = new KoStatSysInfo();
				}
			}
		}
		
		return instance;
	}
	
	/**
	 * Retrieve the system information value for the given key.
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {

		return this.infoMap.get(key);
	}
	
	/**
	 * Retrieve the system information value for the given key.
	 * 
	 * <p> Unlike <code>get(String key)</code>, it will throw <code>GreenRuntimeException</code>
	 * if not found value.
	 * 
	 * @param key
	 * @return
	 * @throws GreenRuntimeException if not found value.
	 */
	public String get(KEY key) {
		
		String value = this.get(key.keyName);
		if (value != null) return value;
		
		throw new GreenRuntimeException(ErrorCode.COMMON_UNKNOWN, "Not found known system information - " + key);
	}
	
	/**
	 * Reload system information from database.
	 * 
	 * <p> This method will be called just for the purpose of administration.
	 */
	public void reloadCache() {
		
		loadSysInfo();
	}
	
	/**
	 * Return a <code>Set</code> of the keys that system information has
	 * 
	 * @return a set of the keys in the system information
	 */
	public Set<String> keySet() {
		
		return this.keySet();
	}	
	
	
	/*
	 * load system information from database
	 */
	private void loadSysInfo() {
		Map<String, String> map = new HashMap<String, String>();
		
		DAOFactory daoFactory = null;
		try {
			daoFactory = DAOFactory.getDAOFactory(DAOFactory.SQLLOADER_DAO);
			daoFactory.beginTransaction();

			SysInfoDAO dao = daoFactory.getSysInfoDAO();

			map = dao.retrieveAllSysInfo();
			
			if ((map == null) || (map.isEmpty())) {
				logger.fatal("Failed to load SysInfo. SysInfo table might be empty.");
				throw new GreenRuntimeException(ErrorCode.COMMON_UNKNOWN);
			}
	
			daoFactory.commitTransaction();
			
			// replace newly loaded map with old one.
			this.infoMap = map; 
			
		} catch (Exception ex) {
			logger.fatal("Failed to load SysInfo.", ex);
			if (daoFactory != null) daoFactory.rollbackTransaction();
			throw new GreenRuntimeException(ErrorCode.COMMON_UNKNOWN, ex);
		} finally {
			if (daoFactory != null) daoFactory.endTransaction();
		}
	}

	/**
	 * Reserved KEYs for SysInfo
	 *
	 */
	public static enum KEY {
		
		URL_LOGIN("URL_LOGIN"),
		URL_MAIN("URL_MAIN"),
		SESSION_TIMEOUT("SESSION_TIMEOUT"),
		REST_SERVLETPATH("REST_SERVLETPATH"),
		AUTH_FORMPATH("AUTH_FORMPATH"),
		AUTH_REDIRECTPATH("AUTH_REDIRECTPATH");
		
		String keyName;
		
		private KEY(String keyName) {
			this.keyName = keyName;
		}
	}
	
}
