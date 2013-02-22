package com.ibm.green.kostat.dao.sqlloader;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.green.exception.ErrorCode;
import com.ibm.green.exception.GreenRuntimeException;
import com.ibm.green.kostat.dao.IndustryCodeDAO;
import com.ibm.green.kostat.dto.IndustryCodeDTO;
import com.ibm.green.kostat.enums.IndustryCodeType;
import com.ibm.query.execute.Jdbc;
import com.ibm.query.mapper.IRowMapper;

public class SqlLoaderIndustryCodeDAO implements IndustryCodeDAO {

	// override logger to use in static method.
	protected static Log logger = LogFactory.getLog(SqlLoaderJisuDAO.class);
	
	// jdbc instance
	protected Jdbc jdbc = Jdbc.getInstance();
	
	// SqlId prefix String. 
	String sqlIdPrefix = "industrycode";
	
	IRowMapper<IndustryCodeDTO> sanIdRowMapper = new IRowMapper<IndustryCodeDTO>() {

		@Override
		public IndustryCodeDTO mapRow(ResultSet resultSet, int row)
				throws SQLException, IllegalArgumentException,	IllegalAccessException, InvocationTargetException {

			IndustryCodeDTO dto = new IndustryCodeDTO();
			
			dto.setId(resultSet.getString("SANID"));
			dto.setName(resultSet.getString("SANNAME"));
			
			return dto;
		}
		
	};
	
	IRowMapper<IndustryCodeDTO> pumIdRowMapper = new IRowMapper<IndustryCodeDTO>() {

		@Override
		public IndustryCodeDTO mapRow(ResultSet resultSet, int row)
				throws SQLException, IllegalArgumentException,	IllegalAccessException, InvocationTargetException {

			IndustryCodeDTO dto = new IndustryCodeDTO();
	
			dto.setId(resultSet.getString("PUMID"));
			dto.setName(resultSet.getString("PUMNAME"));
			dto.setParent(resultSet.getString("SANID_TOP"));
						
			return dto;
		}
		
	};
	
	IRowMapper<IndustryCodeDTO> saupIdRowMapper = new IRowMapper<IndustryCodeDTO>() {

		@Override
		public IndustryCodeDTO mapRow(ResultSet resultSet, int row)
				throws SQLException, IllegalArgumentException,	IllegalAccessException, InvocationTargetException {

			IndustryCodeDTO dto = new IndustryCodeDTO();
			
			dto.setId(resultSet.getString("SAUPID"));
			dto.setName(resultSet.getString("SAUPNAME"));
			dto.setParent(resultSet.getString("PUMID"));			
			
			return dto;
		}
		
	};
	
	@Override
	public List<IndustryCodeDTO> getCodeList(IndustryCodeType codeType) {

		logger.debug("" + this.getClass() + ",s getCodeList(IndustryCodeType codeType) was called.");
		
		String queryName = "";
		IRowMapper<IndustryCodeDTO> rowMapper = null;
	
		try {
			switch (codeType){
			case SanId:
				rowMapper = sanIdRowMapper;
				queryName = "getCodeListForSanId";
				break;
			case PumId:
				rowMapper = pumIdRowMapper;
				queryName = "getCodeListForPumId";
				break;
			case SaupId:
				rowMapper = saupIdRowMapper;
				queryName = "getCodeListForSaupId";
				break;
			}
			
			
			List<IndustryCodeDTO> list = jdbc.selectList(sqlIdPrefix + "." + queryName, rowMapper, null);
	
			return list;
			
		} catch (Exception ex) {
			logger.error("Exception occurred in getJisuListWithIndustryCode(String sanId, String pumId, String fromYYYYMM, String toYYYYMM)  of " + this.getClass() , ex);
			throw new GreenRuntimeException(ErrorCode.DBIO_UNKNOWN, ex);
		}

	}

}
