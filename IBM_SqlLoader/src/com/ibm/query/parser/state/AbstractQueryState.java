package com.ibm.query.parser.state;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.ibm.query.execute.manager.QueryManager;
import com.ibm.query.model.IHasQuery;
import com.ibm.query.model.RuleContainer;
import com.ibm.query.parser.XMLParserHandler;

/**
  * AbstractQueryState.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
  */
public abstract class AbstractQueryState extends AbstractState {

	protected IHasQuery model = null;

	public AbstractQueryState(XMLParserHandler handler, String jdbc, String category) {
		super(handler);
		
		model = createQueryModel();
		model.setJdbc(jdbc);
		model.setCategory(category);
	}
	
	public void parseAttrs(Attributes attrs) throws SAXException{
		model.setId(getAttrib(attrs, "id"));
	}
	
	public boolean end() throws SAXException{
		String value = getText().toString().trim();
		
		model.setRawQuery(value);
		
		String key = null;
		if(model.getCategory()==null || model.getCategory().isEmpty()){
			key = model.getId();
		}else{
			key = model.getCategory() +"."+model.getId();	
		}
		
		QueryManager.getInstance().addQuery(key, model);
		
		return true;
	}
	
	protected abstract IHasQuery createQueryModel();
	
	protected abstract RuleContainer getRuleContainer();
}
