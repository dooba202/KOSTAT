package com.ibm.query.parser.state;

import org.xml.sax.SAXException;

import com.ibm.query.model.RuleContainer;
import com.ibm.query.parser.XMLParserHandler;
import com.ibm.query.rule.Rule;

/**
  * SkipState.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
  */
public class RuleState extends AbstractState{
	
	private Rule rule = null;

	public RuleState(XMLParserHandler handler, RuleContainer container) {
		super(handler);
		
		rule = new Rule();
		
		container.addRule(rule);
	}
	
	public AbstractState startElement(String tagName) throws SAXException{
		
		return StateFactory.getState(tagName, rule, getHandler());
	}
}
