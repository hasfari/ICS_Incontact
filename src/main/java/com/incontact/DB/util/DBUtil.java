package com.incontact.DB.util;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Session;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;

import com.incontact.DB.pojo.SYSMinMaxSysTeam;
import com.incontact.model.IncontactService;
import com.incontact.model.SysTeam;


/**
 * @author HAsfari added on Nov 10, 2014
 *
 *
 * DBUtil.java
 */

public class DBUtil {

	public static boolean desiredTeam(List<SysTeam> sysTeamsLst, BigDecimal teamId) {
	    for(SysTeam o : sysTeamsLst) {
	        if(o != null && o.getSysTeamId().equals(teamId)) {
	            return true;
	        }
	    }
	    return false;
	}

	public static SYSMinMaxSysTeam loadLookupMetaData(List<SysTeam> sysTeamsLst, IncontactService incontactService, JobExecutionContext jec, Session session) throws SchedulerException{
		
		SYSMinMaxSysTeam sysMinMaxSysTeam = new SYSMinMaxSysTeam();
		for (SysTeam sysTeams : sysTeamsLst){
			if (sysMinMaxSysTeam.getMinTime() == null || sysMinMaxSysTeam.getMinTime().after(sysTeams.getWsStartTime())){
				sysMinMaxSysTeam.setMinTime(sysTeams.getWsStartTime());
			}
			if (sysMinMaxSysTeam.getMaxTime() == null || sysMinMaxSysTeam.getMaxTime().before(sysTeams.getWsEndTime())){
				sysMinMaxSysTeam.setMaxTime(sysTeams.getWsEndTime());
			}
		}
		return sysMinMaxSysTeam;
	}
}
