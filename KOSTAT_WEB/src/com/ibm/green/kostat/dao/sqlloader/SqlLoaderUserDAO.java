package com.ibm.green.kostat.dao.sqlloader;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibm.green.kostat.dao.UserDAO;
import com.ibm.green.kostat.dto.UserDTO;
import com.ibm.green.kostat.enums.UserType;
import com.ibm.query.mapper.IParamMapper;
import com.ibm.query.mapper.IRowMapper;

public class SqlLoaderUserDAO extends GenericDAOSqlLoaderImpl<UserDTO, String> implements UserDAO {

	IRowMapper<UserDTO>  totalRawMapper = new IRowMapper<UserDTO>() {

		@Override
		public UserDTO mapRow(ResultSet resultSet, int row) throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {

			UserDTO usrDTO = new UserDTO();
			
			usrDTO.setUserID(resultSet.getString("USER_ID"));
			usrDTO.setUserType(UserType.valueOf(resultSet.getString("USER_TYPE")));
			return usrDTO;
		}
	};	
	
	IParamMapper<UserDTO> totalParamMapper = new IParamMapper<UserDTO>() {

		@Override
		public void execute(PreparedStatement ps, UserDTO creDTO) throws SQLException {
			// Notice : sequence is usertype, and then userid
			ps.setString(1, creDTO.getUserType().toString());
			ps.setString(2, creDTO.getUserID());
		}
	};
	
	public SqlLoaderUserDAO() {
		super("user");
	}

	@Override
	protected IParamMapper<UserDTO> getDTOParamMapper() {
		
		return totalParamMapper;
	}

	@Override
	protected IRowMapper<UserDTO> getDTORowMapper() {

		return totalRawMapper;
	}

	@Override
	protected String getPrimaryKey(UserDTO value) {

		return value.getUserID();
	}

}
