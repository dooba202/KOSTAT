package com.ibm.green.kostat.dao;

import java.util.List;

import com.ibm.green.kostat.dto.MulyangDTO;
import com.ibm.green.kostat.enums.MulyangType;

public interface MulyangDAO {

	List<MulyangDTO> getMulyangListWithIndustryCode(MulyangType muyangType, String pumId, String saupId, String fromYYYYMM, String toYYYYMM);
}
