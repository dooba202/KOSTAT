package com.ibm.query.parser.state;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.ibm.query.parser.XMLParserHandler;
import com.ibm.query.rule.Condition;
import com.ibm.query.rule.condition.ForCondition;

public class ForState extends GroupState {
	
	public ForState(XMLParserHandler handler) {
		super(handler);
	}
	
	protected Condition getNewCondition(){
		return new ForCondition();
	}
	
	public void parseAttrs(Attributes attrs) throws SAXException{
		ForCondition selfCondition = (ForCondition)condition;
		selfCondition.setProperty(attrs.getValue("property"));
	}
}
