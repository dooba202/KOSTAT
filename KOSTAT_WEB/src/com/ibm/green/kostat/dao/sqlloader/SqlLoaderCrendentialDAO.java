package com.ibm.green.kostat.dao.sqlloader;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibm.green.kostat.dao.CredentialDAO;
import com.ibm.green.kostat.dto.CredentialDTO;
import com.ibm.query.mapper.IParamMapper;
import com.ibm.query.mapper.IRowMapper;

/**
 * <code>CredentialDAO</code> implementation based on Sql Loader 
 */
public class SqlLoaderCrendentialDAO extends GenericDAOSqlLoaderImpl<CredentialDTO, String> implements CredentialDAO {
	
	IRowMapper<CredentialDTO>  totalRawMapper = new IRowMapper<CredentialDTO>() {

		@Override
		public CredentialDTO mapRow(ResultSet resultSet, int row) throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {

			CredentialDTO creDTO = new CredentialDTO();
			
			creDTO.setUserID(resultSet.getString("USER_ID"));
			creDTO.setPassword(resultSet.getString("PASSWORD"));
			creDTO.setSalt(resultSet.getString("SALT"));
			return creDTO;
		}
	};	
	
	IParamMapper<CredentialDTO> totalParamMapper = new IParamMapper<CredentialDTO>() {

		@Override
		public void execute(PreparedStatement ps, CredentialDTO creDTO) throws SQLException {
			// Notice : password, salt and then userid. (sequence is important)
			ps.setString(1, creDTO.getPassword());
			ps.setString(2, creDTO.getSalt());
			ps.setString(3, creDTO.getUserID());
		}
	};
	
	
	public SqlLoaderCrendentialDAO() {
		super("credential");
	}

	@Override
	protected IParamMapper<CredentialDTO> getDTOParamMapper() {

		return totalParamMapper;
	}

	@Override
	protected IRowMapper<CredentialDTO> getDTORowMapper() {
		
		return totalRawMapper;
	}

	@Override
	protected String getPrimaryKey(CredentialDTO value) {

		return value.getUserID();
	}


}
