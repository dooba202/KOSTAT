package com.ibm.query.rule;

import java.beans.IntrospectionException;

import com.ibm.query.exception.InvokeFailedException;
import com.ibm.query.exception.MethodNotFoundException;
import com.ibm.query.execute.visitor.IRuleVisitor;

public class ComplexCondition extends Condition {

	protected GroupCondition childGroup;

	ComplexCondition(Condition parent, Condition child) {
		this.parent = parent;
		addChildCondition(child);
	}
	
	public Condition getParentCondition() {
		return parent;
	}
	
	public GroupCondition getChildCondition() {
		return childGroup;
	}
	
	public void addChildCondition(Condition child) {
		if(childGroup == null){
			childGroup = new GroupCondition();
		}
		
		childGroup.addCondition(child);
	}
	
	public void removeChildCondition(Condition parentCondition) {
		childGroup.removceCondition(parentCondition);	
	}
	
	public int getConditionIndex() {
		return parent.getConditionIndex();
	}
	
	@Override
	public boolean isSatisfied(Object inputData) throws IntrospectionException, MethodNotFoundException, InvokeFailedException {
		return parent.isSatisfied(inputData);
	}

	@Override
	public void accept(IRuleVisitor queryVisitor, StringBuilder query, Object inputData) throws Exception {
		queryVisitor.visit(this, query, inputData);
	}

	
}
