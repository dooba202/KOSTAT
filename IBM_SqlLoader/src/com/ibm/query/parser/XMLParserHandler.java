package com.ibm.query.parser;

import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.ibm.query.parser.state.AbstractQueryState;
import com.ibm.query.parser.state.AbstractState;
import com.ibm.query.parser.state.GroupState;
import com.ibm.query.parser.state.IConditionState;
import com.ibm.query.parser.state.RefState;

/**
  * XMLParserHandler.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
  */
public abstract class XMLParserHandler extends DefaultHandler {

	protected String currentElement;
    protected Stack<AbstractState> stateStack;
    private int ruleIndex = 0;
    private AbstractQueryState queryState = null;
    
    public XMLParserHandler()
    {
        currentElement = null;
        stateStack = new Stack<AbstractState>();
    }

    public void startDocument()
        throws SAXException
    {
        super.startDocument();
        if(!stateStack.isEmpty())
        {
            throw new AssertionError();
        } else
        {
            pushState(createStartState());
            return;
        }
    }

    protected void pushState(AbstractState state)
    {
        if(state == null)
        {
            throw new AssertionError();
        } else
        {
            stateStack.push(state);
            return;
        }
    }

    public void endDocument()
        throws SAXException
    {
        super.endDocument();
        if(stateStack.size() != 1)
        {
            throw new AssertionError();
        } else
        {
            topState().end();
            popState();
            return;
        }
    }

    private AbstractState popState()
        throws SAXException
    {
        if(stateStack.isEmpty())
            throw new AssertionError();
        AbstractState state = (AbstractState)stateStack.pop();
        if(stateStack.size() > 0)
            topState().endElement(state);
        return state;
    }

    protected AbstractState topState()
    {
        if(stateStack.isEmpty()){
        	throw new AssertionError();
        }else{
        	return (AbstractState)stateStack.lastElement();
        }
            
    }

    public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
        throws SAXException
    {
        currentElement = qName;
        AbstractState newState = topState().startElement(qName);
        
        if(newState instanceof AbstractQueryState){
        	queryState = (AbstractQueryState)newState;
        }
        
        ruleIndex++;
        
        if(newState instanceof GroupState){
        	((GroupState)newState).setGroupIndex(ruleIndex);
        	((IConditionState)newState).setConditionIndex(ruleIndex);
        	queryState.getText().append("@[rule"+ ruleIndex +"]{");
        	
        }else if(newState instanceof IConditionState){
        	((IConditionState)newState).setConditionIndex(ruleIndex);
        	markPosition();	
        }
        
        pushState(newState);
        newState.parseAttrs(atts);
    }

    public void endElement(String namespaceURI, String localName, String qName)
        throws SAXException
    {
        AbstractState state = topState();
        
        if(state instanceof GroupState){
        	queryState.getText().append("@[rule"+ ((GroupState)state).getGroupIndex() +"]}");
        }
        
        if(state instanceof AbstractQueryState){
        	queryState = null;
        }
        
        if(state instanceof RefState){
        	String refId = ((RefState)state).getRefId();
        	queryState.getText().append("@[ref."+ refId +"]");		
        }
        
        state.end();
        popState();
        if(!stateStack.isEmpty())
            topState().endElement(state);
    }
    
    public void markPosition(){
    	queryState.getText().append("@[rule"+ ruleIndex +"]");	
    }

    public void characters(char ch[], int start, int length)
        throws SAXException
    {
        if(!stateStack.isEmpty())
            topState().getText().append(ch, start, length);
    }

    public abstract AbstractState createStartState();
}
