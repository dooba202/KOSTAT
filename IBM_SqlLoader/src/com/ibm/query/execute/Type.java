package com.ibm.query.execute;


public enum Type {
	STRING("string"),
	TIMESTAMP("timestamp"),
	INT("int"),
	LONG("long"),
	OBJECT("object"),;
	
	private final String code;
	
	private Type(String code){
		this.code = code; 
	}
	
	public String value() {
        return code;
    }
	
	public static Type fromValue(String v) {
        for (Type c: Type.values()) {
            if (c.code.equals(v.toLowerCase())) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
