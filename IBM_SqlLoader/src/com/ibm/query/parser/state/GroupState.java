package com.ibm.query.parser.state;

import org.xml.sax.SAXException;

import com.ibm.query.parser.XMLParserHandler;

public abstract class GroupState extends ConditionState{
	
	private int groupIndex = 0;
	
	public GroupState(XMLParserHandler handler) {
		super(handler);
	}
	
	public AbstractState startElement(String tagName) throws SAXException{
		return StateFactory.getState(tagName, this, getHandler());
	}

	public int getGroupIndex() {
		return groupIndex;
	}

	public void setGroupIndex(int groupIndex) {
		this.groupIndex = groupIndex;
	}
	
}
