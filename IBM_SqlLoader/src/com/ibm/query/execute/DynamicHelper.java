package com.ibm.query.execute;

import java.sql.PreparedStatement;
import java.util.Iterator;

import com.ibm.query.exception.InvokeFailedException;
import com.ibm.query.exception.NotSupportedQueryException;
import com.ibm.query.exception.NotSupportedTypeException;
import com.ibm.query.execute.handler.ITypeHandler;
import com.ibm.query.execute.handler.TypeHandlerFactory;
import com.ibm.query.execute.visitor.QueryMakeVisitor;
import com.ibm.query.model.IHasQuery;
import com.ibm.query.model.IHasRule;
import com.ibm.query.model.ParamterType;
import com.ibm.query.rule.Rule;
import com.ibm.query.rule.RuleContext;
import com.ibm.query.utils.QueryStringUtil;


public class DynamicHelper {

	private static DynamicHelper instance = null;
	
	private QueryMakeVisitor visitor = null;

	private DynamicHelper() {
		visitor = new QueryMakeVisitor();
	}
	
	public static DynamicHelper getInstance(){
		if (instance != null) return instance;
		synchronized (DynamicHelper.class) {
			if(instance ==null){
				instance = new DynamicHelper();
			}
			
			return instance;	
		}
	}
	
	public RuleContext query(IHasQuery container, Object inputData) throws Exception{
		
		RuleContext context = new RuleContext();
		
		String query = container.getRawQuery();
		
		if(container instanceof IHasRule){
			IHasRule ruleContainer = (IHasRule)container;
			Iterator<Rule> rules = ruleContainer.getRules();
			
			context.setRuleContainer(ruleContainer);
			
			try {
				query = QueryStringUtil.replaceSpecificWord(ruleContainer, query, context, inputData);
				context.setQuery(query);
				visitor.setContext(context);
				
				while (rules.hasNext()) {
					Rule rule = (Rule) rules.next();
					
					rule.accept(visitor, inputData);
					
				}
				
				query = QueryStringUtil.findAndReplace(ruleContainer, context.getQuery(), context, inputData);
				context.setQuery(query);
			} finally {
				visitor.clear();
			}
		}
		
		return context;
	}
	
	public void execute(IHasQuery container, RuleContext context, PreparedStatement ps, Object inputData) throws Exception{
		if(container instanceof IHasRule){
			IHasRule ruleContainer = (IHasRule)container;
			
			execute(ruleContainer, context, ps, inputData);
		}else{
			throw new NotSupportedQueryException("this query don't support dynamic");
		}
	}
	
	private void execute(IHasRule ruleContainer, RuleContext context, PreparedStatement ps, Object inputData) throws Exception {
		
		int count = context.getQuestionCount();
		int parameterIndex = 1;
		for (int i = 0; i < count; i++) {
			String fieldName = context.getFieldName(parameterIndex);
			Object value = context.getFieldValue(parameterIndex);
			
			ParamterType parameterType = ruleContainer.getParameterType(context.getFieldType(fieldName));
			if(parameterType == null){
				throw new InvokeFailedException(ruleContainer + " : fieldname : "+ fieldName);
			}
			
			ITypeHandler handler = TypeHandlerFactory.getInstance().getTypeHandler(parameterType.getType());
			
			if(handler != null){
				handler.execute(ps, parameterType, parameterIndex, value);
			}else{
				throw new NotSupportedTypeException(parameterType.toString());
			}
			
			parameterIndex ++;
		}
	}
}
