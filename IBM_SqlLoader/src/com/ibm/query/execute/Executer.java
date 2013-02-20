package com.ibm.query.execute;

import java.lang.reflect.InvocationTargetException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.query.connection.CacheManager;
import com.ibm.query.connection.ResourceManager;
import com.ibm.query.connection.TransactionManager;
import com.ibm.query.exception.ApplicationException;
import com.ibm.query.exception.QueryNotFoundException;
import com.ibm.query.exception.UnknownDataSourceException;
import com.ibm.query.execute.log.IHasQueryData;
import com.ibm.query.execute.manager.QueryManager;
import com.ibm.query.execute.manager.ReplaceStringManager;
import com.ibm.query.mapper.ICallableParamMapper;
import com.ibm.query.mapper.ICallbaleResultMapper;
import com.ibm.query.mapper.IParamMapper;
import com.ibm.query.mapper.IRowMapper;
import com.ibm.query.mapper.IStringMapper;
import com.ibm.query.mapper.IUpdateSetCondition;
import com.ibm.query.mapper.IUpdateWhereCondition;
import com.ibm.query.model.IHasQuery;
import com.ibm.query.rule.RuleContext;
import com.ibm.query.utils.QueryStringUtil;

/**
  * Session.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
  */
public class Executer {
	
	private Log logger = LogFactory.getLog(getClass());
	
	private CacheManager cacheManager = new CacheManager();
	
	public <O> int insert(String sqlId, IParamMapper<O> paramMapper, IStringMapper<O> sMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException {
		IHasQuery container = QueryManager.getInstance().getCachedData(sqlId);
		
		if(container ==null)
			throw new QueryNotFoundException("Please check sql in the xml : "+ sqlId);
		
		Connection conn = null;
		PreparedStatement ps = null;
		String query = null;
		try {
			conn = TransactionManager.getInstance().getConnection(container.getJdbc());
			
			query = container.getRawQuery();
			if(sMapper != null){
				query = sMapper.replaceAll(query, inputObject);
			}
			
			query = ReplaceStringManager.getInstance().replaceAll(query);
			
			ps = conn.prepareStatement(query);
			if(paramMapper != null){
				paramMapper.execute(ps, inputObject);	
			}
			if(ps instanceof IHasQueryData){
				((IHasQueryData)ps).setSqlId(sqlId);
			}
			
			
			int result = ps.executeUpdate();
			
			ResourceManager.commit(conn);
			return result;
			
		}catch (SQLException e) {
			logger.info(e.getMessage());
			ResourceManager.rollback(conn);
			throw e;
		}catch (Exception e) {
			logger.info(e.getMessage() + "\n" + inputObject + "\n"+ query);
			ResourceManager.rollback(conn);
			throw new ApplicationException(e);
		}finally{
			ResourceManager.disposeResource(container, conn, ps, null);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T, O, R> T selectObject(String sqlId, IParamMapper<O> paramMapper, IRowMapper<R> rowMapper, IStringMapper<O> sMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		IHasQuery container = QueryManager.getInstance().getCachedData(sqlId);
		
		if(container ==null)
			throw new QueryNotFoundException("Please check sql in the xml : "+ sqlId);
		
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String query = null;
		try {
			conn = TransactionManager.getInstance().getConnection(container.getJdbc());
			
			query = container.getRawQuery();
			if(sMapper != null){
				query = sMapper.replaceAll(query, inputObject);
			}
			
			query = ReplaceStringManager.getInstance().replaceAll(query);
			ps = conn.prepareStatement(query);
			
			if(paramMapper != null){
				paramMapper.execute(ps, inputObject);	
			}
			if(ps instanceof IHasQueryData){
				((IHasQueryData)ps).setSqlId(sqlId);
			}
			
			Object cacheData = cacheManager.get(sqlId, ps);
			if(cacheData != null){
				return (T) cacheData;
			}
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				R obj = rowMapper.mapRow(rs, 0);
				cacheManager.put(sqlId, ps, obj);
				
				return (T) obj;
			}
			
		}catch (SQLException e) {
			logger.info(e.getMessage());
			throw e;
		} catch (IllegalArgumentException e) {
			throw new SQLException(e);
		} catch (IllegalAccessException e) {
			throw new SQLException(e);
		} catch (InvocationTargetException e) {
			throw new SQLException(e);
		}catch (NullPointerException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}  catch (Exception e) {
			logger.info(e.getMessage() + "\n" + inputObject + "\n"+ query);
			throw new ApplicationException(e);
		} finally{
			ResourceManager.disposeResource(container, conn, ps, rs);
		}
		
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T, O, R> T dynamicSelectObject(String sqlId, IRowMapper<R> rowMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		IHasQuery container = QueryManager.getInstance().getCachedData(sqlId);
		
		if(container ==null)
			throw new QueryNotFoundException("Please check sql in the xml : "+ sqlId);
		
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String query = null;
		try {
			conn = TransactionManager.getInstance().getConnection(container.getJdbc());
			RuleContext context = DynamicHelper.getInstance().query(container, inputObject);
			
			query = context.getQuery();
			
			query = ReplaceStringManager.getInstance().replaceAll(query);
			ps = conn.prepareStatement(query);
			if(ps instanceof IHasQueryData){
				((IHasQueryData)ps).setSqlId(sqlId);
			}
			
			DynamicHelper.getInstance().execute(container, context, ps, inputObject);
			context.clear();
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				R obj = rowMapper.mapRow(rs, 0);
				cacheManager.put(sqlId, ps, obj);
				
				return (T) obj;
			}
			
		} catch (SQLException e) {
			logger.info(e.getMessage());
			throw e;
		} catch (IllegalArgumentException e) {
			throw new SQLException(e);
		} catch (IllegalAccessException e) {
			throw new SQLException(e);
		} catch (InvocationTargetException e) {
			throw new SQLException(e);
		} catch (NullPointerException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.info(e.getMessage() + "\n" + inputObject + "\n"+ query);
			throw new ApplicationException(e);
		} finally{
			ResourceManager.disposeResource(container, conn, ps, rs);
		}
		
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T, O, R> T dynamicSelectList(String sqlId, IRowMapper<R> rowMapper, O inputObject) throws SQLException, QueryNotFoundException, IllegalArgumentException, UnknownDataSourceException{
		IHasQuery container = QueryManager.getInstance().getCachedData(sqlId);

		List<Object> list = new ArrayList<Object>();
		
		if(container ==null)
			throw new QueryNotFoundException("Please check sql in the xml : "+ sqlId);
		
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String query = null;
		try {
			conn = TransactionManager.getInstance().getConnection(container.getJdbc());
			RuleContext context = DynamicHelper.getInstance().query(container, inputObject);
			
			query = context.getQuery();
			
			query = ReplaceStringManager.getInstance().replaceAll(query);
			ps = conn.prepareStatement(query);
			if(ps instanceof IHasQueryData){
				((IHasQueryData)ps).setSqlId(sqlId);
			}
			
			DynamicHelper.getInstance().execute(container, context, ps, inputObject);
			context.clear();
			
			rs = ps.executeQuery();
			
			int i=0;
			while(rs.next()) {
				R obj = rowMapper.mapRow(rs, i);
				list.add(obj);
				i++;
			}
		}catch (SQLException e) {
			logger.info(e.getMessage());
			throw e;
		} catch (IllegalAccessException e) {
			throw new SQLException(e);
		} catch (InvocationTargetException e) {
			throw new SQLException(e);
		}catch (Exception e) {
			logger.info(e.getMessage() + "\n" + inputObject + "\n"+ query);
			throw new ApplicationException(e);
		}finally{
			ResourceManager.disposeResource(container, conn, ps, rs);
		}
		
		return (T) list;
	}
	
	@SuppressWarnings("unchecked")
	public <T, O, R> T selectList(String sqlId, IParamMapper<O> paramMapper, IRowMapper<R> rowMapper, IStringMapper<O> sMapper, O inputObject) throws SQLException, QueryNotFoundException, IllegalArgumentException, UnknownDataSourceException{
		IHasQuery container = QueryManager.getInstance().getCachedData(sqlId);

		List<Object> list = new ArrayList<Object>();
		
		if(container ==null)
			throw new QueryNotFoundException("Please check sql in the xml : "+ sqlId);
		
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String query = null;
		try {
			conn = TransactionManager.getInstance().getConnection(container.getJdbc());
			
			query = container.getRawQuery();
			if(sMapper != null){
				query = sMapper.replaceAll(query, inputObject);
			}
			
			query = ReplaceStringManager.getInstance().replaceAll(query);
			ps = conn.prepareStatement(query);
			if(ps instanceof IHasQueryData){
				((IHasQueryData)ps).setSqlId(sqlId);
			}
			
			if(paramMapper != null){
				paramMapper.execute(ps, inputObject);	
			}
			
			rs = ps.executeQuery();
			
			int i=0;
			while(rs.next()) {
				R obj = rowMapper.mapRow(rs, i);
				list.add(obj);
				i++;
			}
		}catch (SQLException e) {
			logger.info(e.getMessage());
			throw e;
		} catch (IllegalAccessException e) {
			throw new SQLException(e);
		} catch (InvocationTargetException e) {
			throw new SQLException(e);
		}catch (Exception e) {
			logger.info(e.getMessage() + "\n" + inputObject + "\n"+ query);
			throw new ApplicationException(e);
		}finally{
			ResourceManager.disposeResource(container, conn, ps, rs);
		}
		
		return (T) list;
	}
	

	public <O> int update(String sqlId, IParamMapper<O> paramMapper, IStringMapper<O> sMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		IHasQuery container = QueryManager.getInstance().getCachedData(sqlId);
		
		if(container ==null)
			throw new QueryNotFoundException("Please check sql in the xml : "+ sqlId);
		
		Connection conn = null;
		PreparedStatement ps = null;
		String query = null;
		try {
			conn = TransactionManager.getInstance().getConnection(container.getJdbc());
			
			query = container.getRawQuery();
			if(sMapper != null){
				query = sMapper.replaceAll(query, inputObject);
			}
			
			query = ReplaceStringManager.getInstance().replaceAll(query);
			ps = conn.prepareStatement(query);
			if(paramMapper != null){
				paramMapper.execute(ps, inputObject);	
			}
			if(ps instanceof IHasQueryData){
				((IHasQueryData)ps).setSqlId(sqlId);
			}
			
			int result = ps.executeUpdate();
			
			ResourceManager.commit(conn);
			return result;
		}catch (SQLException e) {
			logger.info(e.getMessage());
			ResourceManager.rollback(conn);
			throw e;
		}catch (Exception e) {
			logger.info(e.getMessage() + "\n" + inputObject + "\n"+ query);
			ResourceManager.rollback(conn);
			throw new ApplicationException(e);
		}finally{
			ResourceManager.disposeResource(container, conn, ps, null);
		}
	}
	
	public <O> int delete(String sqlId, IParamMapper<O> paramMapper, IStringMapper<O> sMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		IHasQuery container = QueryManager.getInstance().getCachedData(sqlId);
		
		if(container ==null)
			throw new QueryNotFoundException("Please check sql in the xml : "+ sqlId);
		
		Connection conn = null;
		PreparedStatement ps = null;
		String query = null;
		try {
			conn = TransactionManager.getInstance().getConnection(container.getJdbc());
			query = container.getRawQuery();
			if(sMapper != null){
				query = sMapper.replaceAll(query, inputObject);
			}
			
			query = ReplaceStringManager.getInstance().replaceAll(query);
			ps = conn.prepareStatement(query);
			if(paramMapper != null){
				paramMapper.execute(ps, inputObject);	
			}
			
			if(ps instanceof IHasQueryData){
				((IHasQueryData)ps).setSqlId(sqlId);
			}
			
			int result = ps.executeUpdate();
			
			ResourceManager.commit(conn);
			return result;
		}catch (SQLException e) {
			logger.info(e.getMessage());
			ResourceManager.rollback(conn);
			throw e;
		}catch (Exception e) {
			logger.info(e.getMessage() + "\n" + inputObject + "\n"+ query);
			ResourceManager.rollback(conn);
			throw new ApplicationException(e);
		}finally{
			ResourceManager.disposeResource(container, conn, ps, null);
		}
		
	}

	public <O> int updateDynamic(String sqlId, List<IUpdateSetCondition<O>> setConditions, IUpdateWhereCondition<O> whereCondition, O inputObject) throws QueryNotFoundException, SQLException, UnknownDataSourceException {
		IHasQuery container = QueryManager.getInstance().getCachedData(sqlId);
		
		if(container ==null)
			throw new QueryNotFoundException("Please check sql in the xml : "+ sqlId);
		
		Connection conn = null;
		PreparedStatement ps = null;
		String query = null;
		try {
			conn = TransactionManager.getInstance().getConnection(container.getJdbc());
			
			query = container.getRawQuery();
			StringBuffer dynamicQuery = new StringBuffer();
			boolean isStarted = true;
			List<IUpdateSetCondition<O>> satisfiedList = new ArrayList<IUpdateSetCondition<O>>();
			for (IUpdateSetCondition<O> iCondition : setConditions) {
				if(iCondition.isSatisfied(inputObject)){
					satisfiedList.add(iCondition);
					
					if(isStarted){
						dynamicQuery.append(iCondition.getColumnName() + " = ? ");
						isStarted = false;
					}else{
						dynamicQuery.append(" ," +iCondition.getColumnName() + " = ? ");	
					}
				}
			}
			
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("dynamic", dynamicQuery.toString());
			
			query = QueryStringUtil.findAndReplace(query, paramMap);
			query = ReplaceStringManager.getInstance().replaceAll(query);
			
			ps = conn.prepareStatement(query);
			if(ps instanceof IHasQueryData){
				((IHasQueryData)ps).setSqlId(sqlId);
			}
			
			int index = 1;
			for (IUpdateSetCondition<O> iCondition : satisfiedList) {
				iCondition.execute(index++, ps, inputObject);	
			}
			
			whereCondition.execute(index, ps, inputObject);
			
			int result = ps.executeUpdate();
			ResourceManager.commit(conn);
			return result;
		}catch (SQLException e) {
			logger.info(e.getMessage());
			ResourceManager.rollback(conn);
			throw e;
		}catch (Exception e) {
			logger.info(e.getMessage() + "\n" + inputObject + "\n"+ query);
			ResourceManager.rollback(conn);
			throw new ApplicationException(e);
		}finally{
			ResourceManager.disposeResource(container, conn, ps, null);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T, O, R> T call(String sqlId, ICallableParamMapper<O> paramMapper, ICallbaleResultMapper<R> resultMapper, IStringMapper<O> sMapper, O inputObject) throws SQLException, QueryNotFoundException, UnknownDataSourceException{
		IHasQuery container = QueryManager.getInstance().getCachedData(sqlId);
		
		if(container ==null)
			throw new QueryNotFoundException("Please check sql in the xml : "+ sqlId);
		
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement  cs = null;
		String query = null;
		try {
			conn = TransactionManager.getInstance().getConnection(container.getJdbc());
			
			query = container.getRawQuery();
			if(sMapper != null){
				query = sMapper.replaceAll(query, inputObject);
			}
			
			query = ReplaceStringManager.getInstance().replaceAll(query);
			cs = conn.prepareCall(query);
			
			if(paramMapper != null){
				paramMapper.execute(cs, inputObject);	
			}
			if(cs instanceof IHasQueryData){
				((IHasQueryData)cs).setSqlId(sqlId);
			}
			
			Object cacheData = cacheManager.get(sqlId, cs);
			if(cacheData != null){
				return (T) cacheData;
			}
			
			cs.execute();
			
			R obj = resultMapper.mapRow(cs);
			cacheManager.put(sqlId, cs, obj);
			
			return (T) obj;
			
		}catch (SQLException e) {
			logger.info(e.getMessage());
			throw e;
		} catch (IllegalArgumentException e) {
			throw new SQLException(e);
		} catch (IllegalAccessException e) {
			throw new SQLException(e);
		} catch (InvocationTargetException e) {
			throw new SQLException(e);
		}catch (NullPointerException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}  catch (Exception e) {
			logger.info(e.getMessage() + "\n" + inputObject + "\n"+ query);
			throw new ApplicationException(e);
		} finally{
			ResourceManager.disposeResource(container, conn, cs, rs);
		}
	}
}
