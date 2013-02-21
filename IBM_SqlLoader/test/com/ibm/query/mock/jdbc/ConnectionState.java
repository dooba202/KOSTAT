package com.ibm.query.mock.jdbc;

public class ConnectionState {

	private static int count = 0;
	
	public static void increaseCount(){
		count++;
	}
	
	public static void decreaseCount(){
		count--;
	}
	
	public static int getCount(){
		return count;
	}
}
