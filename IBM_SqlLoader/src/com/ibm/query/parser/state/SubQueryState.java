package com.ibm.query.parser.state;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.ibm.query.model.IHasQuery;
import com.ibm.query.model.RuleContainer;
import com.ibm.query.model.SubQueryModel;
import com.ibm.query.parser.XMLParserHandler;

/**
  * SelectQueryState.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
  */
public class SubQueryState extends AbstractQueryState {

	public SubQueryState(XMLParserHandler handler, String jdbc, String category) {
		super(handler, jdbc, category);
	}

	@Override
	protected IHasQuery createQueryModel() {
		return new SubQueryModel();
	}
	
	public AbstractState startElement(String tagName) throws SAXException{
		if (tagName.equalsIgnoreCase("rule")){
            return new RuleState(getHandler(), getRuleContainer());
		}
		
		if (tagName.equalsIgnoreCase("param")){
            return new ParamTypeState(getHandler(), getRuleContainer());
		}
		return super.startElement(tagName);
	}
	
	protected RuleContainer getRuleContainer(){
		return (RuleContainer)model;
	}
	
	public void parseAttrs(Attributes attrs) throws SAXException{
		model.setId(getAttrib(attrs, "id"));
	}
}
