package com.ibm.query.parser.state;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.ibm.query.parser.XMLParserHandler;
import com.ibm.query.rule.Condition;
import com.ibm.query.rule.condition.IsNotNullCondition;

public class IsNotNullState extends ConditionState {
	
	public IsNotNullState(XMLParserHandler handler) {
		super(handler);
	}
	
	protected Condition getNewCondition(){
		return new IsNotNullCondition();
	}
	
	public void parseAttrs(Attributes attrs) throws SAXException{
		IsNotNullCondition selfCondition = (IsNotNullCondition)condition;
		selfCondition.setProperty(attrs.getValue("property"));
	}
}
