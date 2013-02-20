package com.ibm.query.parser.state;

import com.ibm.query.parser.XMLParserHandler;
import com.ibm.query.rule.Condition;
import com.ibm.query.rule.condition.IsTrueCondition;

public class IsTrueState extends ConditionState {
	
	public IsTrueState(XMLParserHandler handler) {
		super(handler);
	}
	
	protected Condition getNewCondition(){
		return new IsTrueCondition();
	}
}
