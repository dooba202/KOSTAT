package com.ibm.query.mapper.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ibm.query.mapper.IParamMapper;

public class EmptyParamMapper implements IParamMapper<Object> {

	@Override
	public void execute(PreparedStatement ps, Object inputObject)
			throws SQLException {
		// TODO Auto-generated method stub
	}

}
