package com.ibm.query.parser.state;

import com.ibm.query.model.IHasQuery;
import com.ibm.query.model.RuleContainer;
import com.ibm.query.model.SelectQueryModel;
import com.ibm.query.parser.XMLParserHandler;

/**
  * InsertQueryState.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
  */
public class InsertQueryState extends AbstractQueryState {

	public InsertQueryState(XMLParserHandler handler, String jdbc, String category) {
		super(handler, jdbc, category);
	}

	@Override
	protected IHasQuery createQueryModel() {
		return new SelectQueryModel();
	}

	protected RuleContainer getRuleContainer(){
		return (RuleContainer)model;
	}
}
