package com.ibm.query.execute.handler;

import com.ibm.query.execute.Type;



public class TypeHandlerFactory {

	private static TypeHandlerFactory instance = null;
	
	private StringTypeHandler stringTypeHandler = null;
	
	private IntegerTypeHandler integerTypeHandler = null;
	
	private LongTypeHandler longTypeHandler = null;
	
	private TimeStampTypeHandler timeStampTypeHandler = null;
	
	private TypeHandlerFactory() {
		stringTypeHandler = new StringTypeHandler();
		integerTypeHandler = new IntegerTypeHandler();
		longTypeHandler = new LongTypeHandler();
		timeStampTypeHandler = new TimeStampTypeHandler();
	}

	public static TypeHandlerFactory getInstance() {
		synchronized (TypeHandlerFactory.class) {
			if(instance == null){
				instance = new TypeHandlerFactory();
			}
			
			return instance;	
		}
	}
	
	public ITypeHandler getTypeHandler(String type){
		if(Type.STRING.value().equalsIgnoreCase(type)){
			return stringTypeHandler;
		}
		
		if(Type.INT.value().equalsIgnoreCase(type)){
			return integerTypeHandler;
		}
		
		if(Type.LONG.value().equalsIgnoreCase(type)){
			return longTypeHandler;
		}
		
		if(Type.TIMESTAMP.value().equalsIgnoreCase(type)){
			return timeStampTypeHandler;
		}
		
		return null;
	}
}
