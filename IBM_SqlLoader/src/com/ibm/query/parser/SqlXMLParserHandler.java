package com.ibm.query.parser;

import com.ibm.query.parser.state.AbstractState;
import com.ibm.query.parser.state.SqlSetState;

/**
  * SqlXMLParserHandler.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
  */
public class SqlXMLParserHandler extends XMLParserHandler {

	@Override
	public AbstractState createStartState() {
		return new SqlSetState(this);
	}
	

}
