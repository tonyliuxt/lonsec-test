package com.lonsec.project.java;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Utility library
 * @author Tony Liu
 * @version 0.1
 */
public class CommUtil {
	
	/**
	 * Format date time and return date to yyyy-MM-dd
	 * @param timestr
	 * @return
	 */
	public static String formatDate(String timestr){
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		Date date = null;
		try{
			date = dateFormat.parse(timestr);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		DateFormat dateFormatOut = new SimpleDateFormat("yyyy-MM-dd");
		dateFormatOut.setTimeZone(TimeZone.getTimeZone("Australia/Victoria"));
		
    	return dateFormatOut.format(date);
	}
	
	/**
	 * Round input into 2 decimal
	 * @param input
	 * @return
	 */
	public static double roundDecimal(double input){
		double roundOff = Math.round(input * 100.0) / 100.0;
		return roundOff;
	}

}
