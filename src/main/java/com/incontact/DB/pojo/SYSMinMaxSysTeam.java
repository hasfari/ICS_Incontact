package com.incontact.DB.pojo;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * @author HAsfari added on Nov 10, 2014
 *
 *
 * SYSMinMaxSysTeam.java
 */

public class SYSMinMaxSysTeam {
	Timestamp minTime = null;
	Timestamp maxTime = null;
	Calendar minMaxMonth = GregorianCalendar.getInstance();

	public int getMinHour() {
		minMaxMonth.setTime(minTime);
		return minMaxMonth.get(minMaxMonth.HOUR_OF_DAY);
	}

	public int getMinMinute() {
		minMaxMonth.setTime(minTime);
		return minMaxMonth.get(minMaxMonth.MINUTE);
	}

	public void setMinTime(Timestamp minTime) {
		this.minTime = minTime;
	}

	public int getMaxHour() {
		minMaxMonth.setTime(maxTime);
		return minMaxMonth.get(minMaxMonth.HOUR_OF_DAY);
	}

	public int getMaxMinute() {
		minMaxMonth.setTime(maxTime);
		return minMaxMonth.get(minMaxMonth.MINUTE);
	}

	public void setMaxTime(Timestamp maxTime) {
		this.maxTime = maxTime;
	}

	public Timestamp getMinTime() {
		return minTime;
	}

	public Timestamp getMaxTime() {
		return maxTime;
	}
};