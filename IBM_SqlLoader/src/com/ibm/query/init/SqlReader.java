package com.ibm.query.init;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SqlReader {
	
	private static SqlReader instance = null;
	
	private SAXParserFactory spf = null;
	
	private SAXParser sp = null;
	
	private SqlReader() throws ParserConfigurationException, SAXException {
		spf = SAXParserFactory.newInstance();
		sp=spf.newSAXParser();
	}

	public static SqlReader getInstance() throws ParserConfigurationException, SAXException {
		synchronized (SqlReader.class) {
			if(instance == null){
				instance = new SqlReader();
			}
			
			return instance;	
		}
	}

	public void read(InputStream is, DefaultHandler handler) throws SAXException, IOException{
		sp.parse(is, handler);
	}
}
