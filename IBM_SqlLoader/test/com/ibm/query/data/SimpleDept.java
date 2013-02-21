package com.ibm.query.data;

import java.util.List;


public class SimpleDept {

	private int id = 0;
	
	private String name = null;
	
	private List<SimpleUser> users = null;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SimpleUser> getUsers() {
		return users;
	}

	public void setUsers(List<SimpleUser> users) {
		this.users = users;
	}
	
}
