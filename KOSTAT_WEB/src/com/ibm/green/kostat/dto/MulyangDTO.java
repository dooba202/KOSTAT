package com.ibm.green.kostat.dto;

public class MulyangDTO implements JisuValue {

	String pumId;
	
	String saupId;
	
	String yyyymm;
	
	String jisuType;
	
	String jisuName;
	
	double value;
	
	long secTime;

	public String getPumId() {
		return pumId;
	}

	public void setPumId(String pumId) {
		this.pumId = pumId;
	}

	public String getSaupId() {
		return saupId;
	}

	public void setSaupId(String saupId) {
		this.saupId = saupId;
	}

	public String getYyyymm() {
		return yyyymm;
	}

	public void setYyyymm(String yyyymm) {
		this.yyyymm = yyyymm;
	}

	public String getJisuType() {
		return jisuType;
	}

	public void setJisuType(String jisuType) {
		this.jisuType = jisuType;
	}

	public String getJisuName() {
		return jisuName;
	}

	public void setJisuName(String jisuName) {
		this.jisuName = jisuName;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public long getSecTime() {
		return secTime;
	}

	public void setSecTime(long secTime) {
		this.secTime = secTime;
	}	
}
