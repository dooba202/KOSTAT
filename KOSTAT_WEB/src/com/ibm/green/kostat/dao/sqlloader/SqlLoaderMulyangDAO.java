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
import com.ibm.green.kostat.dao.MulyangDAO;
import com.ibm.green.kostat.dto.MulyangDTO;
import com.ibm.green.kostat.enums.MulyangType;
import com.ibm.query.execute.Jdbc;
import com.ibm.query.mapper.IParamMapper;
import com.ibm.query.mapper.IRowMapper;

public class SqlLoaderMulyangDAO implements MulyangDAO {

	protected static Log logger = LogFactory.getLog(SqlLoaderMulyangDAO.class);
	
	// jdbc instance
	protected Jdbc jdbc = Jdbc.getInstance();
	
	// SqlId prefix String. 
	String sqlIdPrefix = "mulyang";
	
	IRowMapper<MulyangDTO> mulyangRowMapper = new IRowMapper<MulyangDTO>() {

		@Override
		public MulyangDTO mapRow(ResultSet resultSet, int row)
				throws SQLException, IllegalArgumentException,	IllegalAccessException, InvocationTargetException {

			MulyangDTO dto = new MulyangDTO();
			
			dto.setPumId(resultSet.getString("PUMID"));
			dto.setSaupId(resultSet.getString("SAUPID"));
			dto.setJisuName(resultSet.getString("JISU_GU_NM"));
			dto.setYyyymm(resultSet.getString("JOSA_YM"));
			dto.setSecTime(resultSet.getLong("SEC"));
			dto.setValue(resultSet.getDouble("MULYANG"));
			
			return dto;
		}
		
	};

	@Override
	public List<MulyangDTO> getMulyangListWithIndustryCode(MulyangType mulyangType, String pumId, String saupId, String fromYYYYMM, String toYYYYMM) {
		logger.debug("" + this.getClass() + ",s getJisuListWithIndustryCode(String sanId, String pumId, String fromYYYYMM, String toYYYYMM) was called.");
		
		IParamMapper<String[]> pMapper = new IParamMapper<String[]>() {
			@Override
			public void execute(PreparedStatement ps, String[] params) throws SQLException {
				
				for (int i = 0; i < params.length; i++) {
					ps.setString(i + 1, params[i]);
				}
			}
		};
		
		try {
			
			String[] params = null;
			
			String queryName = "";
			
			switch (mulyangType) {
			case mulyang:
				if ((saupId == null) || (saupId.equals("0"))) {
					queryName = "getMulyangListWithPumId";
					params = new String[3];
					params[0] = pumId;
					params[1] = fromYYYYMM;
					params[2] = toYYYYMM;					
				}
				else {
					queryName = "getMulyangListWithSaupId";
					params = new String[4];
					params[0] = pumId;
					params[1] = saupId;
					params[2] = fromYYYYMM;
					params[3] = toYYYYMM;					
				}
				break;
			case mulyangLM:
				queryName = "getMulyangLMList";
				params = new String[5];
				params[0] = pumId;
				params[1] = saupId;
				params[2] = saupId;
				params[3] = fromYYYYMM;
				params[4] = toYYYYMM;					
				break;
			case mulyangSM:
				queryName = "getMulyangSMList";
				params = new String[5];
				params[0] = pumId;
				params[1] = saupId;
				params[2] = saupId;
				params[3] = fromYYYYMM;
				params[4] = toYYYYMM;					
				break;
			}
			
			List<MulyangDTO> list = jdbc.selectList(sqlIdPrefix + "." + queryName, pMapper, mulyangRowMapper, params);
	
			return list;
			
		} catch (Exception ex) {
			logger.error("Exception occurred in getJisuListWithIndustryCode(String sanId, String pumId, String fromYYYYMM, String toYYYYMM)  of " + this.getClass() , ex);
			throw new GreenRuntimeException(ErrorCode.DBIO_UNKNOWN, ex);
		}
	}
}
