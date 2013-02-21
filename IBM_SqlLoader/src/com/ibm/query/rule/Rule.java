package com.ibm.query.rule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ibm.query.execute.visitor.IRuleVisitor;

public class Rule implements IhasCondition{

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

	public void accept(IRuleVisitor visitor, Object inputData) throws Exception {
		visitor.visit(this, inputData);
	}

	@Override
	public String toString() {
		return "Rule [conditions=" + conditions + "]";
	}

	
	
}
