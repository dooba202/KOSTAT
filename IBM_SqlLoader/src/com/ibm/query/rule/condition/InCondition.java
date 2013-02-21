package com.ibm.query.rule.condition;

import java.beans.IntrospectionException;
import java.util.Collection;
import java.util.Map;

import com.ibm.query.exception.InvokeFailedException;
import com.ibm.query.exception.MethodNotFoundException;
import com.ibm.query.execute.Type;
import com.ibm.query.execute.refelect.BeanInfoFactory;
import com.ibm.query.execute.refelect.IBeanInfo;
import com.ibm.query.execute.visitor.IRuleVisitor;
import com.ibm.query.rule.Condition;

public class InCondition extends Condition {
	
	private String fieldName = null;
	
	private Type type = null;
	
	private String name = null;
	
	private String field = null;
	
	private Object[] values = null;
	
	public void setProperty(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}
	
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Object[] values() {
		if(values == null){
			return new Object[0];
		}
		return values;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean isSatisfied(Object inputData) throws IntrospectionException, MethodNotFoundException, InvokeFailedException {
		if(inputData == null)
			return false;
		
		IBeanInfo bean = BeanInfoFactory.getBeanInfo(inputData);
		
		Object result = bean.invokeTargetOperation(fieldName);
		
		if(result instanceof Collection){
			if(! ((Collection)result).isEmpty()){
				Collection collection = (Collection)result; 
				values = collection.toArray(new Object[collection.size()]);
				if(values.length != 0){
					return true;	
				}
			}
			
		}
		
		if(result instanceof Map){
			if(!((Map)result).values().isEmpty()){
				Map map = (Map)result; 
				Collection collection = map.values();
				values = collection.toArray(new Object[collection.size()]);
				
				if(values.length != 0){
					return true;	
				}	
			}
		}
		
		if(result instanceof Object[]){
			values = (Object[])result;
			if(values.length != 0){
				return true;	
			}
			
		}
		
		return false;
	}


	@Override
	public void accept(IRuleVisitor queryVisitor, StringBuilder query, Object inputData) throws Exception {
		queryVisitor.visit(this, query, inputData);
	}

}
