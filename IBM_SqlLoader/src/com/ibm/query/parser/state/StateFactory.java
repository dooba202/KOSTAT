package com.ibm.query.parser.state;

import com.ibm.query.parser.XMLParserHandler;
import com.ibm.query.rule.ComplexCondition;
import com.ibm.query.rule.Condition;
import com.ibm.query.rule.GroupCondition;
import com.ibm.query.rule.Rule;

public class StateFactory {

	public static AbstractState getState(String tagName, Rule rule, XMLParserHandler handler){
		ConditionState childState = (ConditionState) getState(tagName, handler);
		Condition condition = childState.createConditon();
		
		rule.addCondition(condition);
		condition.setRule(rule);
		
		return childState;
	}
	
	public static AbstractState getState(String tagName, ConditionState parent, XMLParserHandler handler){
		Condition parentCondition = parent.getCondition();
		Rule rule = parentCondition.getRule();
		
		ConditionState childState = (ConditionState) getState(tagName, handler);
		Condition childCondition = childState.createConditon();
		childCondition.setRule(rule);
		
		Condition and = parentCondition.and(childCondition);
		
		if(parentCondition.getParent() == null){
			rule.removceCondition(parentCondition);
			rule.addCondition(and);	
		}else if(parentCondition.getParent() instanceof GroupCondition){
			GroupCondition grandParent = (GroupCondition)parentCondition.getParent();
			grandParent.removceCondition(parentCondition);
			grandParent.addCondition(and);
		}else{
			ComplexCondition grandParent = (ComplexCondition)parentCondition.getParent();
			
			grandParent.removeChildCondition(parentCondition);
			grandParent.addChildCondition(and);
		}
		
		childCondition.setParent(and);
		
		return childState;
	}
	
	public static AbstractState getState(String tagName, GroupState parent, XMLParserHandler handler){
		GroupCondition groupCondition = (GroupCondition) parent.getCondition();
		ConditionState childState = (ConditionState) getState(tagName, handler);
		Condition childCondition = childState.createConditon();
		groupCondition.addCondition(childCondition);
		childCondition.setRule(groupCondition.getRule());
		
		childCondition.setParent(groupCondition);
		
		return childState;
	}
	
	private static AbstractState getState(String tagName, XMLParserHandler handler){
		if (tagName.equalsIgnoreCase("isTrue")){
            return new IsTrueState(handler);
		}
		
		if (tagName.equalsIgnoreCase("isFalse")){
            return new IsFalseState(handler);
		}
		
		if (tagName.equalsIgnoreCase("isNotNull")){
            return new IsNotNullState(handler);
		}
		
		if (tagName.equalsIgnoreCase("isSame")){
            return new IsSameState(handler);
		}
		
		if (tagName.equalsIgnoreCase("isNotSame")){
            return new IsNotSameState(handler);
		}
		
		if (tagName.equalsIgnoreCase("isNotEmpty")){
            return new IsNotEmptyState(handler);
		}
		
		if (tagName.equalsIgnoreCase("isEmpty")){
            return new IsEmptyState(handler);
		}
		
		if (tagName.equalsIgnoreCase("in")){
            return new InState(handler);
		}
		
		if (tagName.equalsIgnoreCase("for")){
            return new ForState(handler);
		}
		
		
		return new SkipState(handler);
	}
}
