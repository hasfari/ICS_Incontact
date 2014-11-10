package com.incontact.model;


import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * @author HAsfari added on Nov 10, 2014
 *
 *
 * AgentStateHistory.java
 */

@Entity
@Table(name = "AGENT_STATE_HISTORY")
public class AgentStateHistory implements java.io.Serializable {

	private int tblId;
	private BigDecimal agentId;
	private BigDecimal teamId;
	private BigDecimal stateIndex;
	private Timestamp startDate;
	private BigDecimal agentStateId;
	private String agentSessionId;
	private BigDecimal skillId;
	private BigDecimal outStateId;
	private String outStateDescription;
	private BigDecimal duration;
	private Timestamp wsStartTime;
	private Timestamp wsEndTime;
	private Timestamp sysUpdateTime;
	private SysAgentMainLog sysStatesHistoryLog;

	@Id
	@Column(name = "TBL_ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "AGENT_STATE_HISTORY_ID")
	@SequenceGenerator(name = "AGENT_STATE_HISTORY_ID", sequenceName = "AGENT_STATE_HISTORY_SEQ")
	public int getTblId() {
		return this.tblId;
	}

	public void setTblId(int tblId) {
		this.tblId = tblId;
	}
	
	@Column(name = "AGENT_ID", nullable = false, precision = 10, scale = 0)
	public BigDecimal getAgentId() {
		return this.agentId;
	}

	public void setAgentId(BigDecimal agentId) {
		this.agentId = agentId;
	}

	@Column(name = "TEAM_ID", nullable = false, precision = 10, scale = 0)
	public BigDecimal getTeamId() {
		return this.teamId;
	}

	public void setTeamId(BigDecimal teamId) {
		this.teamId = teamId;
	}

	@Column(name = "STATE_INDEX", precision = 10, scale = 0)
	public BigDecimal getStateIndex() {
		return this.stateIndex;
	}

	public void setStateIndex(BigDecimal stateIndex) {
		this.stateIndex = stateIndex;
	}

	@Column(name = "START_DATE")
	public Timestamp getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	@Column(name = "AGENT_STATE_ID", precision = 10, scale = 0)
	public BigDecimal getAgentStateId() {
		return this.agentStateId;
	}

	public void setAgentStateId(BigDecimal agentStateId) {
		this.agentStateId = agentStateId;
	}

	@Column(name = "AGENT_SESSION_ID", length = 20)
	public String getAgentSessionId() {
		return this.agentSessionId;
	}

	public void setAgentSessionId(String agentSessionId) {
		this.agentSessionId = agentSessionId;
	}

	@Column(name = "SKILL_ID", precision = 10, scale = 0)
	public BigDecimal getSkillId() {
		return this.skillId;
	}

	public void setSkillId(BigDecimal skillId) {
		this.skillId = skillId;
	}

	@Column(name = "OUT_STATE_ID", precision = 10, scale = 0)
	public BigDecimal getOutStateId() {
		return this.outStateId;
	}

	public void setOutStateId(BigDecimal outStateId) {
		this.outStateId = outStateId;
	}

	@Column(name = "OUT_STATE_DESCRIPTION", length = 250)
	public String getOutStateDescription() {
		return this.outStateDescription;
	}

	public void setOutStateDescription(String outStateDescription) {
		this.outStateDescription = outStateDescription;
	}

	@Column(name = "DURATION", precision = 10, scale = 0)
	public BigDecimal getDuration() {
		return this.duration;
	}

	public void setDuration(BigDecimal duration) {
		this.duration = duration;
	}

	@Column(name = "WS_START_TIME", nullable = false)
	public Timestamp getWsStartTime() {
		return wsStartTime;
	}

	public void setWsStartTime(Timestamp wsStartTime) {
		this.wsStartTime = wsStartTime;
	}

	@Column(name = "WS_END_TIME", nullable = false)
	public Timestamp getWsEndTime() {
		return wsEndTime;
	}

	public void setWsEndTime(Timestamp wsEndTime) {
		this.wsEndTime = wsEndTime;
	}

	@Column(name = "SYS_UPDATE_TIME", nullable = false)
	public Timestamp getSysUpdateTime() {
		return this.sysUpdateTime;
	}

	public void setSysUpdateTime(Timestamp sysUpdateTime) {
		this.sysUpdateTime = sysUpdateTime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SYS_LOG_ID", nullable = true)
	public SysAgentMainLog getSysStatesHistoryLog() {
		return this.sysStatesHistoryLog;
	}

	public void setSysStatesHistoryLog(SysAgentMainLog sysStatesHistoryLog) {
		this.sysStatesHistoryLog = sysStatesHistoryLog;
	}


}
