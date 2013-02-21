package com.ibm.query.rule;

import java.util.HashMap;
import java.util.Map;

import com.ibm.query.model.IHasRule;

public class RuleContext extends HashMap<String, String> {

	private static final long serialVersionUID = 20110907L;
	
	private IHasRule ruleContainer = null;
	
	private int index = 1;
	
	private int keyIndex = 0;
	
	private String query = null;
	
	private String tmpQuery = null;
	
	private Map<Integer, String> fieldNameMap = new HashMap<Integer, String>();
	private Map<String, Object> fieldValueMap = new HashMap<String, Object>();
	private Map<String, String> fieldTypeMap = new HashMap<String, String>();
	
	private Map<Object, String> objectCache = new HashMap<Object, String>();
	
	public IHasRule getRuleContainer() {
		return ruleContainer;
	}
	
	public void clear(){
		fieldNameMap.clear();
		fieldValueMap.clear();
		fieldTypeMap.clear();
		objectCache.clear();
	}

	public void setRuleContainer(IHasRule ruleContainer) {
		this.ruleContainer = ruleContainer;
	}

	public void addFieldValue(String fieldName, String fieldType, Object fieldValue){
		fieldValueMap.put(fieldName, fieldValue);
		fieldTypeMap.put(fieldName, fieldType);
	}
	
	public String getObjectKey(Object key){
		if(objectCache.containsKey(key)){
			return objectCache.get(key);
		}else{
			int tmpKey = keyIndex++;
			String tmpKeyStr = String.valueOf(tmpKey);
			objectCache.put(key, tmpKeyStr);
			return tmpKeyStr; 
		}
	}
	
	public int getQuestionCount(){
		return fieldNameMap.size();
	}

	public String getFieldName(int key) {
		return fieldNameMap.get(key);
	}
	
	public Object getFieldValue(int key) {
		return fieldValueMap.get(getFieldName(key));
	}

	public String getQuery() {
		return query.trim();
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getTmpQuery() {
		return tmpQuery;
	}

	public void setTmpQuery(String tmpQuery) {
		this.tmpQuery = tmpQuery;
	}

	public void addFieldIndex(String fieldName) {
		fieldNameMap.put(index++, fieldName);		
	}

	public String getFieldType(String fieldName) {
		return fieldTypeMap.get(fieldName);
	}
	
}
