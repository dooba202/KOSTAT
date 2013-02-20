package com.ibm.query.rule;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ibm.query.exception.InvokeFailedException;
import com.ibm.query.exception.MethodNotFoundException;
import com.ibm.query.execute.visitor.IRuleVisitor;

public class GroupCondition extends Condition implements IhasCondition{

	private List<Condition> conditions = new ArrayList<Condition>();
	
	public Iterator<Condition> getConditions(){
		return conditions.iterator();
	}
	
	public void addCondition(Condition condition) {
		conditions.add(condition);
	}
	
	public void removceCondition(Condition oldCondition) {
		conditions.remove(oldCondition);
	}

	@Override
	public void accept(IRuleVisitor queryVisitor, StringBuilder query, Object inputData) throws Exception {
		queryVisitor.visit(this, query, inputData);
	}

	@Override
	public boolean isSatisfied(Object inputData) throws IntrospectionException, MethodNotFoundException, InvokeFailedException {
		return true;
	}
}
