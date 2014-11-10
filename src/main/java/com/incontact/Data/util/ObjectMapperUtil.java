package com.incontact.Data.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.json.JSONException;
import org.json.JSONObject;

import com.incontact.DB.util.DBUtil;
import com.incontact.Date.util.DateUtil;
import com.incontact.model.AgentIdStates;
import com.incontact.model.AgentPerformance;
import com.incontact.model.AgentStateHistory;
import com.incontact.model.ConvertStringToBollean;
import com.incontact.model.IncontactService;
import com.incontact.model.InteractionHistory;
import com.incontact.model.SkillSummary;
import com.incontact.model.SysAgentMainLog;
import com.incontact.model.SysTeam;
import com.incontact.ws.IIncontactPerformanceWS;
import com.incontact.ws.impl.IncontactPerformanceWSImpl;
import com.incontact.ws.pojo.TokenResponse;


/**
 * @author HAsfari added on Nov 10, 2014
 *
 *
 * ObjectMapperUtil.java
 */

public class ObjectMapperUtil {

	public static void populateStatesHistoryByAgentList(IIncontactPerformanceWS incontactPerformanceWS, IncontactService incontactService, TokenResponse tokenResponse, Session session, SysAgentMainLog agentMainLog, List<AgentStateHistory> agentStateHistoryLst, AgentIdStates agentIdStates) throws IOException {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String rspns = ((IncontactPerformanceWSImpl) incontactPerformanceWS).getAgentStateHistoryByAgent(tokenResponse, String.valueOf(agentIdStates.getAgentId()), 
				dateFormatter.format(agentMainLog.getSysLogReqStartTime()), dateFormatter.format(agentMainLog.getSysLogReqEndTime()));
		if (!StringUtils.isEmpty(rspns)) {
			JSONObject jsonArrayRspnse = new JSONObject(rspns);
			if (!"{}".equals(jsonArrayRspnse.getJSONObject("agentStateHistory").toString())) {
				for (int indx = 0; indx < jsonArrayRspnse.getJSONObject("agentStateHistory").getJSONArray("entries").length(); indx++) {
					String statesHistoryRspns = jsonArrayRspnse.getJSONObject("agentStateHistory").getJSONArray("entries").get(indx).toString();
					JSONObject jsonStatesHistoryObject = new JSONObject(statesHistoryRspns);
					AgentStateHistory statesHistory = new AgentStateHistory();
					statesHistory.setAgentId(agentIdStates.getAgentId());
					statesHistory.setTeamId(agentIdStates.getTeamId());
					statesHistory.setStateIndex(DateUtil.checkIfDecimalIsNull((String) jsonStatesHistoryObject.get("stateIndex")));
					statesHistory.setStartDate(DateUtil.checkIfTimestampIsNull((String) jsonStatesHistoryObject.get("startDate")));
					statesHistory.setAgentStateId(DateUtil.checkIfDecimalIsNull((String) jsonStatesHistoryObject.get("agentStateId")));
					statesHistory.setAgentSessionId((String) jsonStatesHistoryObject.get("agentSessionId"));
					statesHistory.setSkillId(DateUtil.checkIfDecimalIsNull((String) jsonStatesHistoryObject.get("skillId")));
					statesHistory.setOutStateId(DateUtil.checkIfDecimalIsNull((String) jsonStatesHistoryObject.get("outStateId")));
					statesHistory.setOutStateDescription((String) jsonStatesHistoryObject.get("outStateDescription"));
					statesHistory.setDuration(DateUtil.checkIfDecimalIsNull((String) jsonStatesHistoryObject.get("duration")));
					statesHistory.setWsStartTime(agentMainLog.getSysLogReqStartTime());
					statesHistory.setWsEndTime(agentMainLog.getSysLogReqEndTime());
					statesHistory.setSysUpdateTime(new Timestamp(new Date().getTime()));
					statesHistory.setSysStatesHistoryLog(agentMainLog);
					agentStateHistoryLst.add(statesHistory);
					if (agentStateHistoryLst.size() % 40 == 0) {
						incontactService.createEntities(session, agentStateHistoryLst);
						agentStateHistoryLst.clear();
					}
				}
			}
		}
	}	

	public static void populateAgentIdStatesList(List<SysTeam> sysTeamsLst, IncontactService incontactService, List<AgentIdStates> agentIdStatesLst, SysAgentMainLog sysAgentPerformanceLog, String rspns,JSONObject jsonArrayRspnse, int indx, Session session) throws JSONException, ParseException {
		String agentIdStatesRspns = jsonArrayRspnse.getJSONArray("agentStates").get(indx).toString();
		JSONObject jsonAgentIdStatesObject = new JSONObject(agentIdStatesRspns);
		BigDecimal teamId = DateUtil.checkIfDecimalIsNull((String) jsonAgentIdStatesObject.get("TeamId"));
		if (!DBUtil.desiredTeam(sysTeamsLst, teamId))
			return;
		AgentIdStates agentIdStates = new AgentIdStates();
		agentIdStates.setAgentId(DateUtil.checkIfDecimalIsNull((String) jsonAgentIdStatesObject.get("AgentId")));
		agentIdStates.setStateId(DateUtil.checkIfDecimalIsNull((String) jsonAgentIdStatesObject.get("AgentStateId")));
		agentIdStates.setStateName((String) jsonAgentIdStatesObject.get("AgentStateName"));
		agentIdStates.setBusinessUnitId(DateUtil.checkIfDecimalIsNull((String) jsonAgentIdStatesObject.get("BusinessUnitId")));
		agentIdStates.setContactId(DateUtil.checkIfDecimalIsNull((String) jsonAgentIdStatesObject.get("ContactId")));
		agentIdStates.setIsAcw("False".equalsIgnoreCase((String) jsonAgentIdStatesObject.get("IsACW")) ? "F" : "T");
		agentIdStates.setFirstName((String) jsonAgentIdStatesObject.get("FirstName"));
		agentIdStates.setFromAddress((String) jsonAgentIdStatesObject.get("FromAddress"));
		agentIdStates.setLastName((String) jsonAgentIdStatesObject.get("LastName"));
		agentIdStates.setLastPolltime(DateUtil.checkIfTimestampIsNull((String) jsonAgentIdStatesObject.get("LastPollTime")));
		agentIdStates.setLastUpdateTime(DateUtil.checkIfTimestampIsNull((String) jsonAgentIdStatesObject.get("LastUpdateTime")));
		agentIdStates.setMediaName((String) jsonAgentIdStatesObject.get("MediaName"));
		agentIdStates.setMediaType((String) jsonAgentIdStatesObject.get("MediaType"));
		agentIdStates.setOutStateDescription((String) jsonAgentIdStatesObject.get("OutStateDescription"));
		agentIdStates.setOutStateId(DateUtil.checkIfDecimalIsNull((String) jsonAgentIdStatesObject.get("OutStateId")));
		agentIdStates.setSkillId(DateUtil.checkIfDecimalIsNull((String) jsonAgentIdStatesObject.get("OutStateId")));
		agentIdStates.setSkillName((String) jsonAgentIdStatesObject.get("SkillName"));
		agentIdStates.setStartDate(DateUtil.checkIfDateIsNull((String) jsonAgentIdStatesObject.get("StartDate")));
		agentIdStates.setStationId(DateUtil.checkIfDecimalIsNull((String) jsonAgentIdStatesObject.get("StationId")));
		agentIdStates.setStationPhoneNumber((String) jsonAgentIdStatesObject.get("StationPhoneNumber"));
		agentIdStates.setTeamId(teamId);
		agentIdStates.setTeamName((String) jsonAgentIdStatesObject.get("TeamName"));
		agentIdStates.setToAddress((String) jsonAgentIdStatesObject.get("ToAddress"));
		agentIdStates.setWsStartTime(sysAgentPerformanceLog.getSysLogReqStartTime());
		agentIdStates.setWsEndTime(sysAgentPerformanceLog.getSysLogReqEndTime());
		agentIdStates.setSysUpdateTime(new Timestamp(new Date().getTime()));
		agentIdStates.setSysAgentPerformanceLog(sysAgentPerformanceLog);
		agentIdStatesLst.add(agentIdStates);
		if (agentIdStatesLst.size() % 40 == 0) {
			incontactService.createEntities(session, agentIdStatesLst);
			agentIdStatesLst.clear();
		}
	}

	public static void populatePerformanceList(IIncontactPerformanceWS incontactPerformanceWS, IncontactService incontactService, TokenResponse tokenResponse, Session session, SysAgentMainLog sysAgentPerformanceLog, List<AgentPerformance> agentPerformanceLst, AgentIdStates agentIdStates) throws IOException {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String rspns = ((IncontactPerformanceWSImpl) incontactPerformanceWS).getPerformance(tokenResponse, String.valueOf(agentIdStates.getAgentId()), 
				dateFormatter.format(sysAgentPerformanceLog.getSysLogReqStartTime()), dateFormatter.format(sysAgentPerformanceLog.getSysLogReqEndTime()));
		if (!StringUtils.isEmpty(rspns)) {
			JSONObject jsonArrayRspnse = new JSONObject(rspns);
			for (int indx = 0; indx < jsonArrayRspnse.getJSONArray("agentPerformance").length(); indx++) {

				String performanceRspns = jsonArrayRspnse.getJSONArray("agentPerformance").get(indx).toString();
				JSONObject jsonPerformanceObject = new JSONObject(performanceRspns);
				BigDecimal teamId = DateUtil.checkIfDecimalIsNull((String) jsonPerformanceObject.get("teamId"));
				AgentPerformance agentPerformance = new AgentPerformance();
				agentPerformance.setAgentId(DateUtil.checkIfDecimalIsNull((String) jsonPerformanceObject.get("agentId")));
				agentPerformance.setTeamId(teamId);
				agentPerformance.setAgentOffered("0".equalsIgnoreCase((String) jsonPerformanceObject.get("agentOffered")) ? "N" : "Y");
				agentPerformance.setInboundHandled("0".equalsIgnoreCase((String) jsonPerformanceObject.get("inboundHandled")) ? "N" : "Y");
				agentPerformance.setInboundTime(DateUtil.checkIfDecimalIsNull(DateUtil.convert8601TimeToSeconds((String) jsonPerformanceObject.get("inboundTime"))));
				agentPerformance.setInboundTalkTime(DateUtil.checkIfDecimalIsNull(DateUtil.convert8601TimeToSeconds((String) jsonPerformanceObject.get("inboundTalkTime"))));
				agentPerformance.setOutboundHandled("0".equalsIgnoreCase((String) jsonPerformanceObject.get("outboundHandled")) ? "N" : "Y");
				agentPerformance.setOutboundTime(DateUtil.checkIfDecimalIsNull(DateUtil.convert8601TimeToSeconds((String) jsonPerformanceObject.get("outboundTime"))));
				agentPerformance.setOutboundTalkTime(DateUtil.checkIfDecimalIsNull(DateUtil.convert8601TimeToSeconds((String) jsonPerformanceObject.get("outboundTalkTime"))));
				agentPerformance.setOutboundAvgTalkTime(DateUtil.checkIfDecimalIsNull(DateUtil.convert8601TimeToSeconds((String) jsonPerformanceObject.get("outboundAvgTalkTime"))));
				agentPerformance.setTotalHandled(DateUtil.checkIfDecimalIsNull((String) jsonPerformanceObject.get("totalHandled")));
				agentPerformance.setTotalTalkTime(DateUtil.checkIfDecimalIsNull(DateUtil.convert8601TimeToSeconds((String) jsonPerformanceObject.get("totalTalkTime"))));
				agentPerformance.setTotalAvgTalkTime(DateUtil.checkIfDecimalIsNull(DateUtil.convert8601TimeToSeconds((String) jsonPerformanceObject.get("totalAvgTalkTime"))));
				agentPerformance.setTotalAvgHandleTime(DateUtil.checkIfDecimalIsNull(DateUtil.convert8601TimeToSeconds((String) jsonPerformanceObject.get("totalAvgHandleTime"))));
				agentPerformance.setConsultTime(DateUtil.checkIfDecimalIsNull(DateUtil.convert8601TimeToSeconds((String) jsonPerformanceObject.get("consultTime"))));
				agentPerformance.setAvailableTime(DateUtil.checkIfDecimalIsNull(DateUtil.convert8601TimeToSeconds((String) jsonPerformanceObject.get("availableTime"))));
				agentPerformance.setUnavailableTime(DateUtil.checkIfDecimalIsNull(DateUtil.convert8601TimeToSeconds((String) jsonPerformanceObject.get("unavailableTime"))));
				agentPerformance.setAcwTime(DateUtil.checkIfDecimalIsNull(DateUtil.convert8601TimeToSeconds((String) jsonPerformanceObject.get("acwTime"))));
				agentPerformance.setRefused(DateUtil.checkIfDecimalIsNull((String) jsonPerformanceObject.get("refused")));
				agentPerformance.setPercentRefused(DateUtil.checkIfDecimalIsNull((String) jsonPerformanceObject.get("percentRefused")));
				agentPerformance.setLoginTime(DateUtil.checkIfDecimalIsNull(DateUtil.convert8601TimeToSeconds((String) jsonPerformanceObject.get("loginTime"))));
				agentPerformance.setWorkingRate(DateUtil.checkIfDecimalIsNull((String) jsonPerformanceObject.get("workingRate")));
				agentPerformance.setOccupancy(DateUtil.checkIfDecimalIsNull((String) jsonPerformanceObject.get("occupancy")));
				agentPerformance.setWsStartTime(sysAgentPerformanceLog.getSysLogReqStartTime());
				agentPerformance.setWsEndTime(sysAgentPerformanceLog.getSysLogReqEndTime());
				agentPerformance.setSysUpdateTime(new Timestamp(new Date().getTime()));
				agentPerformance.setSysAgentPerformanceLog(sysAgentPerformanceLog);
				agentPerformanceLst.add(agentPerformance);
				if (agentPerformanceLst.size() % 40 == 0) {
					incontactService.createEntities(session, agentPerformanceLst);
					agentPerformanceLst.clear();
				}
			}
		}
	}
	
	public static void populateSkillSummaryList(IIncontactPerformanceWS incontactPerformanceWS, IncontactService incontactService, TokenResponse tokenResponse, Session session, SysAgentMainLog sysAgentPerformanceLog, List<SkillSummary> agentSkillSummaryLst) throws IOException {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		ConvertStringToBollean convertStringToBollean = new ConvertStringToBollean();
		String rspns = ((IncontactPerformanceWSImpl) incontactPerformanceWS).getSkillsSummary(tokenResponse, dateFormatter.format(sysAgentPerformanceLog.getSysLogReqStartTime()), dateFormatter.format(sysAgentPerformanceLog.getSysLogReqEndTime()));
		if (!StringUtils.isEmpty(rspns)) {
			JSONObject jsonArrayRspnse = new JSONObject(rspns);
			for (int indx = 0; indx < jsonArrayRspnse.getJSONArray("skillSummaries").length(); indx++) {

				String skillSummariesRspns = jsonArrayRspnse.getJSONArray("skillSummaries").get(indx).toString();
				JSONObject jsonPerformanceObject = new JSONObject(skillSummariesRspns);
				SkillSummary skillSummary = new SkillSummary();
				skillSummary.setBusinessUnitId(DateUtil.checkIfDecimalIsNull((String) jsonPerformanceObject.get("BusinessUnitId")));
				skillSummary.setAbandonCount(DateUtil.checkIfDecimalIsNull((String) jsonPerformanceObject.get("AbandonCount")));
				skillSummary.setAbandonRate(DateUtil.checkIfDecimalIsNull((String) jsonPerformanceObject.get("AbandonRate")));
				skillSummary.setAgentsAcw(convertStringToBollean.convertToEntityAttribute((String) jsonPerformanceObject.get("AgentsAcw")));
				skillSummary.setAgentsAvailable(DateUtil.checkIfDecimalIsNull((String) jsonPerformanceObject.get("AgentsAvailable")));
				skillSummary.setAgentsIdle(DateUtil.checkIfDecimalIsNull((String) jsonPerformanceObject.get("AgentsIdle")));
				skillSummary.setAgentsLoggedIn(DateUtil.checkIfDecimalIsNull((String) jsonPerformanceObject.get("AgentsLoggedIn")));
				skillSummary.setAgentsUnavailable(DateUtil.checkIfDecimalIsNull((String) jsonPerformanceObject.get("AgentsUnavailable")));
				skillSummary.setAgentsWorking(DateUtil.checkIfDecimalIsNull((String) jsonPerformanceObject.get("AgentsWorking")));
				skillSummary.setAverageHandleTime(DateUtil.checkIfDecimalIsNull((String) jsonPerformanceObject.get("AverageHandleTime")));
				skillSummary.setAverageInqueueTime(DateUtil.checkIfDecimalIsNull((String) jsonPerformanceObject.get("AverageInqueueTime")));
				skillSummary.setAverageSpeedToAnswer(DateUtil.checkIfDecimalIsNull((String) jsonPerformanceObject.get("AverageSpeedToAnswer")));
				skillSummary.setAverageTalkTime(DateUtil.checkIfDecimalIsNull((String) jsonPerformanceObject.get("AverageTalkTime")));
				skillSummary.setAverageWrapTime(DateUtil.checkIfDecimalIsNull((String) jsonPerformanceObject.get("AverageWrapTime")));
				skillSummary.setCampaignId(DateUtil.checkIfDecimalIsNull((String) jsonPerformanceObject.get("CampaignId")));
				skillSummary.setCampaignName((String) jsonPerformanceObject.get("CampaignName"));
				skillSummary.setContactsActive(convertStringToBollean.convertToEntityAttribute((String) jsonPerformanceObject.get("ContactsActive")));
				skillSummary.setContactsHandled(convertStringToBollean.convertToEntityAttribute((String) jsonPerformanceObject.get("ContactsHandled")));
				skillSummary.setContactsOffered(convertStringToBollean.convertToEntityAttribute((String) jsonPerformanceObject.get("ContactsOffered")));
				skillSummary.setContactsQueued(convertStringToBollean.convertToEntityAttribute((String) jsonPerformanceObject.get("ContactsQueued")));
				skillSummary.setContactsOutOfSLA(convertStringToBollean.convertToEntityAttribute((String) jsonPerformanceObject.get("ContactsOutOfSLA")));
				skillSummary.setContactsWithinSLA(convertStringToBollean.convertToEntityAttribute((String) jsonPerformanceObject.get("ContactsWithinSLA")));
				skillSummary.setHoldTime(DateUtil.checkIfDecimalIsNull((String) jsonPerformanceObject.get("HoldTime")));
				skillSummary.setIsOutbound(convertStringToBollean.convertToEntityAttribute((String) jsonPerformanceObject.get("IsOutbound")));
				skillSummary.setLongestQueueDuration(DateUtil.checkIfDecimalIsNull((String) jsonPerformanceObject.get("LongestQueueDur")));
				skillSummary.setMediaTypeId((String) jsonPerformanceObject.get("MediaTypeId"));
				skillSummary.setMediaTypeName((String) jsonPerformanceObject.get("MediaTypeName"));
				skillSummary.setQueueCount(DateUtil.checkIfDecimalIsNull((String) jsonPerformanceObject.get("QueueCount")));
				skillSummary.setServiceLevel(DateUtil.checkIfDecimalIsNull((String) jsonPerformanceObject.get("ServiceLevel")));
				skillSummary.setSkillName((String) jsonPerformanceObject.get("SkillName"));
				skillSummary.setSkillId(DateUtil.checkIfDecimalIsNull((String) jsonPerformanceObject.get("SkillId")));
				skillSummary.setServiceLevelGoal(DateUtil.checkIfDecimalIsNull((String) jsonPerformanceObject.get("ServiceLevelGoal")));
				skillSummary.setServiceLevelThreshold(DateUtil.checkIfDecimalIsNull((String) jsonPerformanceObject.get("ServiceLevelThreshold")));				
				skillSummary.setWsStartTime(sysAgentPerformanceLog.getSysLogReqStartTime());
				skillSummary.setWsEndTime(sysAgentPerformanceLog.getSysLogReqEndTime());
				skillSummary.setSysUpdateTime(new Timestamp(new Date().getTime()));
				skillSummary.setSysAgentPerformanceLog(sysAgentPerformanceLog);
				agentSkillSummaryLst.add(skillSummary);
				if (agentSkillSummaryLst.size() % 40 == 0) {
					incontactService.createEntities(session, agentSkillSummaryLst);
					agentSkillSummaryLst.clear();
				}
			}
		}
	}
	
	
	public static void populateInteractionHistoryList(IIncontactPerformanceWS incontactPerformanceWS, IncontactService incontactService, TokenResponse tokenResponse, Session session, SysAgentMainLog sysAgentInteractionHistoryLog, List<InteractionHistory> interactionHistoryLst, AgentIdStates agentIdStates) throws IOException {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String rspns = ((IncontactPerformanceWSImpl) incontactPerformanceWS).getInteractionHistory(tokenResponse, String.valueOf(agentIdStates.getAgentId()), 
				dateFormatter.format(sysAgentInteractionHistoryLog.getSysLogReqStartTime()), dateFormatter.format(sysAgentInteractionHistoryLog.getSysLogReqEndTime()));
		if (!StringUtils.isEmpty(rspns)) {
			JSONObject jsonArrayRspnse = new JSONObject(rspns);
			if (!"{}".equals(jsonArrayRspnse.getJSONObject("contactHistory").toString())) {
				for (int indx = 0; indx < jsonArrayRspnse.getJSONObject("contactHistory").getJSONArray("Contacts").length(); indx++) {
					String interactionHistoryRspns = jsonArrayRspnse.getJSONObject("contactHistory").getJSONArray("Contacts").get(indx).toString();
					JSONObject jsonInteractionHistoryObject = new JSONObject(interactionHistoryRspns);
					BigDecimal teamId = DateUtil.checkIfDecimalIsNull((String) jsonInteractionHistoryObject.get("TeamId"));
					InteractionHistory interactionHistory = new InteractionHistory();
					interactionHistory.setAgentId(DateUtil.checkIfDecimalIsNull((String) jsonInteractionHistoryObject.get("AgentId")));
					interactionHistory.setAgentName((String) jsonInteractionHistoryObject.get("AgentName"));
					interactionHistory.setBusinessUnitId(DateUtil.checkIfDecimalIsNull((String) jsonInteractionHistoryObject.get("BusinessUnitId")));
					interactionHistory.setCampaignName((String) jsonInteractionHistoryObject.get("CampaignName"));
					interactionHistory.setCampaignId(DateUtil.checkIfDecimalIsNull((String) jsonInteractionHistoryObject.get("CampaignId")));
					interactionHistory.setFromAddress((String) jsonInteractionHistoryObject.get("FromAddr"));
					interactionHistory.setLastUpdateTime(DateUtil.checkIfTimestampIsNull((String) jsonInteractionHistoryObject.get("LastUpdateTime")));
					interactionHistory.setMastercontactId((String) jsonInteractionHistoryObject.get("MasterContactId"));
					interactionHistory.setMediaName((String) jsonInteractionHistoryObject.get("MediaName"));
					interactionHistory.setMediaType((String) jsonInteractionHistoryObject.get("MediaType"));
					interactionHistory.setSkillName((String) jsonInteractionHistoryObject.get("SkillName"));
					interactionHistory.setSkillId(DateUtil.checkIfDecimalIsNull((String) jsonInteractionHistoryObject.get("SkillId")));
					interactionHistory.setStartDate(DateUtil.checkIfTimestampIsNull((String) jsonInteractionHistoryObject.get("StartDate")));
					interactionHistory.setTeamName((String) jsonInteractionHistoryObject.get("TeamName"));
					interactionHistory.setTeamId(teamId);
					interactionHistory.setToAddress((String) jsonInteractionHistoryObject.get("ToAddr"));
					interactionHistory.setPointOfContact((String) jsonInteractionHistoryObject.get("PointOfContact"));
					interactionHistory.setWsStartTime(sysAgentInteractionHistoryLog.getSysLogReqStartTime());
					interactionHistory.setWsEndTime(sysAgentInteractionHistoryLog.getSysLogReqEndTime());
					interactionHistory.setSysUpdateTime(new Timestamp(new Date().getTime()));
					interactionHistory.setSysAgentInteractionHistoryLog(sysAgentInteractionHistoryLog);
					interactionHistoryLst.add(interactionHistory);
					if (interactionHistoryLst.size() % 40 == 0) {
						incontactService.createEntities(session, interactionHistoryLst);
						interactionHistoryLst.clear();
					}
				}
			}
		}
	}
		
}
