package com.ibm.green.kostat.dao;

import java.util.List;

import com.ibm.green.kostat.dto.JisuDTO;
import com.ibm.green.kostat.enums.JisuType;

/**
 * DAO Class for Jisu Query
 */
public interface JisuDAO {

	List<JisuDTO> getJisuListWithIndustryCode(JisuType jisuType, String sanId, String pumId, String fromYYYYMM, String toYYYYMM);
}
