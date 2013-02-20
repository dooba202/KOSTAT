package com.ibm.green.kostat.dao.sqlloader;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibm.green.exception.ErrorCode;
import com.ibm.green.exception.GreenRuntimeException;
import com.ibm.green.kostat.dao.SysInfoDAO;
import com.ibm.green.kostat.dto.SysInfoDTO;
import com.ibm.query.mapper.IParamMapper;
import com.ibm.query.mapper.IRowMapper;

/**
 * Sql Loader based implementation of <code>SysInfoDAO</code>
 * 
 */
public class SqlLoaderSysInfoDAO extends GenericDAOSqlLoaderImpl<SysInfoDTO, String> implements SysInfoDAO {
	
	IRowMapper<SysInfoDTO> totalRowMapper = new IRowMapper<SysInfoDTO>() {

		@Override
		public SysInfoDTO mapRow(ResultSet resultSet, int row) throws SQLException, IllegalArgumentException,
				IllegalAccessException, InvocationTargetException {

			SysInfoDTO sysInfo = new SysInfoDTO();
			sysInfo.setInfoKey(resultSet.getString("INFO_KEY"));
			sysInfo.setInfoValue(resultSet.getString("INFO_VALUE"));
			
			return sysInfo;
		}
	};

	public SqlLoaderSysInfoDAO() {
		super("sysinfo");
	}

	@Override
	protected IParamMapper<SysInfoDTO> getDTOParamMapper() {
		
		throw new GreenRuntimeException(ErrorCode.COMMON_NOT_ALLOWED);
	}

	@Override
	protected IRowMapper<SysInfoDTO> getDTORowMapper() {
		
		return totalRowMapper;
	}

	@Override
	protected String getPrimaryKey(SysInfoDTO value) {
		
		return value.getInfoKey();
	}

	@Override
	public Map<String, String> retrieveAllSysInfo() {
		
		HashMap<String, String> infoMap = new HashMap<String, String>();
		
		List<SysInfoDTO> list = this.find();
		
		if (list == null) return null;
		
		for (SysInfoDTO sysInfo : list) {
			infoMap.put(sysInfo.getInfoKey(), sysInfo.getInfoValue());
		}

		return infoMap;
	}

}
