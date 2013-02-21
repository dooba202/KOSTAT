package com.ibm.query.execute.visitor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.ibm.query.execute.Type;
import com.ibm.query.execute.refelect.ObjectBeanInfo;
import com.ibm.query.model.IHasRule;
import com.ibm.query.rule.ComplexCondition;
import com.ibm.query.rule.Condition;
import com.ibm.query.rule.GroupCondition;
import com.ibm.query.rule.Rule;
import com.ibm.query.rule.RuleContext;
import com.ibm.query.rule.condition.ForCondition;
import com.ibm.query.rule.condition.InCondition;
import com.ibm.query.utils.QueryStringUtil;
import com.ibm.query.utils.TypeCheckUtil;

public class QueryMakeVisitor implements IRuleVisitor{
	
	private RuleTagRemoveVisitor removeVisitor = null;
	
	protected ThreadLocal<RuleContext> contextSession = new ThreadLocal<RuleContext>();
	
	public QueryMakeVisitor() {
		removeVisitor = new RuleTagRemoveVisitor();
	}
	
	public void setContext(RuleContext context) {
		contextSession.set(context);
	}
	
	public RuleContext getContext(){
		return contextSession.get();
	}
	
	public void clear(){
		contextSession.remove();
	}
	
	@Override
	public void visit(Rule rule, Object inputData) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(getContext().getQuery());
		
		Iterator<Condition> conditions = rule.getConditions();
		while (conditions.hasNext()) {
			Condition condition = (Condition) conditions.next();
			
			condition.accept(this, sb, inputData);
		}
		
		getContext().setQuery(sb.toString());
	}

	@Override
	public void visit(ComplexCondition condition, StringBuilder query, Object inputData) throws Exception {
		int index = condition.getConditionIndex();
		QueryStringUtil.replace(query, "@[rule"+index+"]", " ");
		
		if(condition.isSatisfied(inputData)){
			condition.getChildCondition().accept(this, query, inputData);
		}else{
			condition.accept(removeVisitor, query, inputData);
		}
	}
	
	public void visit(GroupCondition group, StringBuilder query, Object inputData) throws Exception {
		Iterator<Condition> conditions = group.getConditions();
		while (conditions.hasNext()) {
			Condition condition = (Condition) conditions.next();
			condition.accept(this, query, inputData);
		}
	}

	@Override
	public void visit(Condition condition, StringBuilder query, Object inputData) throws Exception {
		int index = condition.getConditionIndex();
		if(condition.isSatisfied(inputData)){
			RuleContext context = getContext();
			
			IHasRule ruleContainer = context.getRuleContainer();
			String subQuery = condition.getSubQuery();
			subQuery = QueryStringUtil.replaceSpecificWord(ruleContainer, subQuery, context, inputData);
			
			QueryStringUtil.replace(query, "@[rule"+index+"]", " "+ subQuery);
		}else{
			QueryStringUtil.replace(query, "@[rule"+index+"]", " ");
		}
	}

	@Override
	public void visit(InCondition condition, StringBuilder query, Object inputData) throws Exception {
		int index = condition.getConditionIndex();
		if(condition.isSatisfied(inputData)){
			Object[] values = condition.values();
			if(condition.getType() == null){
				condition.setType(Type.STRING);
			}
			
			StringBuilder replacedStr = new StringBuilder();
			for (int i = 0; i < values.length; i++) {
				Object object = values[i];
				String field = condition.getField();
				
				if(TypeCheckUtil.isPrimitive(object) || field == null || field.isEmpty() ){
					if(Type.STRING.equals(condition.getType())){
						replacedStr.append("'"+ object.toString().trim() + "'");	
					}else{
						replacedStr.append(object);
					}	
				}else{
					ObjectBeanInfo bean = new ObjectBeanInfo(object);
					
					if(Type.STRING.equals(condition.getType())){
						replacedStr.append("'"+ bean.getString(field) + "'");	
					}else if(Type.INT.equals(condition.getType())){
						replacedStr.append(bean.getInteger(field));	
					}else if(Type.LONG.equals(condition.getType())){
						replacedStr.append(bean.getLong(field));	
					}else{
						replacedStr.append("'"+ object + "'");
					}
				}
				
				if(i+1 != values.length){
					replacedStr.append(", ");
				}
				
			}
			
			String sql = condition.getSubQuery();
			
			Map<String, String> paramMap = new HashMap<String, String>(1);
			paramMap.put(condition.getName(), replacedStr.toString());
			
			sql = QueryStringUtil.findAndReplace(sql, paramMap);
			QueryStringUtil.replace(query, "@[rule"+index+"]", " "+ sql);
			
		}else{
			QueryStringUtil.replace(query, "@[rule"+index+"]", " ");
		}
	}

	@Override
	public void visit(ForCondition condition, StringBuilder sb, Object inputData) throws Exception {
		int index = condition.getConditionIndex();
		
		if(! condition.isSatisfied(inputData)){
			QueryStringUtil.replaceRange(sb, "@[rule"+index+"]{", "@[rule"+index+"]}", "");
			return;
		}
		
		String subQuery = QueryStringUtil.subString(sb.toString(), "@[rule"+index+"]{", "@[rule"+index+"]}");
		StringBuilder subSb = new StringBuilder();
		
		Object[] values = condition.values();
		for (Object object : values) {
			subSb.append(subQuery);
			
			Iterator<Condition> conditions = condition.getConditions();
			while (conditions.hasNext()) {
				Condition subCondition = (Condition) conditions.next();
				
				subCondition.accept(this, subSb, object);
			}
			
			subSb.append("\n");
		}
		
		QueryStringUtil.replaceRange(sb, "@[rule"+index+"]{", "@[rule"+index+"]}", subSb.toString());
	}
	
	
	
}
