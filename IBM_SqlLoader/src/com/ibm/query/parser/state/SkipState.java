package com.ibm.query.parser.state;

import org.xml.sax.SAXException;

import com.ibm.query.parser.XMLParserHandler;

/**
  * SkipState.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
  */
public class SkipState extends AbstractState {

	public SkipState(XMLParserHandler handler) {
		super(handler);
	}
	
	public AbstractState startElement(String tagName) throws SAXException{
		return new SkipState(getHandler());
	}
}
