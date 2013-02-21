package com.ibm.query.rule.condition;

import java.beans.IntrospectionException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.ibm.query.exception.InvokeFailedException;
import com.ibm.query.exception.MethodNotFoundException;
import com.ibm.query.execute.refelect.BeanInfoFactory;
import com.ibm.query.execute.refelect.IBeanInfo;
import com.ibm.query.execute.visitor.IRuleVisitor;
import com.ibm.query.rule.Condition;
import com.ibm.query.utils.QueryStringUtil;

public class IsNotEmptyCondition extends Condition {
	
	private String fieldName = null;
	
	public void setProperty(String fieldName) {
		this.fieldName = fieldName;
	}


	@SuppressWarnings("rawtypes")
	public boolean isSatisfied(Object inputData) throws IntrospectionException, MethodNotFoundException, InvokeFailedException {
		if(inputData == null)
			return false;
		
		IBeanInfo bean = BeanInfoFactory.getBeanInfo(inputData);
		
		Object result = bean.invokeTargetOperation(fieldName);
		if(result instanceof String){
			return QueryStringUtil.isNotEmpty((String)result);
		}
		
		if(result instanceof Long){
			return ((Long)result).intValue() == 0 ? false : true;
		}
		
		if(result instanceof Integer){
			return ((Integer)result).intValue() == 0 ? false : true;
		}
		
		if(result instanceof Collection){
			return ! ((Collection)result).isEmpty();
		}
		
		if(result instanceof Map){
			return ! ((Map)result).isEmpty();
		}
		
		if(result instanceof Date){
			return true;
		}
		
		return false;
	}


	@Override
	public void accept(IRuleVisitor queryVisitor, StringBuilder query, Object inputData) throws Exception {
		queryVisitor.visit(this, query, inputData);
	}

}
