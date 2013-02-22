package com.ibm.green.kostat.dao.sqlloader;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.green.exception.ErrorCode;
import com.ibm.green.exception.GreenRuntimeException;
import com.ibm.green.kostat.dao.JisuDAO;
import com.ibm.green.kostat.dto.JisuDTO;
import com.ibm.green.kostat.enums.JisuType;
import com.ibm.query.execute.Jdbc;
import com.ibm.query.mapper.IParamMapper;
import com.ibm.query.mapper.IRowMapper;

public class SqlLoaderJisuDAO implements JisuDAO {
	
	// override logger to use in static method.
	protected static Log logger = LogFactory.getLog(SqlLoaderJisuDAO.class);
	
	// jdbc instance
	protected Jdbc jdbc = Jdbc.getInstance();
	
	// SqlId prefix String. 
	String sqlIdPrefix = "jisu";
	
	IRowMapper<JisuDTO> jisuRowMapper = new IRowMapper<JisuDTO>() {

		@Override
		public JisuDTO mapRow(ResultSet resultSet, int row)
				throws SQLException, IllegalArgumentException,	IllegalAccessException, InvocationTargetException {

			JisuDTO dto = new JisuDTO();
			
			dto.setSanId(resultSet.getString("SANID"));
			dto.setPumId(resultSet.getString("PUMID"));
			dto.setJisuType(resultSet.getString("JISU_GU"));
			dto.setJisuName(resultSet.getString("JISU_GU_NM"));
			dto.setYyyymm(resultSet.getString("JOSA_YM"));
			dto.setSecTime(resultSet.getLong("SEC"));
			dto.setValue(resultSet.getDouble("JISU"));
			
			return dto;
		}
		
	};
	
	@Override
	public List<JisuDTO> getJisuListWithIndustryCode(JisuType jisuType, String sanId, String pumId, String fromYYYYMM, String toYYYYMM) {
		logger.debug("" + this.getClass() + ",s getJisuListWithIndustryCode(String sanId, String pumId, String fromYYYYMM, String toYYYYMM) was called.");
		
		IParamMapper<Object[]> pMapper = new IParamMapper<Object[]>() {
			@Override
			public void execute(PreparedStatement ps, Object[] params) throws SQLException {
				ps.setString(1, (String)params[0]);
				ps.setString(2, (String)params[1]);
				ps.setString(3, (String)params[2]);
				ps.setString(4, (String)params[3]);
			}
		};
		
		try {
			
			Object[] params = new Object[4];
			params[0] = sanId;
			params[1] = pumId;
			params[2] = fromYYYYMM;
			params[3] = toYYYYMM;
			
			String queryName = "";
			switch (jisuType) {
			case jisu:
				queryName = "getJisuListWithIndustryCode";
				break;
			case jisuLM: // 전월비
				queryName = "getJisuLMListWithIndustryCode";
				break;
			case jisuSM: // 전년동월비
				queryName = "getJisuSMListWithIndustryCode";
				break;
			}
			
			List<JisuDTO> list = jdbc.selectList(sqlIdPrefix + "." + queryName, pMapper, jisuRowMapper, params);
	
			return list;
			
		} catch (Exception ex) {
			logger.error("Exception occurred in getJisuListWithIndustryCode(String sanId, String pumId, String fromYYYYMM, String toYYYYMM)  of " + this.getClass() , ex);
			throw new GreenRuntimeException(ErrorCode.DBIO_UNKNOWN, ex);
		}
	}

}
