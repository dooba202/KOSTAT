package com.ibm.query.execute.manager;

import java.util.HashMap;
import java.util.Map;

import com.ibm.query.utils.QueryStringUtil;

public class ReplaceStringManager {

	private static ReplaceStringManager instance = null;

	private Map<String, String> stringMap = null;
	
	private ReplaceStringManager() {
		stringMap = new HashMap<String, String>();
	}
	
	public static ReplaceStringManager getInstance(){
		synchronized (ReplaceStringManager.class) {
			if(instance ==null){
				instance = new ReplaceStringManager();
			}	
			
			return instance;
		}
		
	}
	
	public void addReplceString(String from, String to){
		stringMap.put(from.toLowerCase(), to);
	}
	
	public String replaceAll(String query){
		String sSql = QueryStringUtil.findAndReplace("#",query, stringMap);
		
		return sSql;
	}
	
	public static int size(){
		return ReplaceStringManager.getInstance().stringMap.size();
	}
}
