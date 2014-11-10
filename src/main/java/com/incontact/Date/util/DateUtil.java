package com.incontact.Date.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;


/**
 * @author HAsfari added on Nov 10, 2014
 *
 *
 * DateUtil.java
 */

public class DateUtil {

	
	
	static final Map<String, String> DATE_FORMAT_REGEXPS = new HashMap<String, String>() {
		{
			put("^\\d{8}$", "yyyyMMdd");
			put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
			put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
			put("^\\d{1,2}\\/\\d{1,2}\\/\\d{4}$", "MM/dd/yyyy");
			put("^\\d{4}\\/\\d{1,2}\\/\\d{1,2}$", "yyyy/MM/dd");
			put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
			put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
			put("^\\d{12}$", "yyyyMMddHHmm");
			put("^\\d{8}\\s\\d{4}$", "yyyyMMdd HHmm");
			put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$", "dd-MM-yyyy HH:mm");
			put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy-MM-dd HH:mm");
			put("^\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{2}:\\d{2}Z$", "yyyy-MM-dd HH:mm:ssZ");
			put("^\\d{1,2}\\/\\d{1,2}\\/\\d{4}\\s\\d{1,2}:\\d{2}$", "MM/dd/yyyy HH:mm");
			put("^\\d{4}\\/\\d{1,2}\\/\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy/MM/dd HH:mm");
			put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMM yyyy HH:mm");
			put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMMM yyyy HH:mm");
			put("^\\d{14}$", "yyyyMMddHHmmss");
			put("^\\d{8}\\s\\d{6}$", "yyyyMMdd HHmmss");
			put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd-MM-yyyy HH:mm:ss");
			put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss");
			put("^\\d{1,2}\\/\\d{1,2}\\/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "MM/dd/yyyy HH:mm:ss");
			put("^\\d{1,2}\\/\\d{1,2}\\/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}\\s[A-Za-z]{2}$", "MM/dd/yyyy HH:mm:ss a");
			put("^\\d{4}\\/\\d{1,2}\\/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss");
			put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMM yyyy HH:mm:ss");
			put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMMM yyyy HH:mm:ss");
		}
	};

	
	public static <T> BigDecimal checkIfDecimalIsNull(T inNumber) {
		if (inNumber instanceof String) {
			if (StringUtils.isEmpty((String) inNumber))
				return null;
			return new BigDecimal((String) inNumber);
		} else if (inNumber instanceof Double) {
			return new BigDecimal((Double) inNumber);
		}
		return new BigDecimal(0);
	}
	
	public static Double convert8601TimeToSeconds(String inTimeStr) {
		double minutes = 0;
		double seconds = 0;
		if (!StringUtils.contains(inTimeStr, "PT")) {
			return 0.0;
		}
		inTimeStr = StringUtils.replace(inTimeStr, "PT", "");
		if (StringUtils.contains(inTimeStr, "M") && StringUtils.contains(inTimeStr, "S")) {
			minutes = Double.valueOf(inTimeStr.split("M")[0]);
			seconds = Double.valueOf(inTimeStr.split("M")[1].split("S")[0]);
		} else if (StringUtils.contains(inTimeStr, "M") && !StringUtils.contains(inTimeStr, "S")) {
			minutes = Double.valueOf(inTimeStr.split("M")[0]);
		} else if (!StringUtils.contains(inTimeStr, "M") && StringUtils.contains(inTimeStr, "S")) {
			seconds = Double.valueOf(inTimeStr.split("S")[0]);
		}
		return minutes * 60 + seconds;
	}
	
	public static Boolean isWeekEnds(Date dateIn){
		Calendar now = Calendar.getInstance();
		now.setTime(dateIn);
		int day = now.get(Calendar.DAY_OF_WEEK);
		if (day >= Calendar.MONDAY && day <= Calendar.FRIDAY)
			return false;
		return true;
	}

	public static Timestamp checkIfTimestampIsNull(String inTime) {
		Calendar calendar = null;
		if (StringUtils.isEmpty(inTime))
			return null;
		try{
			calendar = javax.xml.bind.DatatypeConverter.parseDateTime(inTime);
			return new Timestamp(calendar.getTime().getTime());
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public static Date checkIfDateIsNull(String inDate) throws ParseException {
		if (StringUtils.isEmpty(inDate))
			return null;
		String dateFormat = determineDateFormat(inDate);
		if (dateFormat != null) {
			return new SimpleDateFormat(dateFormat).parse(inDate);
		}
		return null;
	}

	static String determineDateFormat(String dateString) {
		for (String regexp : DateUtil.DATE_FORMAT_REGEXPS.keySet()) {
			if (dateString.matches(regexp)) {
				return DateUtil.DATE_FORMAT_REGEXPS.get(regexp);
			}
		}
		return null;
	}
	
}