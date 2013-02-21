package com.ibm.query.mapper;

/**
  * IStringMapper.java
  * << description >>
  *
  * @author jaepil.jung
  * Property of IBM Korea, Copyrightⓒ. IBM Korea 2012 All Rights Reserved.
  */
public interface IStringMapper<T> {

	public String replaceAll(String query, T inputObject);

}
