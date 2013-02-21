package com.ibm.query.rule.condition;

import java.beans.IntrospectionException;

import com.ibm.query.exception.InvokeFailedException;
import com.ibm.query.exception.MethodNotFoundException;
import com.ibm.query.execute.refelect.BeanInfoFactory;
import com.ibm.query.execute.refelect.IBeanInfo;
import com.ibm.query.execute.visitor.IRuleVisitor;
import com.ibm.query.rule.Condition;

public class IsNotNullCondition extends Condition {
	
	private String fieldName = null;
	
	public void setProperty(String fieldName) {
		this.fieldName = fieldName;
	}


	@Override
	public boolean isSatisfied(Object inputData) throws IntrospectionException, MethodNotFoundException, InvokeFailedException {
		if(inputData == null)
			return false;
		
		IBeanInfo bean = BeanInfoFactory.getBeanInfo(inputData);
		
		Object result = bean.invokeTargetOperation(fieldName);
		
		return result == null ? false : true;
	}


	@Override
	public void accept(IRuleVisitor queryVisitor, StringBuilder query, Object inputData) throws Exception {
		queryVisitor.visit(this, query, inputData);
	}

}
