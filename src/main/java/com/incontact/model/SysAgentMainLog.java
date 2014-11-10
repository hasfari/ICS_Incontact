package com.incontact.model;


import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * @author HAsfari added on Nov 10, 2014
 *
 *
 * SysAgentMainLog.java
 */

@Entity
@Table(name = "SYS_AGENT_PERFORMANCE_LOG")
public class SysAgentMainLog implements java.io.Serializable {

	private long SysLogId;
	private Timestamp sysLogReqStartTime;
	private Timestamp sysLogReqEndTime;
	private Timestamp sysUpdateTime;
	private Set sysAgentPerformances = new HashSet(0);
	private Set sysAgentInteractionHistoryLog = new HashSet(0);
	private Set sysStatesHistoryLog = new HashSet(0);

	@Id
	@Column(name = "SYS_LOG_ID", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="LOGID")
	@SequenceGenerator(name="LOGID", sequenceName="AGENT_PERFORMANCE_LOG_SEQ")
	public long getSysLogId() {
		return this.SysLogId;
	}

	public void setSysLogId(long SysLogId) {
		this.SysLogId = SysLogId;
	}

	@Column(name = "SYS_LOG_REQ_START_TIME", nullable = false)
	public Timestamp getSysLogReqStartTime() {
		return this.sysLogReqStartTime;
	}

	public void setSysLogReqStartTime(Timestamp sysLogReqStartTime) {
		this.sysLogReqStartTime = sysLogReqStartTime;
	}

	@Column(name = "SYS_LOG_REQ_END_TIME", nullable = false)
	public Timestamp getSysLogReqEndTime() {
		return this.sysLogReqEndTime;
	}

	public void setSysLogReqEndTime(Timestamp logReqEndTime) {
		this.sysLogReqEndTime = logReqEndTime;
	}

	@Column(name = "SYS_UPDATE_TIME", nullable = false)
	public Timestamp getSysUpdateTime() {
		return this.sysUpdateTime;
	}

	public void setSysUpdateTime(Timestamp sysUpdateTime) {
		this.sysUpdateTime = sysUpdateTime;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sysAgentPerformanceLog")
	public Set<AgentPerformance> getSysAgentPerformances() {
		return this.sysAgentPerformances;
	}

	public void setSysAgentPerformances(Set sysAgentPerformances) {
		this.sysAgentPerformances = sysAgentPerformances;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sysAgentInteractionHistoryLog")
	public Set<InteractionHistory> getSysAgentInteractionHistoryLog() {
		return this.sysAgentInteractionHistoryLog;
	}

	public void setSysAgentInteractionHistoryLog(Set sysAgentInteractionHistoryLog) {
		this.sysAgentInteractionHistoryLog = sysAgentInteractionHistoryLog;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sysStatesHistoryLog")
	public Set<AgentStateHistory> getSysStatesHistoryLog() {
		return this.sysStatesHistoryLog;
	}

	public void setSysStatesHistoryLog(Set sysStatesHistoryLog) {
		this.sysStatesHistoryLog = sysStatesHistoryLog;
	}

}
