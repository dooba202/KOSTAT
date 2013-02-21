package com.ibm.query.parser.state;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.ibm.query.parser.XMLParserHandler;
import com.ibm.query.rule.Condition;
import com.ibm.query.rule.condition.IsEmptyCondition;

public class IsEmptyState extends ConditionState {
	
	public IsEmptyState(XMLParserHandler handler) {
		super(handler);
	}
	
	protected Condition getNewCondition(){
		return new IsEmptyCondition();
	}
	
	public void parseAttrs(Attributes attrs) throws SAXException{
		IsEmptyCondition selfCondition = (IsEmptyCondition)condition;
		selfCondition.setProperty(attrs.getValue("property"));
	}
}
