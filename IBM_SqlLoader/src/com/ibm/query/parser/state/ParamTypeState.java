package com.ibm.query.parser.state;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.ibm.query.model.ParamterType;
import com.ibm.query.model.RuleContainer;
import com.ibm.query.parser.XMLParserHandler;

public class ParamTypeState extends AbstractState {
	
	private RuleContainer container = null;

	public ParamTypeState(XMLParserHandler handler, RuleContainer container) {
		super(handler);
		this.container = container;
		
	}
	
	public AbstractState startElement(String tagName) throws SAXException{
		return new SkipState(getHandler());
	}
	
	public void parseAttrs(Attributes attrs) throws SAXException{
		String key = attrs.getValue("name");
		String type = attrs.getValue("type");
		String method = attrs.getValue("method");
		String value = attrs.getValue("value");
		String sensitive = attrs.getValue("sensitive");
		String format = attrs.getValue("format");
		String escape = attrs.getValue("escape");
		
		ParamterType paramterType = new ParamterType();
 		paramterType.setName(key);
		paramterType.setMethod(method);
		paramterType.setType(type);
		paramterType.setEscape(escape);
		paramterType.setValue(value);
		paramterType.setSensitive(sensitive);
		paramterType.setFormat(format);
		
		container.addType(key, paramterType);
	}
}
