package com.ibm.query.parser.state;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.ibm.query.parser.XMLParserHandler;

/**
  * SkipState.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
  */
public class RefState extends AbstractState {
	
	private String refId = null;

	public RefState(XMLParserHandler handler) {
		super(handler);
	}
	
	public String getRefId() {
		return refId;
	}

	public AbstractState startElement(String tagName) throws SAXException{
		return new RefState(getHandler());
	}
	
	public void parseAttrs(Attributes attrs) throws SAXException{
		refId = getAttrib(attrs, "id");
	}
}
