package com.ibm.query.rule;

import java.util.Iterator;

public interface IhasCondition {
	
	public Iterator<Condition> getConditions();
	
	public void addCondition(Condition condition);
	
	public void removceCondition(Condition condition);
}
