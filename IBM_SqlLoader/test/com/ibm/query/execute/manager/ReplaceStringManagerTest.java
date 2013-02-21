package com.ibm.query.execute.manager;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ReplaceStringManagerTest {

	@Test
	public void testReplaceAll() throws Exception{
		ReplaceStringManager manager = ReplaceStringManager.getInstance();
		manager.addReplceString("test", "test2");
		
		assertEquals("test2", manager.replaceAll("#test#"));
	}
}
