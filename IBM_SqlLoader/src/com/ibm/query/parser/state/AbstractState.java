package com.ibm.query.parser.state;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.ibm.query.parser.XMLParserHandler;

/**
  * AbstractState.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
  */
public class AbstractState {
    private XMLParserHandler handler;
    private StringBuffer text;

	public AbstractState(XMLParserHandler handler){
        text = new StringBuffer();
        this.handler = handler;
    }

    public AbstractState startElement(String tagName) throws SAXException{
        throw new SAXException("Unknow Tag!" + tagName);
    }

    public void endElement(AbstractState abstractstate) throws SAXException{
    	if(abstractstate == null){
    		throw new SAXException();
    	}
    }

    public void parseAttrs(Attributes attributes) throws SAXException{
    	if(attributes == null){
    		throw new SAXException();
    	}
    }

    protected String getAttrib(Attributes attrs, String attrName){
        return attrs.getValue(attrName);
    }

    public int getIntAttrib(Attributes attrs, String attrName){
        return getIntAttrib(attrs, attrName, 0);
    }

    public int getIntAttrib(Attributes attrs, String attrName, int defaultValue){
    	try {
    		String value;
            value = attrs.getValue(attrName);
            if(value == null)
                return defaultValue;
            Integer result = Integer.decode(value);
            return result.intValue();	
		} catch (NumberFormatException e) {
			return 0;
		}
    }

    public boolean end()throws SAXException{
    	return false;
    }

    public StringBuffer getText(){
        return text;
    }

    protected XMLParserHandler getHandler(){
        return handler;
    }

    protected void setHandler(XMLParserHandler handler)
    {
        this.handler = handler;
    }


}
