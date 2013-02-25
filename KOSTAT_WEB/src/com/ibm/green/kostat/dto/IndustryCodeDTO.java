package com.ibm.green.kostat.dto;

public class IndustryCodeDTO {
	
	String id;
	
	String code;
	
	String name;
	
	String parent;

	// id is generated with code and parent. It's unique
	public String getId() {
		return (parent != null) ? parent + "." + code : code; 
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
