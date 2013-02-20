package com.ibm.query.parser.state;

import com.ibm.query.parser.XMLParserHandler;
import com.ibm.query.rule.Condition;
import com.ibm.query.rule.condition.IsFalseCondition;

public class IsFalseState extends ConditionState {
	
	public IsFalseState(XMLParserHandler handler) {
		super(handler);
	}
	
	protected Condition getNewCondition(){
		return new IsFalseCondition();
	}
}
