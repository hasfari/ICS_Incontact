package com.incontact.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author HAsfari added on Nov 10, 2014
 *
 * @param <T>
 *
 * IncontactService.java
 */
@Service
@SuppressWarnings("unchecked")
@Transactional
public class IncontactService <T> {

	private final String GET_MOST_RECENT_LOG = "SELECT * FROM SYS_AGENT_PERFORMANCE_LOG WHERE SYS_LOG_ID IN (SELECT MAX(SYS_LOG_ID) FROM SYS_AGENT_PERFORMANCE_LOG WHERE SYS_API_TYPE_ID= :apiTypeID)"; 
	private final String GET_PERFORMANCE_SCHEDULE_SQL = "SELECT * FROM SYS_AGENT_SCHDL WHERE SYS_API_TYPE_ID= :apiTypeID"; 

    public void createEntities(Session session, List<T> entities) {
    	for (T entity : entities){
        	session.save(entity);
    	}
    }

    public void createEntity(Session session, T entity) {
    	session.save(entity);
    }

    public List<SysAgentMainLog> getMissingLogsEntries(Session session, Class<T> clazz, int agentIdStatesTypeID, int otherTypeID, int timeInterval, String interval) {
    	Query qry = session.createSQLQuery("SELECT SYS_LOG_ID, SYS_API_TYPE_ID, SYS_LOG_REQ_START_TIME, SYS_LOG_REQ_START_TIME + interval '"+timeInterval+"' "+interval+" AS SYS_LOG_REQ_END_TIME, " +
    				"SYS_UPDATE_TIME FROM SYS_AGENT_PERFORMANCE_LOG WHERE SYS_LOG_REQ_START_TIME=(SELECT MIN(SYS_LOG_REQ_START_TIME) FROM SYS_AGENT_PERFORMANCE_LOG a WHERE  " +
    				"SYS_API_TYPE_ID=:agentIdStatesTypeID AND a.SYS_LOG_REQ_START_TIME >= (CASE WHEN (SELECT MAX(b.SYS_LOG_REQ_END_TIME) FROM SYS_AGENT_PERFORMANCE_LOG b WHERE SYS_API_TYPE_ID=:otherTypeID) IS NULL " +
    				"THEN a.SYS_LOG_REQ_START_TIME ELSE (SELECT MAX(b.SYS_LOG_REQ_END_TIME) FROM SYS_AGENT_PERFORMANCE_LOG b WHERE SYS_API_TYPE_ID=:otherTypeID) END)) AND SYS_API_TYPE_ID=:agentIdStatesTypeID " +
    				"AND SYS_LOG_REQ_START_TIME + interval '"+timeInterval+"' "+interval+" <= (SELECT MAX(SYS_LOG_REQ_END_TIME) FROM SYS_AGENT_PERFORMANCE_LOG WHERE SYS_API_TYPE_ID =:agentIdStatesTypeID)").
				addEntity(SysAgentMainLog.class).setParameter("agentIdStatesTypeID", agentIdStatesTypeID).setParameter("otherTypeID", otherTypeID);
		List<SysAgentMainLog> agentIdStatesLst = qry.list();
		return agentIdStatesLst;
    }
    
    public List<SysAgentMainLog> getMissingLogsEntries_2(Session session, Class<T> clazz, int agentIdStatesTypeID, int otherTypeID, int timeInterval, String interval) {
    	Query qry = session.createSQLQuery("select * from( " + 
		  "SELECT SYS_LOG_ID, SYS_API_TYPE_ID, MIN(SYS_LOG_REQ_START_TIME) as SYS_LOG_REQ_START_TIME, MIN(SYS_LOG_REQ_END_TIME) AS SYS_LOG_REQ_END_TIME, SYS_UPDATE_TIME " +
		  "FROM SYS_AGENT_PERFORMANCE_LOG " +
		  "WHERE SYS_API_TYPE_ID=:agentIdStatesTypeID and SYS_LOG_REQ_START_TIME > " + 
		  "(SELECT MAX(SYS_LOG_REQ_START_TIME) " +
		     "FROM SYS_AGENT_PERFORMANCE_LOG " +
		  	 "WHERE SYS_API_TYPE_ID=:otherTypeID) " +
		  	 "group by SYS_LOG_ID, SYS_API_TYPE_ID, SYS_UPDATE_TIME order by SYS_LOG_REQ_START_TIME) where rownum <=1").
		  	addEntity(SysAgentMainLog.class).setParameter("agentIdStatesTypeID", agentIdStatesTypeID).setParameter("otherTypeID", otherTypeID);
		List<SysAgentMainLog> agentIdStatesLst = qry.list();
		return agentIdStatesLst;
    }
    
    public List<AgentIdStates> getMissingAgentIdStates(Session session, Class<T> clazz, int agentIdStatesTypeID, int otherTypeID, int timeInterval, String interval) {
    
    	Query qry = session.createSQLQuery("SELECT AGENT_ID, TEAM_ID, WS_START_TIME, WS_START_TIME + interval '"+timeInterval+"' "+interval+" AS WS_END_TIME FROM AGENT_ID_STATES " +
    				"WHERE (ws_start_Time, ws_end_Time) IN (SELECT SYS_LOG_REQ_START_TIME, SYS_LOG_REQ_END_TIME FROM SYS_AGENT_PERFORMANCE_LOG lg WHERE " + 
    				"SYS_LOG_REQ_START_TIME = (CASE WHEN (SELECT MAX(b.SYS_LOG_REQ_END_TIME) FROM SYS_AGENT_PERFORMANCE_LOG b WHERE SYS_API_TYPE_ID=:otherTypeID) IS NULL " +
    				"THEN (SELECT MIN(b.SYS_LOG_REQ_START_TIME) FROM SYS_AGENT_PERFORMANCE_LOG b WHERE SYS_API_TYPE_ID=:agentIdStatesTypeID) ELSE (SELECT MAX(b.SYS_LOG_REQ_END_TIME) " +
    				"FROM SYS_AGENT_PERFORMANCE_LOG b WHERE SYS_API_TYPE_ID=:otherTypeID ) END)) AND WS_START_TIME + interval '"+timeInterval+"' "+interval+" <= (SELECT MAX(SYS_LOG_REQ_END_TIME) " +
    				"FROM SYS_AGENT_PERFORMANCE_LOG WHERE SYS_API_TYPE_ID =:agentIdStatesTypeID)").
    				setParameter("agentIdStatesTypeID", agentIdStatesTypeID).setParameter("otherTypeID", otherTypeID);
    	List<Object[]> rows = qry.list();
    	List<AgentIdStates> agentIdStatesLst = new ArrayList();
    	for(Object[] row : rows){
    		AgentIdStates agentIdStates = new AgentIdStates();
    		agentIdStates.setAgentId((BigDecimal) row[0]);
    		agentIdStates.setTeamId((BigDecimal) row[1]);
    		agentIdStates.setWsStartTime((Timestamp) row[2]);
    		agentIdStates.setWsEndTime((Timestamp) row[3]);
    	    agentIdStatesLst.add(agentIdStates);
    	}    	
		return agentIdStatesLst;
    }
    
	public List<SysAgentMainLog> findLatestLog(Session session, int apiTypeID) {
			SQLQuery qry = session.createSQLQuery(GET_MOST_RECENT_LOG);
			qry.setParameter("apiTypeID", apiTypeID);
			qry.addEntity(SysAgentMainLog.class);
			List<SysAgentMainLog> agentPerformanceLogLst = qry.list();
		return agentPerformanceLogLst;
		
	}

	public List<SysAgentSchdl> findPerformanceSchedule(Session session, int apiTypeID) {
			SQLQuery qry = session.createSQLQuery(GET_PERFORMANCE_SCHEDULE_SQL);
			qry.setParameter("apiTypeID", apiTypeID);
			qry.addEntity(SysAgentSchdl.class);
			List<SysAgentSchdl> agentPerformanceScheduleLst = qry.list();
		return agentPerformanceScheduleLst;
		
	}

	public List<SysTeam> findSysTeams(Session session) {
		List<SysTeam> sysTeamLst = (List<SysTeam>) session.createQuery("from SysTeam a where SYS_TEAM_EXCLUDED='N'").list();
		return sysTeamLst;
	}
	
	
	public final T findOne(Class<T> clazz, Session session, final int id) {
		return (T) session.get(clazz, id);
	}
    

}
