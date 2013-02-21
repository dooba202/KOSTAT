package com.ibm.query.model;

import java.util.Iterator;
import java.util.Map;

import com.ibm.query.rule.Rule;

public interface IHasRule {

	public int getRuleCount();

	public Iterator<Rule> getRules();
	
	public void addRule(Rule rule);
	
	public Map<String, ParamterType> getParameterTypeMap();

	public ParamterType getParameterType(String fieldName);
	
	public Iterator<String> getParameterKeys();
}
