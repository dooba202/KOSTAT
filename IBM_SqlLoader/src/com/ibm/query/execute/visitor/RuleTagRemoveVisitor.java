package com.ibm.query.execute.visitor;

import java.util.Iterator;

import com.ibm.query.rule.ComplexCondition;
import com.ibm.query.rule.Condition;
import com.ibm.query.rule.GroupCondition;
import com.ibm.query.rule.Rule;
import com.ibm.query.rule.condition.ForCondition;
import com.ibm.query.rule.condition.InCondition;
import com.ibm.query.utils.QueryStringUtil;

public class RuleTagRemoveVisitor implements IRuleVisitor{

	@Override
	public void visit(Rule rule, Object inputData) throws Exception {
		
	}

	@Override
	public void visit(ComplexCondition condition, StringBuilder query, Object inputData) throws Exception {
		int index = condition.getConditionIndex();
		QueryStringUtil.replace(query, "@[rule"+index+"]", "");
		
		condition.getChildCondition().accept(this, query, inputData);
	}
	
	public void visit(GroupCondition group, StringBuilder query, Object inputData) throws Exception {
		Iterator<Condition> conditions = group.getConditions();
		while (conditions.hasNext()) {
			Condition condition = (Condition) conditions.next();
			condition.accept(this, query, inputData);
		}
	}

	@Override
	public void visit(Condition condition, StringBuilder query, Object inputData) throws Exception {
		int index = condition.getConditionIndex();
		QueryStringUtil.replace(query, "@[rule"+index+"]", "");
	}

	@Override
	public void visit(InCondition condition, StringBuilder query, Object inputData) throws Exception {
		int index = condition.getConditionIndex();
		QueryStringUtil.replace(query, "@[rule"+index+"]", "");
	}

	@Override
	public void visit(ForCondition condition, StringBuilder query, Object inputData) throws Exception {
		int index = condition.getConditionIndex();
		QueryStringUtil.replace(query, "@[rule"+index+"]", "");
		
		if(condition.isSatisfied(inputData)){
			Iterator<Condition> conditions = condition.getConditions();
			while (conditions.hasNext()) {
				Condition subCondition = (Condition) conditions.next();
				
				subCondition.accept(this, query, inputData);
			}
		}
	}
}
