package com.ibm.query.execute.visitor;

import com.ibm.query.rule.ComplexCondition;
import com.ibm.query.rule.Condition;
import com.ibm.query.rule.GroupCondition;
import com.ibm.query.rule.Rule;
import com.ibm.query.rule.condition.ForCondition;
import com.ibm.query.rule.condition.InCondition;

public interface IRuleVisitor {

	public void visit(Rule rule, Object inputData) throws Exception;
	
	public void visit(ComplexCondition condition, StringBuilder query, Object inputData) throws Exception;
	
	public void visit(GroupCondition condition, StringBuilder query, Object inputData) throws Exception;
	
	public void visit(Condition condition, StringBuilder query, Object inputData) throws Exception;
	
	public void visit(InCondition condition, StringBuilder query, Object inputData) throws Exception;
	
	public void visit(ForCondition condition, StringBuilder query, Object inputData) throws Exception;
}
