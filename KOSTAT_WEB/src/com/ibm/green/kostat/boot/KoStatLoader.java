package com.ibm.green.kostat.boot;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.green.config.GreenConfig;
import com.ibm.query.init.SqlLoader;

/**
 * The class for loading system resources of KoStat system.
 * <br>
 * It will execute the procedure that is required to initialize and fianlize system.
 * The methods of this class will be called from the proper place where the target system will provide.
 * <br>
 * For example, <code>ServletContextListener</code> could be the good place to call these methods.
 */
public class KoStatLoader {

	protected static Log logger = LogFactory.getLog(KoStatLoader.class);
	
	private static boolean bInit = false;
	
	/**
	 * Initialize KoStat system.
	 */
	public synchronized static void init() {
		
		if (bInit) return; // already initialized
		
		try {
			
			// trigger to load GreenConfig
			GreenConfig.getString("");
			logger.debug("GreenConfig was loaded.");
			
			// initialize SqlLoader
			SqlLoader.init("sqlFiles.xml");
			logger.debug("IBM SqlLoader was loaded.");
			
			
			bInit = true;
			logger.info("--------- KoStat system was successfully initialized.---------");
		} catch (Exception ex) {
			logger.error("Failed to initialize.", ex);
			ex.printStackTrace(); // it's critical. print it once again
		} 
		
	}
	
	/**
	 * Deinitialize KoStat system.
	 */
	public synchronized static void destroy() {
		
		if (!bInit) return; // not yet initialized
		
		try {
			
			bInit = false;
			logger.info("--------- KoStat system was successfully destroyed.---------");			
		} catch (Exception ex) {
			logger.error("Failed to destroy.", ex);	
		}
		
	}
	
}
