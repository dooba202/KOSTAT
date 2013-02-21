package com.ibm.query.execute;

import java.sql.SQLException;
import java.util.List;

import com.ibm.query.exception.QueryNotFoundException;
import com.ibm.query.exception.UnknownDataSourceException;
import com.ibm.query.execute.jta.DefaultTransactionalState;
import com.ibm.query.execute.jta.ItransactionalState;
import com.ibm.query.execute.jta.NoneTransactionalState;
import com.ibm.query.mapper.ICallableParamMapper;
import com.ibm.query.mapper.ICallbaleResultMapper;
import com.ibm.query.mapper.IParamMapper;
import com.ibm.query.mapper.IRowMapper;
import com.ibm.query.mapper.IStringMapper;
import com.ibm.query.mapper.IUpdateSetCondition;
import com.ibm.query.mapper.IUpdateWhereCondition;

/**
  * Jdbc.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
  */
public class Jdbc {
	
	private static Jdbc instance = null;
	
	private ItransactionalState noneTransactionalState = new NoneTransactionalState();
	
	private ItransactionalState defaultTransactionalState = new DefaultTransactionalState();
	
	private Executer executer = null;
	
	protected ThreadLocal<ItransactionalState> localSession = new ThreadLocal<ItransactionalState>();
	
	private Jdbc(){
		executer = new Executer();
	}
	
	public static Jdbc getInstance() {
		synchronized (Jdbc.class) {
			if(instance == null){
				instance = new Jdbc();
			}
			
			return instance;	
		}
	}
	
	public void setConnectionManager(ItransactionalState connManager){
		localSession.set(connManager);
	}
	
	public ItransactionalState getTransactionState(){
		ItransactionalState state = localSession.get();
		if(state == null){
			return this.noneTransactionalState;
		}
		
		return state;
	}

	public Executer getQueryExecuter() throws SQLException {
		return executer;
	}
	
	public <O> int insert(String sqlId, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		return getQueryExecuter().insert(sqlId, null, null, inputObject);
	}
	
	public <O> int insert(String sqlId, IParamMapper<O> paramMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		return getQueryExecuter().insert(sqlId, paramMapper, null, inputObject);
	}
	
	public <O> int insert(String sqlId, IParamMapper<O> paramMapper, IStringMapper<O> sMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		return getQueryExecuter().insert(sqlId, paramMapper, sMapper, inputObject);
	}
	
	public <O> int update(String sqlId, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		return getQueryExecuter().update(sqlId, null, null, inputObject);
	}
	
	public <O> int update(String sqlId, IParamMapper<O> paramMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		return getQueryExecuter().update(sqlId, paramMapper, null, inputObject);
	}
	
	public <O> int updateDynamic(String sqlId, List<IUpdateSetCondition<O>> setConditions, IUpdateWhereCondition<O> whereCondition, O inputObject)  throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		return getQueryExecuter().updateDynamic(sqlId, setConditions, whereCondition, inputObject);
	}
	
	public <O> int update(String sqlId, IParamMapper<O> paramMapper, IStringMapper<O> sMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		return getQueryExecuter().update(sqlId, paramMapper, sMapper, inputObject);
	}
	
	public <T, O> T selectList(String sqlId, IRowMapper<O> rowMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		return getQueryExecuter().selectList(sqlId, null, rowMapper, null, inputObject);
	}
	
	public <T, O, R> T selectList(String sqlId, IRowMapper<R> rowMapper, IStringMapper<O> sMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		return getQueryExecuter().selectList(sqlId, null, rowMapper, sMapper, inputObject);
	}
	
	public <T, O, R> T selectList(String sqlId, IParamMapper<O> paramMapper, IRowMapper<R> rowMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		return getQueryExecuter().selectList(sqlId, paramMapper, rowMapper, null, inputObject);
	}
	
	public <T, O, R> T selectList(String sqlId, IParamMapper<O> paramMapper, IRowMapper<R> rowMapper, IStringMapper<O> sMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		return getQueryExecuter().selectList(sqlId, paramMapper, rowMapper, sMapper, inputObject);
	}
	
	public <T, O> T selectObject(String sqlId, IRowMapper<T> rowMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		return getQueryExecuter().selectObject(sqlId, null, rowMapper, null, inputObject);
	}
	
	public <T, O> T selectObject(String sqlId, IParamMapper<O> paramMapper, IRowMapper<T> rowMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		return getQueryExecuter().selectObject(sqlId, paramMapper, rowMapper, null, inputObject);
	}
	
	public <T, O> T selectObject(String sqlId, IRowMapper<T> rowMapper, IStringMapper<O> sMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		return getQueryExecuter().selectObject(sqlId, null, rowMapper, sMapper, inputObject);
	}
	
	public <T, O> T selectObject(String sqlId, IParamMapper<O> paramMapper, IRowMapper<T> rowMapper, IStringMapper<O> sMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		return getQueryExecuter().selectObject(sqlId, paramMapper, rowMapper, sMapper, inputObject);
	}
	

	public <T, O> T dynamicSelectObject(String sqlId, IRowMapper<T> rowMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		return getQueryExecuter().dynamicSelectObject(sqlId, rowMapper, inputObject);
	}
	
	public <T, O, R> T dynamicSelectList(String sqlId, IRowMapper<R> rowMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		return getQueryExecuter().dynamicSelectList(sqlId, rowMapper, inputObject);
	}
	
	public <O> Integer dynamicSelectInteger(String sqlId, IRowMapper<Integer> rowMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		Integer result = (Integer) getQueryExecuter().dynamicSelectObject(sqlId, rowMapper, inputObject); 
		return result == null ? 0 : result;
	}
	
	public <O> Long dynamicSelectLong(String sqlId, IRowMapper<Long> rowMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		Long result = (Long) getQueryExecuter().dynamicSelectObject(sqlId, rowMapper, inputObject);
		return result == null ? 0 : result;
	}
	

	public <O> Integer selectInteger(String sqlId, IRowMapper<Integer> rowMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		Integer result = (Integer) getQueryExecuter().selectObject(sqlId, null, rowMapper, null, inputObject); 
		return result == null ? 0 : result;
	}
	
	public <O> Integer selectInteger(String sqlId, IParamMapper<O> paramMapper, IRowMapper<Integer> rowMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		Integer result = (Integer) getQueryExecuter().selectObject(sqlId, paramMapper, rowMapper, null, inputObject);
		return result == null ? 0 : result;
	}
	
	public <O> Integer selectInteger(String sqlId, IRowMapper<Integer> rowMapper, IStringMapper<O> sMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		Integer result = (Integer) getQueryExecuter().selectObject(sqlId, null, rowMapper, sMapper, inputObject);
		return result == null ? 0 : result;
	}
	
	public <O> Integer selectInteger(String sqlId, IParamMapper<O> paramMapper, IRowMapper<Integer> rowMapper, IStringMapper<O> sMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		Integer result = (Integer) getQueryExecuter().selectObject(sqlId, paramMapper, rowMapper, sMapper, inputObject);
		return result == null ? 0 : result;
	}
	
	
	public <O> Long selectLong(String sqlId, IRowMapper<Long> rowMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		Long result = (Long) getQueryExecuter().selectObject(sqlId, null, rowMapper, null, inputObject);
		return result == null ? 0 : result;
	}
	
	public <O> Long selectLong(String sqlId, IParamMapper<O> paramMapper, IRowMapper<Long> rowMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		Long result = (Long) getQueryExecuter().selectObject(sqlId, paramMapper, rowMapper, null, inputObject);
		return result == null ? 0 : result;
	}
	
	public <O> Long selectLong(String sqlId, IRowMapper<Long> rowMapper, IStringMapper<O> sMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		Long result = (Long) getQueryExecuter().selectObject(sqlId, null, rowMapper, sMapper, inputObject);
		return result == null ? 0 : result;
	}
	
	public <O> Long selectLong(String sqlId, IParamMapper<O> paramMapper, IRowMapper<Long> rowMapper, IStringMapper<O> sMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		Long result = (Long) getQueryExecuter().selectObject(sqlId, paramMapper, rowMapper, sMapper, inputObject);
		return result == null ? 0 : result;
	}
	
	//Delete
	public <O> int delete(String sqlId, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		return getQueryExecuter().delete(sqlId, null, null, inputObject);
	}
	
	public <O> int delete(String sqlId, IParamMapper<O> paramMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		return getQueryExecuter().delete(sqlId, paramMapper, null, inputObject);
	}
	
	public <O> int delete(String sqlId, IParamMapper<O> paramMapper, IStringMapper<O> sMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		return getQueryExecuter().delete(sqlId, paramMapper, sMapper, inputObject);
	}
	
	
	public <T, O> T call(String sqlId, ICallbaleResultMapper<T> resultMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		return getQueryExecuter().call(sqlId, null, resultMapper, null, inputObject);
	}
	
	public <T, O> T call(String sqlId, ICallableParamMapper<O> paramMapper, ICallbaleResultMapper<T> resultMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		return getQueryExecuter().call(sqlId, paramMapper, resultMapper, null, inputObject);
	}
	
	public <T, O> T call(String sqlId, ICallbaleResultMapper<T> resultMapper, IStringMapper<O> sMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		return getQueryExecuter().call(sqlId, null, resultMapper, sMapper, inputObject);
	}
	
	public <T, O> T call(String sqlId, ICallableParamMapper<O> paramMapper, ICallbaleResultMapper<T> resultMapper, IStringMapper<O> sMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		return getQueryExecuter().call(sqlId, paramMapper, resultMapper, sMapper, inputObject);
	}
	
	
	public void commit() throws SQLException {
		getTransactionState().commit();
	}

	public void rollback() throws SQLException {
		getTransactionState().rollback();
	}

	public void close() {
		getTransactionState().close();
		localSession.remove();
	}

	public void begin() {
		setConnectionManager(this.defaultTransactionalState);
	}

	



	
}
