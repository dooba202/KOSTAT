package com.ibm.query.execute.handler;

import java.sql.PreparedStatement;

import com.ibm.query.execute.refelect.IBeanInfo;
import com.ibm.query.model.ParamterType;
import com.ibm.query.utils.QueryStringUtil;

public class StringTypeHandler implements ITypeHandler{
	
	public Object getValue(ParamterType parameterType, IBeanInfo bean) throws Exception{
		String value = "";
		if(QueryStringUtil.isNotEmpty(parameterType.getValue()) ){
			value = QueryStringUtil.getQueryString(bean.getString(parameterType.getMethod()), parameterType.getEscape());
			value = QueryStringUtil.replace(parameterType.getValue(), "?", value);
		}else{
			value = QueryStringUtil.getQueryString(bean.getString(parameterType.getMethod()), parameterType.getEscape());
		}
		
		return value;
	}

	public void execute(PreparedStatement ps, ParamterType parameterType, int parameterIndex, Object inputData) throws Exception{
		String value = (String)inputData;
		
		if(parameterType.isSensitiveEmpty()){
			ps.setString(parameterIndex, value);	
		}else{
			if("lower".equalsIgnoreCase(parameterType.getSensitive())){
				ps.setString(parameterIndex, value.toLowerCase());	
			}else{
				ps.setString(parameterIndex, value.toUpperCase());
			}
		}
	}
}
