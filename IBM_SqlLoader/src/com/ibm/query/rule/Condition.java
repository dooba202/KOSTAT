package com.ibm.query.rule;

import java.beans.IntrospectionException;

import com.ibm.query.exception.InvokeFailedException;
import com.ibm.query.exception.MethodNotFoundException;
import com.ibm.query.execute.visitor.IRuleVisitor;

public abstract class Condition {
	
	private Rule rule = null;
	
	protected Condition parent = null;
	
	private int conditionIndex = 0; 
	
	private String subQuery = null;
	
	public Condition getParent() {
		return parent;
	}

	public void setParent(Condition parent) {
		this.parent = parent;
	}

	public String getSubQuery() {
		return subQuery;
	}

	public void setSubQuery(String subQuery) {
		this.subQuery = subQuery;
	}
	
	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public int getConditionIndex() {
		return conditionIndex;
	}

	public void setConditionIndex(int conditionIndex) {
		this.conditionIndex = conditionIndex;
	}

	
	public Condition and(Condition condition) {
		return new ComplexCondition(this, condition);
	}
	
	public abstract boolean isSatisfied(Object inputData) throws IntrospectionException, MethodNotFoundException, InvokeFailedException;

	public abstract void accept(IRuleVisitor queryVisitor, StringBuilder query, Object inputData) throws Exception;

}
