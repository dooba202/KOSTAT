package com.ibm.query.parser.state;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.ibm.query.execute.Type;
import com.ibm.query.parser.XMLParserHandler;
import com.ibm.query.rule.Condition;
import com.ibm.query.rule.condition.InCondition;
import com.ibm.query.utils.QueryStringUtil;

public class InState extends ConditionState {
	
	public InState(XMLParserHandler handler) {
		super(handler);
	}
	
	protected Condition getNewCondition(){
		return new InCondition();
	}
	
	public AbstractState startElement(String tagName) throws SAXException{
		throw new SAXException("Unknow Tag!" + tagName);
	}
	
	public void parseAttrs(Attributes attrs) throws SAXException{
		InCondition selfCondition = (InCondition)condition;
		selfCondition.setProperty(attrs.getValue("property"));
		selfCondition.setName(attrs.getValue("name"));
		
		if(QueryStringUtil.isNotEmpty(attrs.getValue("type"))){
			selfCondition.setType(Type.fromValue(attrs.getValue("type")));	
		}
		
		if(QueryStringUtil.isNotEmpty(attrs.getValue("field"))){
			selfCondition.setField(attrs.getValue("field"));	
		}
	}
}
