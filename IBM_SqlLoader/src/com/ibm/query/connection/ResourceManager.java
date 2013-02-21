package com.ibm.query.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibm.query.execute.Jdbc;
import com.ibm.query.execute.jta.ItransactionalState;
import com.ibm.query.model.IHasQuery;

/**
  * ConnectionManager.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
  */
public class ResourceManager {
	
	public static void commit(Connection conn) throws SQLException {
		ItransactionalState transactionalState = Jdbc.getInstance().getTransactionState();
		transactionalState.commit(conn);
	}

	public static void rollback(Connection conn) throws SQLException{
		ItransactionalState transactionalState = Jdbc.getInstance().getTransactionState();
		transactionalState.rollback(conn);
	}
	

	public static void disposeResource(IHasQuery container, Connection conn, PreparedStatement ps, ResultSet rs) {
		ItransactionalState transactionalState = Jdbc.getInstance().getTransactionState();
		disposeResource(ps, rs);	
		transactionalState.close(conn);
	}

	private static void disposeResource(PreparedStatement ps, ResultSet rs) {
		try {
			if(rs !=  null){
				rs.close();
			}
		} catch (Exception ignore) {
		}
		
		try {
			if(ps !=  null){
				ps.close();
			}
		} catch (Exception ignore) {
		}
	}
	
}
