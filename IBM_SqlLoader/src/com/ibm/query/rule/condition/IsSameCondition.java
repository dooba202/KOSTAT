package com.ibm.query.rule.condition;

import java.beans.IntrospectionException;

import com.ibm.query.exception.InvokeFailedException;
import com.ibm.query.exception.MethodNotFoundException;
import com.ibm.query.execute.refelect.BeanInfoFactory;
import com.ibm.query.execute.refelect.IBeanInfo;
import com.ibm.query.execute.visitor.IRuleVisitor;
import com.ibm.query.rule.Condition;
import com.ibm.query.utils.QueryStringUtil;

public class IsSameCondition extends Condition {
	
	private String fieldName = null;
	
	private String value = null;
	
	private String sensitive = null;
	
	public void setProperty(String fieldName) {
		this.fieldName = fieldName;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setSensitive(String sensitive) {
		this.sensitive = sensitive;
	}

	@Override
	public boolean isSatisfied(Object inputData) throws IntrospectionException, MethodNotFoundException, InvokeFailedException {
		if(inputData == null)
			return false;
		
		if(QueryStringUtil.isNull(fieldName)){
			return false;
		}
		
		if(QueryStringUtil.isNull(value)){
			return false;
		}
		
		IBeanInfo bean = BeanInfoFactory.getBeanInfo(inputData);
		
		Object result = bean.invokeTargetOperation(fieldName);
		
		if(result instanceof Boolean){
			Boolean bResult = (Boolean)result;
			if(bResult.equals(Boolean.parseBoolean(value))){
				return true;
			}else{
				return false;
			}
		}
		
		if(result instanceof Integer){
			Integer nResult = (Integer)result;
			if(nResult.equals(Integer.parseInt(value))){
				return true;
			}else{
				return false;
			}
		}
		
		if(result instanceof Long){
			Long nResult = (Long)result;
			if(nResult.equals(Long.parseLong(value))){
				return true;
			}else{
				return false;
			}
		}
		
		if("y".equalsIgnoreCase(sensitive)){
			return ((String)result).equals(value);
		}else{
			return ((String)result).equalsIgnoreCase(value);
		}
	}


	@Override
	public void accept(IRuleVisitor queryVisitor, StringBuilder query, Object inputData) throws Exception {
		queryVisitor.visit(this, query, inputData);
	}

}
