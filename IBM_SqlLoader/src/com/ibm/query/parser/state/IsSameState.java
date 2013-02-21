package com.ibm.query.parser.state;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.ibm.query.parser.XMLParserHandler;
import com.ibm.query.rule.Condition;
import com.ibm.query.rule.condition.IsSameCondition;

public class IsSameState extends ConditionState {
	
	public IsSameState(XMLParserHandler handler) {
		super(handler);
	}
	
	protected Condition getNewCondition(){
		return new IsSameCondition();
	}
	
	public void parseAttrs(Attributes attrs) throws SAXException{
		IsSameCondition selfCondition = (IsSameCondition)condition;
		selfCondition.setProperty(attrs.getValue("property"));
		selfCondition.setValue(attrs.getValue("value"));
		selfCondition.setSensitive(attrs.getValue("sensitive"));
	}
}
