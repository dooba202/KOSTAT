package com.ibm.query.data;

import java.util.List;


public class SimpleCompany {

	private int id = 0;
	
	private String name = null;
	
	private List<SimpleDept> depts = null;

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

	public List<SimpleDept> getDepts() {
		return depts;
	}

	public void setDepts(List<SimpleDept> depts) {
		this.depts = depts;
	}

	
}
