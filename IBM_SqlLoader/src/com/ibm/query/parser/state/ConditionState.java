package com.ibm.query.parser.state;

import org.xml.sax.SAXException;

import com.ibm.query.parser.XMLParserHandler;
import com.ibm.query.rule.Condition;

public abstract class ConditionState extends AbstractState implements IConditionState{
	
	protected Condition condition = null;
	
	public ConditionState(XMLParserHandler handler) {
		super(handler);
	}

	public AbstractState startElement(String tagName) throws SAXException{
		return StateFactory.getState(tagName, this, getHandler());
	}
	
	@Override
	public void setConditionIndex(int conditionIndex) {
		condition.setConditionIndex(conditionIndex);		
	}
	
	public boolean end() throws SAXException{
		String sql = getText().toString().trim();
		condition.setSubQuery(sql);
		
		return super.end();
	}
	
	public Condition createConditon() {
		if(condition == null){
			condition = getNewCondition();	
		}
		
		return condition;
	}
	
	protected abstract Condition getNewCondition();
		
	protected Condition getCondition(){
		return condition;
	}
	
	protected void setCondition(Condition condition){
		this.condition = condition;
	}
	
}
