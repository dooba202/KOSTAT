package com.ibm.green.kostat.dao.sqlloader;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.green.exception.ErrorCode;
import com.ibm.green.exception.GreenRuntimeException;
import com.ibm.green.kostat.dao.GenericDAO;
import com.ibm.green.kostat.dao.util.ThreeValuesPK;
import com.ibm.green.kostat.dao.util.TwoValuesPK;
import com.ibm.green.kostat.exception.DataCreationFailureException;
import com.ibm.green.kostat.exception.DataNotFoundException;
import com.ibm.query.execute.Jdbc;
import com.ibm.query.mapper.IParamMapper;
import com.ibm.query.mapper.IRowMapper;

/**
 * Super class for implementation classes of <tt>GenericDAO</tt> with SqlLoader.
 * 
 * <p>It's abstract class and will be inherited by concrete SqlLoader DAO implementation classes.
 * This class already implemented 5 basic CRUD methods that are defined in <tt>GenericDAO</tt>.
 * To use pre-implemented function properly, subclasses must use the reserved sql id for each method.
 * The reserved ids are like below.
 * 
 * <pre>
 * - T find(K id)         : $prefix + ".select"
 * - List<T> find()       : $prefix + ".selectAll"
 * - K save(T value)      : $prefix + ".insert"
 * - void update(T value) : $prefix + ".update"
 * - void delete(T value) : $prefix + ".delete"
 * </pre>
 * 
 * @param <T> Persistent object type
 * @param <K> Primary key type
 */
public abstract class GenericDAOSqlLoaderImpl<T, K extends Serializable> implements GenericDAO<T, K> {

	protected Log logger = LogFactory.getLog(this.getClass());
	
	// jdbc instance
	protected Jdbc jdbc = Jdbc.getInstance();
	
	// SqlId prefix String. 
	// Only applied for pre-implemented functions
	String sqlIdPrefix;
	
	// predefined sql ids
	final static String FIND_SQLID = ".select";
	final static String FINDALL_SQLID = ".selectAll";
	final static String SAVE_SQLID = ".insert";
	final static String UPDATE_SQLID = ".update";
	final static String DELETE_SQLID = ".delete";
	
	// default IParamMapper for primary key
	private IParamMapper<K> defaultPKParamMapper = new IParamMapper<K>() {
		public void execute(PreparedStatement ps, K pkObject) throws SQLException {
			ps.setString(1, pkObject.toString());
		};
	};

	protected static final IParamMapper<TwoValuesPK<String, String>> twoPKParamMapper = new IParamMapper<TwoValuesPK<String, String>>() {
		@Override
		public void execute(PreparedStatement ps, TwoValuesPK<String, String> inputObject) throws SQLException {
			ps.setString(1, inputObject.getPKPartOne()); 
			ps.setString(2, inputObject.getPKPartTwo()); 
		}
	};
	
	protected static final IParamMapper<ThreeValuesPK<String, String, String>> threePKParamMapper = new IParamMapper<ThreeValuesPK<String,String,String>>() {
		@Override
		public void execute(PreparedStatement ps, ThreeValuesPK<String, String, String> inputObject) throws SQLException {
			ps.setString(1, inputObject.getPKPartOne()); 
			ps.setString(2, inputObject.getPKPartTwo()); 
			ps.setString(3, inputObject.getPKPartThree()); 
		}
	};

	
	/*
	 * Constructor
	 */
	protected GenericDAOSqlLoaderImpl(String sqlIdPrefix) {
		this.sqlIdPrefix = sqlIdPrefix;
	}
	
	/*
	 * Return IParamMapper<T> object for DTO object -  T
	 * This must be implemented by subclass 
	 */
	protected abstract IParamMapper<T> getDTOParamMapper();
	
	/*
	 * Return IRowMapper<T> object for DTO object - T
	 * This must be implemented by subclass
	 */
	protected abstract IRowMapper<T> getDTORowMapper();
	
	/*
	 * Return IParamMapper<K> object for Primary Key object - K
	 * Subclass can override this method if needed
	 */
	protected IParamMapper<K> getPKParamMapper() {
		return defaultPKParamMapper;
	}
	
	/*
	 * Return the primary key K of the given value T
	 * This must be implemented by subclass
	 */
	protected abstract K getPrimaryKey(T value);

	/*
	 * Common implementation of T find(K).
	 * @see com.ibm.green.kostat.dao.GenericDAO#find(java.io.Serializable)
	 */
	@Override
	public T find(K id) {
		logger.debug("" + this.getClass() + ",s find(K id) was called.");
		
		try {
			T dto = jdbc.selectObject(sqlIdPrefix + FIND_SQLID, getPKParamMapper(), getDTORowMapper(), id);
			return dto; 
		} catch (Exception ex) {
			logger.error("Exception occurred in find(K id) of " + this.getClass() + " + with pk = " + id, ex);
			throw new GreenRuntimeException(ErrorCode.DBIO_UNKNOWN, ex);
		}
	}

	/*
	 * Common implementation of List<T> find()
	 * @see com.ibm.green.kostat.dao.GenericDAO#find()
	 */
	@Override
	public List<T> find() {
		logger.debug("" + this.getClass() + ",s find() was called.");
		
		try {
			List<T> list = jdbc.selectList(sqlIdPrefix + FINDALL_SQLID, getDTORowMapper(), null);
			return list; 
		} catch (Exception ex) {
			logger.error("Exception occurred in find() of " + this.getClass(), ex);
			throw new GreenRuntimeException(ErrorCode.DBIO_UNKNOWN, ex);
		}
	}

	/*
	 * Common implementation of save(T value)
	 * @see com.ibm.green.kostat.dao.GenericDAO#save(java.lang.Object)
	 */
	@Override
	public K save(T value) {
		logger.debug("" + this.getClass() + ",s save(T value) was called.");
		
		try {
			int nInsert = jdbc.insert(sqlIdPrefix + SAVE_SQLID, getDTOParamMapper(), value);
			
			if (nInsert == 0) {
				logger.error("Failed to insert in save(T value) of " + this.getClass() + " with pk = " + getPrimaryKey(value));
				throw new DataCreationFailureException();
			}
			
			return getPrimaryKey(value);
		} catch (DataCreationFailureException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occurred in save(T value) of " + this.getClass() + " with pk = " + getPrimaryKey(value), ex);
			throw new GreenRuntimeException(ErrorCode.DBIO_UNKNOWN, ex);
		}
	}

	/*
	 * Common implementation of update(T value)
	 * @see com.ibm.green.kostat.dao.GenericDAO#update(java.lang.Object)
	 */
	@Override
	public void update(T value) {
		logger.debug("" + this.getClass() + ",s update(T value) was called with pk = " + getPrimaryKey(value));
		
		try {
			int nUpdate = jdbc.update(sqlIdPrefix + UPDATE_SQLID, getDTOParamMapper(), value);
			
			if (nUpdate == 0) {
				logger.error("Failed to update in update(T value) of " + this.getClass() + " with pk = " + getPrimaryKey(value));
				throw new DataNotFoundException();
			}
		} catch (DataNotFoundException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occurred in update(T value) of " + this.getClass() + " with pk = " + getPrimaryKey(value), ex);
			throw new GreenRuntimeException(ErrorCode.DBIO_UNKNOWN, ex);
		}
	}

	/*
	 * Common implementation of delete(T value)
	 * @see com.ibm.green.kostat.dao.GenericDAO#delete(java.lang.Object)
	 */
	@Override
	public void delete(T value) {
		logger.debug("" + this.getClass() + ",s delete(T value) was called with pk = " + getPrimaryKey(value));
		
		try {
			int nDelete = jdbc.delete(sqlIdPrefix + DELETE_SQLID, getPKParamMapper(), getPrimaryKey(value));
			
			if (nDelete == 0) {
				logger.error("Failed to update in delete(T value) of " + this.getClass() + " with pk = " + getPrimaryKey(value));
				throw new DataNotFoundException();
			}
		} catch (DataNotFoundException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occurred in delete(T value) of " + this.getClass() + " with pk = " + getPrimaryKey(value), ex);
			throw new GreenRuntimeException(ErrorCode.DBIO_UNKNOWN, ex);
		}
	}

}
