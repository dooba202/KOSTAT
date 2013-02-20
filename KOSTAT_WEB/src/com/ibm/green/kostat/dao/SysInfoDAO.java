package com.ibm.green.kostat.dao;

import java.util.Map;

import com.ibm.green.kostat.dto.SysInfoDTO;

/**
 * DAO class for System Information Table
 */
public interface SysInfoDAO extends GenericDAO<SysInfoDTO, String> {
	
	
	/**
	 * Retrieve all system information as <code>Map<String, String</code> object.
 	
	 * @return
	 */
	Map<String, String> retrieveAllSysInfo(); 

}
