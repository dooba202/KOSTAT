package com.ibm.query.rule.condition;

import java.beans.IntrospectionException;

import com.ibm.query.exception.InvokeFailedException;
import com.ibm.query.exception.MethodNotFoundException;
import com.ibm.query.execute.visitor.IRuleVisitor;
import com.ibm.query.rule.Condition;

public class IsFalseCondition extends Condition {
	
	public boolean isSatisfied(Object inputData) throws IntrospectionException, MethodNotFoundException, InvokeFailedException {
		return true;
	}


	@Override
	public void accept(IRuleVisitor queryVisitor, StringBuilder query, Object inputData) throws Exception {
		queryVisitor.visit(this, query, inputData);
	}

}
