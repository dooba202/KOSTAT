package com.ibm.green.kostat.dto;

/**
 * Data Transfer Object for Index of JISU / MULYANG
 */
public class JisuDTO {
	
	String sanId;
	
	String pumId;
	
	String yyyymm;
	
	String jisuType;
	
	String jisuName;
	
	double value;
	
	long secTime;

	public String getSanId() {
		return sanId;
	}

	public void setSanId(String sanId) {
		this.sanId = sanId;
	}

	public String getPumId() {
		return pumId;
	}

	public void setPumId(String pumId) {
		this.pumId = pumId;
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
