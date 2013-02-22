package com.ibm.green.kostat.dao;

import java.util.List;

import com.ibm.green.kostat.dto.IndustryCodeDTO;
import com.ibm.green.kostat.dto.QueryStringDTO;
import com.ibm.green.kostat.enums.IndustryCodeType;

public interface IndustryCodeDAO {
	
	List<IndustryCodeDTO> getCodeList(IndustryCodeType codeType);
	
	List<IndustryCodeDTO> getInternalCodeList(IndustryCodeType codeType);
	
	List<QueryStringDTO> getQueryString(String sanId, String pumId, String saupId, String upDown);

}
