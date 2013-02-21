package com.ibm.query.parser.state;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.ibm.query.parser.XMLParserHandler;
import com.ibm.query.rule.Condition;
import com.ibm.query.rule.condition.IsNotEmptyCondition;

public class IsNotEmptyState extends ConditionState {
	
	public IsNotEmptyState(XMLParserHandler handler) {
		super(handler);
	}
	
	protected Condition getNewCondition(){
		return new IsNotEmptyCondition();
	}
	
	public void parseAttrs(Attributes attrs) throws SAXException{
		IsNotEmptyCondition selfCondition = (IsNotEmptyCondition)condition;
		selfCondition.setProperty(attrs.getValue("property"));
	}
}
