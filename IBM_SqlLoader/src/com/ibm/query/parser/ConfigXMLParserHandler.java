package com.ibm.query.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.ibm.query.execute.manager.ReplaceStringManager;
import com.ibm.query.model.ConfigModel;
import com.ibm.query.model.DataSourceModel;
import com.ibm.query.parser.state.AbstractState;
import com.ibm.query.parser.state.SkipState;

/**
  * ConfigXMLParserHandler.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
  */
public class ConfigXMLParserHandler extends XMLParserHandler{

	private ConfigModel rootModel = null;
	
	public ConfigModel getRootModel() {
		return rootModel;
	}

	@Override
	public AbstractState createStartState() {
		rootModel = new ConfigModel();
		
		return new SqlLoader(this);
	}
	
	class SqlLoader extends AbstractState{
		public SqlLoader(XMLParserHandler handler) {
			super(handler);
		}
		
		public AbstractState startElement(String tagName) throws SAXException{
			if (tagName.equalsIgnoreCase("sqlLoader")){
                return new SqlLoader(getHandler());
			}
			
			if (tagName.equalsIgnoreCase("dataSources")){
                return new DataSourcesState(getHandler());
			}
			
			if (tagName.equalsIgnoreCase("sqlList")){
                return new SqlLoader(getHandler());
			}
			
			if (tagName.equalsIgnoreCase("sql")){
                return new XmlState(getHandler());
			}
			
			if (tagName.equalsIgnoreCase("global")){
                return new GlobalState(getHandler());
			}
			
			return new SkipState(getHandler());
		}
	}
	
	class DataSourcesState extends AbstractState{
		
		public DataSourcesState(XMLParserHandler handler) {
			super(handler);
		}
		
		public AbstractState startElement(String tagName) throws SAXException{
			if (tagName.equalsIgnoreCase("dataSource")){
                return new DataSourceState(getHandler());
			}
			
			return new SkipState(getHandler());
		}
	}
	
	class DataSourceState extends AbstractState{
		
		private DataSourceModel datasource = null;
		
		public DataSourceState(XMLParserHandler handler) {
			super(handler);
			
			datasource = new DataSourceModel();
			
			rootModel.addDataSource(datasource);
		}
		
		public AbstractState startElement(String tagName) throws SAXException{
			if (tagName.equalsIgnoreCase("property")){
                return new PropertyState(getHandler(), datasource);
			}
			
			return new SkipState(getHandler());
		}
		
		public void parseAttrs(Attributes attrs) throws SAXException{
			datasource.setName(attrs.getValue("name"));
		}
	}
	
	class PropertyState extends AbstractState{
		private DataSourceModel datasource = null;
		
		public PropertyState(XMLParserHandler handler, DataSourceModel datasource) {
			super(handler);
			this.datasource = datasource;
		}
		
		public AbstractState startElement(String tagName) throws SAXException{
			
			return new SkipState(getHandler());
		}
		
		public void parseAttrs(Attributes attrs) throws SAXException{
			datasource.addProperty(attrs.getValue("name"), attrs.getValue("value"));
		}
	}
	
	class XmlState extends AbstractState{
		
		public XmlState(XMLParserHandler handler) {
			super(handler);
		}
		
		public AbstractState startElement(String tagName) throws SAXException{
			
			return new SkipState(getHandler());
		}
		
		public void parseAttrs(Attributes attrs) throws SAXException{
			rootModel.addQuery(attrs.getValue("resource"));
		}
	}
	
	class GlobalState extends AbstractState{
		
		public GlobalState(XMLParserHandler handler) {
			super(handler);
		}
		
		public AbstractState startElement(String tagName) throws SAXException{
			
			if (tagName.equalsIgnoreCase("replaceQeury")){
                return new ReplaceQeuryState(getHandler());
			}
			
			return new SkipState(getHandler());
		}
	}
	
	class ReplaceQeuryState extends AbstractState{
		
		public ReplaceQeuryState(XMLParserHandler handler) {
			super(handler);
		}
		
		public AbstractState startElement(String tagName) throws SAXException{
			
			return new SkipState(getHandler());
		}
		
		public void parseAttrs(Attributes attrs) throws SAXException{
			String from = attrs.getValue("from");
			String to = attrs.getValue("to");
			
			ReplaceStringManager.getInstance().addReplceString(from, to);
		}
	}
}
