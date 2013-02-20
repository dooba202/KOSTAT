package com.ibm.ccms.abstraction.jta;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import com.ibm.query.execute.jta.ItransactionalState;
import com.ibm.query.execute.proxy.ConnectionProxy;

public class SpringTransactionState implements ItransactionalState{
	
	private Log logger = LogFactory.getLog(getClass());

	public Connection getConnection(DataSource ds) throws SQLException {
		DataSource dataSource = null;
		
		if (ds instanceof TransactionAwareDataSourceProxy) {
			dataSource = ((TransactionAwareDataSourceProxy) ds).getTargetDataSource();
		}
		else {
			dataSource = ds;
		}
		
		Connection conn = DataSourceUtils.getConnection(dataSource);
		
		logger.debug("JDBC Connection : " + conn + ", dataSource : "+ dataSource);
		
		return new ConnectionProxy(conn);
	}

	@Override
	public void close(DataSource ds, Connection conn) {
//		DataSourceUtils.releaseConnection(conn, ds);
	}

	@Override
	public void commit(Connection conn) throws SQLException{
	}
	
	public void commit() throws SQLException {
	}

	@Override
	public void rollback(Connection conn) throws SQLException {
		
	}
	
	@Override
	public void close() {
		
	}

	@Override
	public void rollback() throws SQLException {
		
	}
}
