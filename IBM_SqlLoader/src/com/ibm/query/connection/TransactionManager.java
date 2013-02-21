package com.ibm.query.connection;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.query.exception.UnknownDataSourceException;
import com.ibm.query.execute.Jdbc;
import com.ibm.query.execute.jta.ItransactionalState;

/**
  * DataSourceManager.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
  */
public class TransactionManager {
	private static TransactionManager instance = new TransactionManager();
	
	private Log logger = LogFactory.getLog(getClass());
	
	
	public static synchronized TransactionManager getInstance() {
		if(instance == null){
			instance = new TransactionManager();
		}
		return instance;
	}
	
	public DataSource getDataSource(String dsName) throws UnknownDataSourceException{
		ItransactionalState transactionState = Jdbc.getInstance().getTransactionState();
		DataSource ds = transactionState.getDataSource(dsName);
		if(ds == null){
			throw new UnknownDataSourceException("Unable to locate DataSource " + dsName);
		}else{
			return ds;
		}
	}
	
	public Connection getConnection(String dsName) throws SQLException,UnknownDataSourceException{
		ItransactionalState transactionState = Jdbc.getInstance().getTransactionState();
		
		DataSource ds = getDataSource(dsName);
		logger.debug("DataSource : "+ ds);
		
		return transactionState.getConnection(ds);
	}
	
}
