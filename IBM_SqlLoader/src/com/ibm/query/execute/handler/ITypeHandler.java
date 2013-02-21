package com.ibm.query.execute.handler;

import java.sql.PreparedStatement;

import com.ibm.query.execute.refelect.IBeanInfo;
import com.ibm.query.model.ParamterType;

public interface ITypeHandler {
	
	public Object getValue(ParamterType parameterType, IBeanInfo bean) throws Exception;

	public void execute(PreparedStatement ps, ParamterType parameterType, int parameterIndex, Object value) throws Exception;
}
