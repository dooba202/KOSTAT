package com.ibm.query.execute.handler;

import java.sql.PreparedStatement;

import com.ibm.query.execute.refelect.IBeanInfo;
import com.ibm.query.model.ParamterType;

public class IntegerTypeHandler implements ITypeHandler{

	public void execute(PreparedStatement ps, ParamterType parameterType, int parameterIndex, Object inputValue) throws Exception{
		ps.setInt(parameterIndex, (Integer)inputValue);
	}

	@Override
	public Object getValue(ParamterType parameterType, IBeanInfo bean) throws Exception {
		return bean.getInteger(parameterType.getMethod());
	}

}
