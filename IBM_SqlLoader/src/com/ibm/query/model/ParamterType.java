package com.ibm.query.model;

public class ParamterType {

	private String name = null;
	
	private String method = null;
	
	private String type = null;
	
	private String value = null;
	
	private String sensitive = null;
	
	private String format = null;
	
	private String escape = null;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getSensitive() {
		return sensitive;
	}

	public void setSensitive(String sensitive) {
		this.sensitive = sensitive;
	}

	public String getEscape() {
		return escape;
	}

	public void setEscape(String escape) {
		this.escape = escape;
	}

	public boolean isTypeEmpty(){
		if(type==null){
			return true;
		}
		
		if(type.isEmpty()){
			return true;
		}
		
		return false;
	}
	
	public boolean isSensitiveEmpty(){
		if(sensitive==null){
			return true;
		}
		
		if(sensitive.isEmpty()){
			return true;
		}
		
		return false;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@Override
	public String toString() {
		return "ParamterType [name=" + name + ", method=" + method + ", type="
				+ type + ", value=" + value + ", sensitive=" + sensitive
				+ ", format=" + format + "]";
	}

	
}
