package com.ibm.query.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ibm.query.rule.Rule;

public class RuleContainer implements IHasRule{

	private List<Rule> ruleList = null;
	
	private Map<String, ParamterType> parameterTypeMap = new HashMap<String, ParamterType>();
	
	public void addRule(Rule rule) {
		synchronized (this) {
			if(ruleList ==null){
				ruleList = new ArrayList<Rule>();
			}	
		}
		
		ruleList.add(rule);
	}
	
	public Iterator<Rule> getRules(){
		if(ruleList ==null)
			ruleList = new ArrayList<Rule>();
		
		return ruleList.iterator();
	}
	
	public int getRuleCount(){
		return ruleList.size();
	}
	
	public void addType(String key, ParamterType type){
		parameterTypeMap.put(key.toLowerCase(), type);
	}

	public Map<String, ParamterType> getParameterTypeMap() {
		return parameterTypeMap;
	}

	@Override
	public ParamterType getParameterType(String fieldName) {
		return parameterTypeMap.get(fieldName.toLowerCase());
	}
	
	public Iterator<String> getParameterKeys(){
		return parameterTypeMap.keySet().iterator();
	}
}
