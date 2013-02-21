package com.ibm.query.data;

import java.util.List;

public class SimpleUser {

	private int id = 0;
	
	private String name = null;
	
	private List<String> roles = null;
	
	private List<SimpleRole> roles2 = null;

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
	
	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	

	public List<SimpleRole> getRoles2() {
		return roles2;
	}

	public void setRoles2(List<SimpleRole> roles2) {
		this.roles2 = roles2;
	}

	@Override
	public String toString() {
		return "SimpleUser [id=" + id + ", name=" + name + ", roles=" + roles
				+ ", roles2=" + roles2 + "]";
	}

	
}
