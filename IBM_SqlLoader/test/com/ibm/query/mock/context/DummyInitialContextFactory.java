package com.ibm.query.mock.context;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

public class DummyInitialContextFactory implements InitialContextFactory {

	static DummyContext context = new DummyContext();
	
	
	public DummyInitialContextFactory() {
		super();
	}


	@Override
	public Context getInitialContext(Hashtable<?, ?> arg0) throws NamingException {
		return context;
	}

	public void setProperty(String key, Object object){
		try {
			context.addToEnvironment(key, object);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
}
