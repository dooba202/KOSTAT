package com.ibm.green.kostat.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.ibm.green.kostat.boot.KoStatLoader;

/**
 * The implementation class of <code>ServletContextListener</code>. 
 */
@WebListener
public class KoStatContextListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		KoStatLoader.init();
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		KoStatLoader.destroy();		
	}

}
