package com.ibm.green.kostat.dao.sqlloader;

import java.util.HashMap;
import java.util.List;

import com.ibm.query.mapper.IStringMapper;
import com.ibm.query.utils.QueryStringUtil;

public class StringMapperUtils {

	public final static String YEAR_LIST = "kostat.yearList"; // reserved words
	public final static String MONTH_LIST = "kostat.monthList"; //reserved words
											
	
	static IStringMapper<HashMap<String, Object>> reservedWordsStringMapper = new IStringMapper<HashMap<String,Object>>() {

		@SuppressWarnings("unchecked")
		@Override
		public String replaceAll(String query, HashMap<String, Object> inputObject) {
			
			HashMap<String, String> outputMap = new HashMap<String, String>();
			//
			// extends known reserved variable.
			String org;
			
			// reserved var : $duringYear$
			// 2012 --> '201201' AND '201212'
			org = (String) inputObject.get("duringYear");
			if (org != null) {
				outputMap.put("duringYear",  "'" + org + "01' AND '" + org + "12'");
				inputObject.remove("duringYear");
			}

			// reserved var : $schoolCode$
			org = (String) inputObject.get("schoolCode");
			if (org != null) {
				outputMap.put("schoolCode",  "'" + org + "'");
				inputObject.remove("schoolCode");				
			}
			
			// reserved var : $sameMonthInPrev5Years$
			// 201205 -> ('200805', '200905', '201005', '201105', '201205')
			org = (String) inputObject.get("sameMonthInPrev5Years");
			if (org != null) {
				outputMap.put("sameMonthInPrev5Years", convertYYYYMMToSameMonthListDuringYears(org, 5));
				inputObject.remove("sameMonthInPrev5Years");				
			}
			
			// reserved var : $prev5Years$
			// 2012 -> ('2008', '2009', '2010', '2011')
			org = (String) inputObject.get("prev5Years"); 
			if (org != null) {
				outputMap.put("prev5Years", convertYYYYToPrevYearsList(org, 5));	
				inputObject.remove("prev5Years");				
			}
			
			// reserved var : $untilMonth$
			// 05 -> ('01', '02', '03', '04', '05')
			org = (String) inputObject.get("untilMonth"); 
			if (org != null) {
				outputMap.put("untilMonth", convertMMToUntilMonthList(org));	
				inputObject.remove("untilMonth");								
			}
			
			// reserved var : $maxMonth$
			org = (String) inputObject.get("maxMonth");
			if (org != null) {
				outputMap.put("maxMonth",  "'" + org + "'");
				inputObject.remove("maxMonth");								
			}
			
		
			// reserved var : $KoStat.yearList$
			// "2012", 5 --> ('2012', '2011', '2010', '2009', '2008')
			Object[] params = (Object[])inputObject.get(YEAR_LIST);
			if (params != null) {
				outputMap.put(YEAR_LIST, convertYYYYToPrevYearsList((String)params[0], (Integer)params[1]));
				inputObject.remove(YEAR_LIST);
			}
			
			// reserved var : $KoStat.monthList$
			// "01", "05" --> ('01', '02', '03', '04', '05')
			params = (Object[])inputObject.get(MONTH_LIST); 
			if (params != null) {
				outputMap.put(MONTH_LIST, convertMMMMtoMonthList((String)params[0], (String)params[1]));	
				inputObject.remove(MONTH_LIST);								
			}
			
			// move all remaining params
			for (String key : inputObject.keySet()) {
				outputMap.put(key, (String)inputObject.get(key));
			}
			
			return QueryStringUtil.findAndReplace(query, outputMap);
		}
	};
	
	private static String convertYYYYMMToSameMonthListDuringYears(String yyyymm, int years) {
		
		String yyyy = yyyymm.substring(0, 4);
		String mm = yyyymm.substring(4);
		
		int nYYYY = Integer.parseInt(yyyy);
		
		StringBuilder stb = new StringBuilder();
		stb.append("( ");
		for (int i = years - 1; i >= 0; i--) {
			if (i != years - 1) {
				stb.append(", ");
			}
			stb.append("'" + (nYYYY - i) + mm + "'");
		}
		
		stb.append(" )");
		return stb.toString();
	}
	
	private static String convertYYYYToPrevYearsList(String yyyy, int years) {
		
		int nYYYY = Integer.parseInt(yyyy);
		
		StringBuilder stb = new StringBuilder();
		stb.append("( ");
		for (int i = years - 1; i >= 0; i--) {
			if (i != years - 1) {
				stb.append(", ");
			}
			stb.append("'" + (nYYYY - i) + "'");
		}
		
		stb.append(" )");
		return stb.toString();	
	}
	
	private static String convertMMToUntilMonthList(String mm) {
		
		int nMM = Integer.parseInt(mm);
		
		StringBuilder stb = new StringBuilder();
		stb.append("( ");
		for (int i = 1; i <= nMM; i++) {
			if (i != 1) {
				stb.append(", ");
			}
			String strMM = "";
			if (i < 10) {
				strMM = "0";
			}
			strMM += i;
			stb.append("'" + strMM + "'");
		}
		
		stb.append(" )");
		return stb.toString();	
	}
	
	private static String convertMMMMtoMonthList(String startMM, String endMM) {
		
		int nStart = Integer.parseInt(startMM);
		int nEnd = Integer.parseInt(endMM);
		
		StringBuilder stb = new StringBuilder();
		stb.append("( ");
		for (int i = nStart; i <= nEnd; i++) {
			if (i != nStart) {
				stb.append(", ");
			}
			String strMM = "";
			if (i < 10) {
				strMM = "0";
			}
			strMM += i;
			stb.append("'" + strMM + "'");
		}
		
		stb.append(" )");
		return stb.toString();	
		
	}
	
	
	public static IStringMapper<HashMap<String, Object>> getReservedWordsStringMapper() {
		return reservedWordsStringMapper;
	}
}
