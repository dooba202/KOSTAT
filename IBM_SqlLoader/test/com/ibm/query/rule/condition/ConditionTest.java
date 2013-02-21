package com.ibm.query.rule.condition;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ibm.query.data.SimpleUser;

public class ConditionTest {

	@Test
	public void testSimpleCondition() throws Exception{
		IsNotNullCondition condtion1 = new IsNotNullCondition();
		condtion1.setProperty("getName");
		
		SimpleUser user = new SimpleUser();
		user.setName("test");
		
		assertTrue(condtion1.and(condtion1).isSatisfied(user));
	}
}
