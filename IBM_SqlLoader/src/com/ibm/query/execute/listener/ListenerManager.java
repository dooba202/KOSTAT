package com.ibm.query.execute.listener;

import java.util.ArrayList;
import java.util.List;

public class ListenerManager {

	private static ListenerManager manager = null;
	
	private List<IQueryExecuteListener> listeners = null;

	private ListenerManager() {
		listeners = new ArrayList<IQueryExecuteListener>();
	}

	public static ListenerManager getInstance(){
		synchronized (ListenerManager.class) {
			if(manager ==null){
				manager = new ListenerManager();
			}
			
			return manager;	
		}
	}
	
	
	public void addListener(IQueryExecuteListener listener){
		if(listener != null){
			listeners.add(listener);	
		}
	}
	
	public void removeListener(IQueryExecuteListener listener){
		listeners.remove(listener);
	}
	
	public void fireSqlExecuteEvent(String query){
		for (IQueryExecuteListener listener : listeners) {
			listener.doExecuteQuery(new QueryEvent());
		}
	}
}
