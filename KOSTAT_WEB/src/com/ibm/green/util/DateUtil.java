package com.ibm.green.util;

import java.util.Calendar;
import java.util.Date;

import com.ibm.green.exception.ErrorCode;
import com.ibm.green.exception.GreenRuntimeException;

/**
 *  General Date utility class
 */
public class DateUtil {
	
	/**
	 * Return true if the given two dates belong to the same day.
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
	
		clearAllFieldsBelowDay(cal);
		date1 = cal.getTime();
		
		cal.setTime(date2);
		clearAllFieldsBelowDay(cal);
		date2 = cal.getTime();
		
		return date1.equals(date2);
	}
	
	static void clearAllFieldsBelowDay(Calendar cal) {
	    // clear all minor fields below day
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);		
	}
	
	/**
	 * Convert the given <code>date</code> into index based on <code>basedDate</code>.
	 * Each 15 minute will increase index by 1 from <code>basedDate</code>.
	 * 
	 * @param baseDate
	 * @param date
	 * @return
	 */
	public static int convertDateToIndexPer15Min(Date baseDate, Date date) {
		
		int nIndex = (int) (date.getTime() - baseDate.getTime()) / (15 * 60 * 1000);
		
		if (nIndex < 0) {
			throw new GreenRuntimeException(ErrorCode.COMMON_UNKNOWN, "Invalid parameter");
		}
		
		return nIndex;
	}

}
