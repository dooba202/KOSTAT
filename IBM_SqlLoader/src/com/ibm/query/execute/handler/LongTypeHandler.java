package com.ibm.query.execute.handler;

import java.sql.PreparedStatement;

import com.ibm.query.execute.refelect.IBeanInfo;
import com.ibm.query.model.ParamterType;

public class LongTypeHandler implements ITypeHandler{

	public void execute(PreparedStatement ps, ParamterType parameterType, int parameterIndex, Object inputData) throws Exception{
		ps.setLong(parameterIndex, (Long)inputData);
	}
	
	@Override
	public Object getValue(ParamterType parameterType, IBeanInfo bean) throws Exception {
		return bean.getLong(parameterType.getMethod());
	}
}
