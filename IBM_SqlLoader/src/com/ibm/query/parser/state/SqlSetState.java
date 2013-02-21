package com.ibm.query.parser.state;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.ibm.query.parser.XMLParserHandler;

public class SqlSetState extends AbstractState{
	
	private String category = null;
	
	private String jdbc = null;
	
	
	public SqlSetState(XMLParserHandler handler) {
		super(handler);
	}
	
	public AbstractState startElement(String tagName) throws SAXException{
		if (tagName.equalsIgnoreCase("dbQuery")){
            return new SqlSetState(getHandler());
		}
		
		if (tagName.equalsIgnoreCase("select")){
            return new SelectQueryState(getHandler(), jdbc, category);
		}
		
		if (tagName.equalsIgnoreCase("insert")){
            return new InsertQueryState(getHandler(), jdbc, category);
		}
		
		if (tagName.equalsIgnoreCase("delete")){
            return new DeleteQueryState(getHandler(), jdbc, category);
		}
		
		if (tagName.equalsIgnoreCase("update")){
            return new UpdateQueryState(getHandler(), jdbc, category);
		}
		
		if (tagName.equalsIgnoreCase("proc")){
            return new ProcQueryState(getHandler(), jdbc, category);
		}
		
		if (tagName.equalsIgnoreCase("sub")){
            return new SubQueryState(getHandler(), jdbc, category);
		}
		
		return new SkipState(getHandler());
	}
	
	public void parseAttrs(Attributes attrs) throws SAXException{
		jdbc = getAttrib(attrs, "jdbc");
		category = getAttrib(attrs, "category");
	}
}
