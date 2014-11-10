package com.incontact.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.quartz.StatefulJob;
import org.springframework.scheduling.quartz.CronTriggerBean;

import com.incontact.DB.pojo.SYSMinMaxSysTeam;
import com.incontact.DB.util.DBUtil;
import com.incontact.Data.util.ObjectMapperUtil;
import com.incontact.Date.util.DateUtil;
import com.incontact.model.AgentApiTypeEnum;
import com.incontact.model.AgentIdStates;
import com.incontact.model.AgentPerformance;
import com.incontact.model.AgentStateHistory;
import com.incontact.model.IncontactService;
import com.incontact.model.InteractionHistory;
import com.incontact.model.SkillSummary;
import com.incontact.model.SysAgentApiType;
import com.incontact.model.SysAgentMainLog;
import com.incontact.model.SysAgentSchdl;
import com.incontact.model.SysTeam;
import com.incontact.spring.ApplicationContextAwareProvider;
import com.incontact.ws.IIncontactPerformanceWS;
import com.incontact.ws.impl.IncontactPerformanceWSImpl;
import com.incontact.ws.pojo.TokenResponse;


/**
 * @author HAsfari added on Nov 10, 2014
 *
 *
 * IncontactJob.java
 */

public class IncontactJob implements StatefulJob  {

	public IncontactService incontactService;
	public ApplicationContextAwareProvider applicationContextAwareProvider;
	private SessionFactory sessionFactory;
	public IIncontactPerformanceWS incontactPerformanceWS;

	public static final int ONE_HOUR = 60 * 60 * 1000;
	public static final int THIRTY_SECONDS = 30 * 1000;
	public static final int THIRTY_MINUTES = 30 * 60 * 1000;
	public static final int ONE_DAY = 24 * 60 * 60 * 1000;
	
	List<SysTeam> sysTeamsLst = null;

	SYSMinMaxSysTeam sysMinMaxSysTeam = null;
	
	public void execute(JobExecutionContext jCurrExecContext) {

        getInitializedBeans();
		TokenResponse tokenResponse = null;
		Session session = null;
		try {
			Date currentDate = new Date();
			Date tokenStartDate = (Date) jCurrExecContext.getScheduler().getContext().get("token.stare.date");
			Integer tokenExpire = (Integer) jCurrExecContext.getScheduler().getContext().get("token.exipre");
			tokenResponse = (TokenResponse) jCurrExecContext.getScheduler().getContext().get("token.response");
			CronTriggerBean cronTrigger = (CronTriggerBean) jCurrExecContext.getTrigger();
			if (tokenResponse == null || tokenStartDate != null && (currentDate.getTime() / 1000) - (tokenStartDate.getTime() / 1000) >= tokenExpire - 60){
				tokenResponse = ((IncontactPerformanceWSImpl) incontactPerformanceWS).getToken();
				jCurrExecContext.getScheduler().getContext().put("token.exipre", tokenResponse.getExpiresIn());
				jCurrExecContext.getScheduler().getContext().put("token.stare.date", new Date());
				jCurrExecContext.getScheduler().getContext().put("token.response", tokenResponse);
			}
			session = sessionFactory.openSession();
			session.getTransaction().begin();
			if (sysMinMaxSysTeam == null || sysTeamsLst == null || sysTeamsLst.size() == 0){
				sysTeamsLst = (List<SysTeam>) jCurrExecContext.getScheduler().getContext().get("lookup.sysTeams");
				if (sysTeamsLst == null || sysTeamsLst.size() == 0){
					sysTeamsLst = incontactService.findSysTeams(session);
					jCurrExecContext.getScheduler().getContext().put("lookup.sysTeams", sysTeamsLst);
				}
				sysMinMaxSysTeam = DBUtil.loadLookupMetaData(sysTeamsLst, incontactService, jCurrExecContext, session);
			}
			executeIncontactAPI(tokenResponse, session, jCurrExecContext);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			if (session!=null && session.getTransaction() != null){
				if (!session.getTransaction().wasRolledBack())
					session.getTransaction().rollback();
			}
			if (!session.isConnected()){
				 Configuration configuration = new Configuration();
		            configuration.configure("hibernate.cfg.xml");
		            ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder().applySettings(configuration.getProperties());
		            sessionFactory = configuration.buildSessionFactory(serviceRegistryBuilder.buildServiceRegistry());
			}
		}
	}

	private void getInitializedBeans() {
		if (sessionFactory == null)
			sessionFactory = (SessionFactory) ApplicationContextAwareProvider.getApplicationContext().getBean("sessionFactory");
		if (incontactPerformanceWS == null)
			incontactPerformanceWS = (IncontactPerformanceWSImpl) applicationContextAwareProvider.getApplicationContext().getBean("incontactPerformanceWS");
		if (incontactService == null){
			incontactService = (IncontactService) applicationContextAwareProvider.getApplicationContext().getBean("incontactService");
		}
	}

	
	private void executeIncontactAPI(TokenResponse tokenResponse, Session session, JobExecutionContext jec) throws ParseException, SchedulerException, IOException {
		CronTriggerBean cronTrigger = (CronTriggerBean) jec.getTrigger();
		SysAgentMainLog sysAgentMainLog = null;
		if ("incontactPerformanceTrigger".equals(cronTrigger.getName())){
			populatePerformance(jec, tokenResponse, session);
		}
		else if ("incontactIntractionHistoryTrigger".equals(cronTrigger.getName())){
			populateInteractionHistory(jec, tokenResponse, session);
		}
		else if ("incontactStatesHistoryByAgentTrigger".equals(cronTrigger.getName())){
			populateStatesHistoryByAgent(jec, tokenResponse, session);
		}
		else 
		if ("incontactSkillSummaryTrigger".equals(cronTrigger.getName())){
			populatepopulateSkillSummary(jec, tokenResponse, session);
		}
		else 
		if ("incontactAgentIdStatesTrigger".equals(cronTrigger.getName())){
			List<SysAgentMainLog> agentIdStatesLogLst = incontactService.findLatestLog(session, AgentApiTypeEnum.AGENTS_ID_STATES.getValue());
			sysAgentMainLog = populateAgentIdStatesLog(tokenResponse, session, jec, agentIdStatesLogLst);
			if (sysAgentMainLog == null)
				return;
			populateAgentIdStates(jec, tokenResponse, session, sysAgentMainLog);
		}
		if(!session.getTransaction().wasCommitted())
			if (session != null){
				session.flush();
				session.clear();
				session.getTransaction().commit();
				if (session.isOpen())
					session.close(); //implicit commit is applied here instead of using session.getTransaction().commit();
			}
	}


	private SysAgentMainLog populateAgentIdStatesLog(TokenResponse tokenResponse, Session session, JobExecutionContext jec, List<SysAgentMainLog> agentIdStatesLogLst)
			throws SchedulerException, ParseException {
		SysAgentMainLog agentMainLog = manageAgentIDStatesLog(tokenResponse, jec, session, agentIdStatesLogLst, AgentApiTypeEnum.AGENTS_ID_STATES);
		return agentMainLog;
	}
	
	private Boolean checkIfOutSideHours(Date inDate){
		Calendar inCal = GregorianCalendar.getInstance();
		inCal.setTime(inDate);
		if ( (inCal.get(Calendar.HOUR_OF_DAY) > sysMinMaxSysTeam.getMaxHour() || (inCal.get(Calendar.HOUR_OF_DAY) == sysMinMaxSysTeam.getMaxHour() && inCal.get(Calendar.MINUTE) > sysMinMaxSysTeam.getMaxMinute()))  
				|| (inCal.get(Calendar.HOUR_OF_DAY) < sysMinMaxSysTeam.getMinHour() || (inCal.get(Calendar.HOUR_OF_DAY) == sysMinMaxSysTeam.getMinHour() && inCal.get(Calendar.MINUTE) < sysMinMaxSysTeam.getMinMinute()) ))
			return true;
		return false;
	}

	private SysAgentMainLog manageAgentIDStatesLog(TokenResponse tokenResponse, JobExecutionContext jec, Session session, List<SysAgentMainLog> agentPerformanceLogLst, AgentApiTypeEnum agentApiTypeEnum) throws SchedulerException, ParseException {
		Timestamp startCallTime;
		Timestamp endCallTime;
		SysAgentMainLog agentMainLog;
		Date currentTime = new Date();
		if (DateUtil.isWeekEnds(currentTime))
			return null;
		if (agentPerformanceLogLst.size() == 0) {
			Calendar sysLogCal = GregorianCalendar.getInstance();
			sysLogCal.set(Calendar.HOUR_OF_DAY, sysMinMaxSysTeam.getMinHour());
			sysLogCal.set(Calendar.MINUTE, sysMinMaxSysTeam.getMinMinute());
			sysLogCal.set(Calendar.SECOND, 0);
			sysLogCal.set(Calendar.MILLISECOND, 0);
			while (true){
				startCallTime = new Timestamp(sysLogCal.getTime().getTime());
				sysLogCal.add(Calendar.SECOND, 30);
				if (DateUtil.isWeekEnds(startCallTime) || checkIfOutSideHours(startCallTime))
					continue;
				break;
			}
			if (startCallTime.after(currentTime))
				return null;
			endCallTime = new Timestamp(sysLogCal.getTime().getTime());
			agentMainLog = new SysAgentMainLog();
			SysAgentApiType sysAgentApiType = (SysAgentApiType) incontactService.findOne(SysAgentApiType.class, session, agentApiTypeEnum.getValue());
			agentMainLog.setSysLogReqStartTime(new Timestamp(startCallTime.getTime()));
			agentMainLog.setSysLogReqEndTime(new Timestamp(endCallTime.getTime()));
			agentMainLog.setSysUpdateTime(new Timestamp(new Date().getTime()));
			incontactService.createEntity(session, agentMainLog);
		} else {
			SysAgentMainLog agentPerformanceLogExisting = agentPerformanceLogLst.get(0);
			Date sysLogReqEndTime = new Date(agentPerformanceLogExisting.getSysLogReqEndTime().getTime());
			if (sysLogReqEndTime.after(currentTime))
				return null;
			Calendar sysLogCal = GregorianCalendar.getInstance();
			sysLogCal.setTime(sysLogReqEndTime);
			sysLogCal.add(Calendar.MINUTE, 1);	
			if (DateUtil.isWeekEnds(sysLogCal.getTime()) || checkIfOutSideHours(sysLogCal.getTime())){
				while (true){
					sysLogCal.add(Calendar.SECOND, 30);	
					if (DateUtil.isWeekEnds(sysLogCal.getTime()) || checkIfOutSideHours(sysLogCal.getTime())){
						continue;
					}
					break;
				}
				sysLogReqEndTime.setTime(sysLogCal.getTime().getTime());
			}
			if (DateUtil.isWeekEnds(sysLogReqEndTime) || sysLogReqEndTime.after(currentTime) || checkIfOutSideHours(sysLogReqEndTime))
				return null;
			agentMainLog = new SysAgentMainLog();
			SysAgentApiType sysAgentApiType = (SysAgentApiType) incontactService.findOne(SysAgentApiType.class, session, agentApiTypeEnum.getValue());
			agentMainLog.setSysLogReqStartTime(new Timestamp(sysLogReqEndTime.getTime()));
			agentMainLog.setSysLogReqEndTime(new Timestamp(sysLogReqEndTime.getTime() + THIRTY_SECONDS));
			System.out.println("Main start : " + agentMainLog.getSysLogReqStartTime() + ", End: " + agentMainLog.getSysLogReqEndTime());			
			agentMainLog.setSysUpdateTime(new Timestamp(new Date().getTime()));
			incontactService.createEntity(session, agentMainLog);
		}
		return agentMainLog;
	}
	
	
	private void populateStatesHistoryByAgent(JobExecutionContext jec, TokenResponse tokenResponse, Session session) throws JSONException, ParseException, SchedulerException, IOException {

		List<SysAgentMainLog> SysAgentMainLogLst = incontactService.getMissingLogsEntries_2(session, AgentIdStates.class, AgentApiTypeEnum.AGENTS_ID_STATES.getValue(), AgentApiTypeEnum.AGENT_ID_STATE_HISTORY.getValue(), 30, "second");
		if (!shallContinue(jec, session, SysAgentMainLogLst, AgentApiTypeEnum.AGENT_ID_STATE_HISTORY))
			return;
		SysAgentMainLog agentMainLog = new SysAgentMainLog();
		SysAgentApiType sysAgentApiType = (SysAgentApiType) incontactService.findOne(SysAgentApiType.class, session, AgentApiTypeEnum.AGENT_ID_STATE_HISTORY.getValue());
		agentMainLog.setSysLogReqStartTime(SysAgentMainLogLst.get(0).getSysLogReqStartTime());
		agentMainLog.setSysLogReqEndTime(SysAgentMainLogLst.get(0).getSysLogReqEndTime());
		System.out.println("StatesHistoryByAgent start : " + SysAgentMainLogLst.get(0).getSysLogReqStartTime() + ", End: " + SysAgentMainLogLst.get(0).getSysLogReqEndTime());
		agentMainLog.setSysUpdateTime(new Timestamp(new Date().getTime()));
		incontactService.createEntity(session, agentMainLog);
		List<AgentStateHistory> agentStateHistoryLst = new ArrayList<AgentStateHistory>();
		List<AgentIdStates> agentIdStatesLst = incontactService.getMissingAgentIdStates(session, AgentIdStates.class, AgentApiTypeEnum.AGENTS_ID_STATES.getValue(), AgentApiTypeEnum.AGENT_ID_STATE_HISTORY.getValue(), 30, "second");
		if (agentIdStatesLst.size() == 0){
			return;
		}
		for (AgentIdStates agentIdStates : agentIdStatesLst) {
			if (!DBUtil.desiredTeam(sysTeamsLst, agentIdStates.getTeamId()))
				continue;
			ObjectMapperUtil.populateStatesHistoryByAgentList(incontactPerformanceWS, incontactService, tokenResponse, session, agentMainLog, agentStateHistoryLst, agentIdStates);
		}
		incontactService.createEntities(session, agentStateHistoryLst);
		agentStateHistoryLst.clear();
	}

	private void populateAgentIdStates(JobExecutionContext jec, TokenResponse tokenResponse, Session session, SysAgentMainLog sysAgentPerformanceLog) throws JSONException, ParseException, SchedulerException {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String rspns = ((IncontactPerformanceWSImpl) incontactPerformanceWS).getAgentIdStates(tokenResponse, dateFormatter.format(sysAgentPerformanceLog.getSysLogReqStartTime()), dateFormatter.format(sysAgentPerformanceLog.getSysLogReqEndTime()));
		List<AgentIdStates> agentIdStatesLst = new ArrayList<AgentIdStates>();
		if (!StringUtils.isEmpty(rspns)) {
			JSONObject jsonArrayRspnse = new JSONObject(rspns);
			for (int indx = 0; indx < jsonArrayRspnse.getJSONArray("agentStates").length(); indx++) {
				ObjectMapperUtil.populateAgentIdStatesList(sysTeamsLst, incontactService, agentIdStatesLst, sysAgentPerformanceLog, rspns, jsonArrayRspnse, indx, session);
			}
		}
		incontactService.createEntities(session, agentIdStatesLst);
		agentIdStatesLst.clear();
	}

	private void populateInteractionHistory(JobExecutionContext jec, TokenResponse tokenResponse, Session session) throws ParseException, SchedulerException, IOException {
		List<SysAgentMainLog> SysAgentMainLogLst = incontactService.getMissingLogsEntries(session, AgentIdStates.class, AgentApiTypeEnum.AGENTS_ID_STATES.getValue(), AgentApiTypeEnum.AGENT_ID_INTERACTION_HISTORY.getValue(), 15, "minute");
		if (!shallContinue(jec, session, SysAgentMainLogLst, AgentApiTypeEnum.AGENT_ID_INTERACTION_HISTORY))
			return;
		SysAgentMainLog agentMainLog = new SysAgentMainLog();
		SysAgentApiType sysAgentApiType = (SysAgentApiType) incontactService.findOne(SysAgentApiType.class, session, AgentApiTypeEnum.AGENT_ID_INTERACTION_HISTORY.getValue());
		agentMainLog.setSysLogReqStartTime(SysAgentMainLogLst.get(0).getSysLogReqStartTime());
		agentMainLog.setSysLogReqEndTime(SysAgentMainLogLst.get(0).getSysLogReqEndTime());
		agentMainLog.setSysUpdateTime(new Timestamp(new Date().getTime()));
		System.out.println("InteractionHistory start : " + SysAgentMainLogLst.get(0).getSysLogReqStartTime() + ", End: " + SysAgentMainLogLst.get(0).getSysLogReqEndTime());
		incontactService.createEntity(session, agentMainLog);
		List<InteractionHistory> interactionHistoryLst = new ArrayList<InteractionHistory>();
		List<AgentIdStates> agentIdStatesLst = incontactService.getMissingAgentIdStates(session, AgentIdStates.class, AgentApiTypeEnum.AGENTS_ID_STATES.getValue(), AgentApiTypeEnum.AGENT_ID_INTERACTION_HISTORY.getValue(), 15, "minute");
		if (agentIdStatesLst.size() == 0){
			return;
		}
		for (AgentIdStates agentIdStates : agentIdStatesLst) {
			if (!DBUtil.desiredTeam(sysTeamsLst, agentIdStates.getTeamId()))
				continue;
			ObjectMapperUtil.populateInteractionHistoryList(incontactPerformanceWS, incontactService, tokenResponse, session, agentMainLog, interactionHistoryLst, agentIdStates);
		}
		incontactService.createEntities(session, interactionHistoryLst);
		interactionHistoryLst.clear();
	}
	
	private void populatePerformance(JobExecutionContext jec, TokenResponse tokenResponse, Session session) throws ParseException, SchedulerException, IOException {

		List<SysAgentMainLog> SysAgentMainLogLst = incontactService.getMissingLogsEntries(session, AgentIdStates.class, AgentApiTypeEnum.AGENTS_ID_STATES.getValue(), AgentApiTypeEnum.AGENT_ID_PERFORMANCE.getValue(), 15, "minute"); 
		if (!shallContinue(jec, session, SysAgentMainLogLst, AgentApiTypeEnum.AGENT_ID_PERFORMANCE))
			return;
		SysAgentMainLog agentMainLog = new SysAgentMainLog();
		SysAgentApiType sysAgentApiType = (SysAgentApiType) incontactService.findOne(SysAgentApiType.class, session, AgentApiTypeEnum.AGENT_ID_PERFORMANCE.getValue());
		agentMainLog.setSysLogReqStartTime(SysAgentMainLogLst.get(0).getSysLogReqStartTime());
		agentMainLog.setSysLogReqEndTime(SysAgentMainLogLst.get(0).getSysLogReqEndTime());
		System.out.println("Performance start : " + SysAgentMainLogLst.get(0).getSysLogReqStartTime() + ", End: " + SysAgentMainLogLst.get(0).getSysLogReqEndTime());
		agentMainLog.setSysUpdateTime(new Timestamp(new Date().getTime()));
		incontactService.createEntity(session, agentMainLog);
		List<AgentPerformance> agentPerformanceLst = new ArrayList<AgentPerformance>();
		List<AgentIdStates> agentIdStatesLst = incontactService.getMissingAgentIdStates(session, AgentIdStates.class, AgentApiTypeEnum.AGENTS_ID_STATES.getValue(), AgentApiTypeEnum.AGENT_ID_PERFORMANCE.getValue(), 15, "minute");
		if (agentIdStatesLst.size() == 0){
			return;
		}
		for (AgentIdStates agentStatesId : agentIdStatesLst) {
			if (!DBUtil.desiredTeam(sysTeamsLst, agentStatesId.getTeamId()))
				continue;
			ObjectMapperUtil.populatePerformanceList(incontactPerformanceWS, incontactService, tokenResponse, session, agentMainLog, agentPerformanceLst, agentStatesId);
		}
		incontactService.createEntities(session, agentPerformanceLst);
		agentPerformanceLst.clear();
	}

	private void populatepopulateSkillSummary(JobExecutionContext jec, TokenResponse tokenResponse, Session session) throws ParseException, SchedulerException, IOException {
		List<SysAgentMainLog> SysAgentMainLogLst = incontactService.getMissingLogsEntries(session, AgentIdStates.class, AgentApiTypeEnum.AGENTS_ID_STATES.getValue(), AgentApiTypeEnum.AGENT_SKILLS_SUMMARY.getValue(), 60, "minute");
		if (!shallContinue(jec, session, SysAgentMainLogLst, AgentApiTypeEnum.AGENT_SKILLS_SUMMARY))
			return;
		SysAgentMainLog agentMainLog = new SysAgentMainLog();
		SysAgentApiType sysAgentApiType = (SysAgentApiType) incontactService.findOne(SysAgentApiType.class, session, AgentApiTypeEnum.AGENT_SKILLS_SUMMARY.getValue());
		agentMainLog.setSysLogReqStartTime(SysAgentMainLogLst.get(0).getSysLogReqStartTime());
		agentMainLog.setSysLogReqEndTime(SysAgentMainLogLst.get(0).getSysLogReqEndTime());
		System.out.println("SkillSummary start : " + SysAgentMainLogLst.get(0).getSysLogReqStartTime() + ", End: " + SysAgentMainLogLst.get(0).getSysLogReqEndTime());
		agentMainLog.setSysUpdateTime(new Timestamp(new Date().getTime()));
		incontactService.createEntity(session, agentMainLog);
		List<SkillSummary> skillSummaryLst = new ArrayList<SkillSummary>();
		ObjectMapperUtil.populateSkillSummaryList(incontactPerformanceWS, incontactService, tokenResponse, session, agentMainLog, skillSummaryLst);
		incontactService.createEntities(session, skillSummaryLst);
		skillSummaryLst.clear();
	}

	private boolean shallContinue(JobExecutionContext jec, Session session, List<SysAgentMainLog> SysAgentMainLogLst, AgentApiTypeEnum agentApiTypeEnum) throws ParseException, SchedulerException {
		Date currentTime = new Date();
		Date sysLogReqEndTime = currentTime;

		if (SysAgentMainLogLst != null && SysAgentMainLogLst.size() > 0){
			SysAgentMainLog agentPerformanceLogExisting = SysAgentMainLogLst.get(0);
			sysLogReqEndTime = new Date(agentPerformanceLogExisting.getSysLogReqEndTime().getTime());
			if (DateUtil.isWeekEnds(currentTime) || sysLogReqEndTime.after(currentTime))
				return false;
			long timeDiff = (currentTime.getTime() - sysLogReqEndTime.getTime());
			if (timeDiff <= ONE_DAY) {
				jec.getScheduler().standby();
				CronTriggerBean cronTrigger = (CronTriggerBean) jec.getTrigger();
				SysAgentSchdl agentSchedule = (SysAgentSchdl) incontactService.findPerformanceSchedule(session, agentApiTypeEnum.getValue()).get(0);
				cronTrigger.setCronExpression("0/"+ agentSchedule.getSysSchdlIntrvl() + " * * * * ?");
				jec.getScheduler().addJob(jec.getJobDetail(), true);
				jec.getScheduler().rescheduleJob(cronTrigger.getName(), cronTrigger.getGroup(), cronTrigger);
				jec.getScheduler().start();
				return false;
			}else{
				CronTriggerBean cronTrigger = (CronTriggerBean) jec.getTrigger();
				if (timeDiff > ONE_DAY && !cronTrigger.getCronExpression().equals((String) jec.getJobDetail().getJobDataMap().get("original.reschedule"))){
					jec.getScheduler().standby();
					cronTrigger.setCronExpression((String) jec.getJobDetail().getJobDataMap().get("original.reschedule"));
					jec.getScheduler().addJob(jec.getJobDetail(), true);
					jec.getScheduler().rescheduleJob(cronTrigger.getName(), cronTrigger.getGroup(), cronTrigger);
					jec.getScheduler().start();
				}
			}
			if (DateUtil.isWeekEnds(sysLogReqEndTime) || checkIfOutSideHours(sysLogReqEndTime)){
				Calendar sysLogCal = GregorianCalendar.getInstance();
				sysLogCal.setTime(sysLogReqEndTime);
				while (true){
					sysLogCal.add(Calendar.SECOND, 30);
					if (DateUtil.isWeekEnds(sysLogCal.getTime()) || checkIfOutSideHours(sysLogCal.getTime())){
						continue;
					}
					break;
				}
				sysLogReqEndTime.setTime(sysLogCal.getTime().getTime());
				timeDiff = currentTime.getTime() - sysLogReqEndTime.getTime();
				if (timeDiff <= ONE_DAY)
					return false;
			}
		}else
			return false;
		
		if (sysLogReqEndTime.after(currentTime) || checkIfOutSideHours(sysLogReqEndTime))
			return false;		

		return true;
	}
	
	public void setApplicationContextAwareProvider(ApplicationContextAwareProvider applicationContextAwareProvider) {
		this.applicationContextAwareProvider = applicationContextAwareProvider;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;

	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setIncontactPerformanceWS(IIncontactPerformanceWS incontactPerformanceWS) {
		this.incontactPerformanceWS = incontactPerformanceWS;
	}

	public void setIncontactService(IncontactService incontactService) {
		this.incontactService = incontactService;
	}
}