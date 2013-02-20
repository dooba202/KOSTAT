package com.ibm.query.parser.state;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.ibm.query.model.IHasQuery;
import com.ibm.query.model.ProcQueryModel;
import com.ibm.query.model.RuleContainer;
import com.ibm.query.parser.XMLParserHandler;

/**
  * SelectQueryState.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
  */
public class ProcQueryState extends AbstractQueryState {

	public ProcQueryState(XMLParserHandler handler, String jdbc, String category) {
		super(handler, jdbc, category);
	}

	@Override
	protected IHasQuery createQueryModel() {
		return new ProcQueryModel();
	}
	
	protected RuleContainer getRuleContainer(){
		return (RuleContainer)model;
	}
	
	public void parseAttrs(Attributes attrs) throws SAXException{
		model.setId(getAttrib(attrs, "id"));
	}
}
