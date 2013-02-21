package com.ibm.query.execute.handler;

import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ibm.query.execute.refelect.IBeanInfo;
import com.ibm.query.model.ParamterType;

public class TimeStampTypeHandler implements ITypeHandler{

	public void execute(PreparedStatement ps, ParamterType parameterType, int parameterIndex, Object inputData) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat(parameterType.getFormat());
		String value = sdf.format((Date)inputData);
		
		ps.setString(parameterIndex, value);
	}
	
	@Override
	public Object getValue(ParamterType parameterType, IBeanInfo bean) throws Exception {
		return bean.invokeTargetOperation(parameterType.getMethod());
	}
}
