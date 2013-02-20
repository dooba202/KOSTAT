package com.ibm.green.kostat.dao;

import java.util.List;

import com.ibm.green.kostat.dto.JisuDTO;

/**
 * DAO Class for Jisu Query
 */
public interface JisuDAO {

	List<JisuDTO> getJisuListWithIndustryCode(String sanId, String pumId, String fromYYYYMM, String toYYYYMM);
}
