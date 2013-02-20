package com.ibm.query.mock.context;

import java.util.Hashtable;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class DummyInitialContext extends InitialContext {

	public DummyInitialContext(Hashtable<?, ?> environment) throws NamingException {
		super(environment);
	}

}
