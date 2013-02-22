package com.ibm.green.kostat.dao;

import java.util.List;

import com.ibm.green.kostat.dto.IndustryCodeDTO;
import com.ibm.green.kostat.enums.IndustryCodeType;

public interface IndustryCodeDAO {
	
	List<IndustryCodeDTO> getCodeList(IndustryCodeType codeType);

}
